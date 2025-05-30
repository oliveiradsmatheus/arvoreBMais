public class BMais {
    private No raiz;

    public BMais() {
        raiz = null;
    }

    private No navegarAteFolha(int info) {
        No no = raiz;
        int pos;
        while (no instanceof NoPonteiro) {
            pos = no.procurarPosicao(info);
            no = ((NoPonteiro) no).getvLig(pos);
        }
        return no;
    }

    private No localizarPai(No no, int info) {
        No aux = raiz, pai = raiz;
        int pos;
        while (aux instanceof NoPonteiro && aux != no) {
            pai = aux;
            pos = aux.procurarPosicao(info);
            aux = ((NoPonteiro) aux).getvLig(pos);
        }
        return pai;
    }

    public No buscarReferencia(int info) {
        No no = raiz;
        int pos;
        boolean flag = false;
        while (no instanceof NoPonteiro && !flag) {
            pos = no.procurarPosicao(info) - 1;
            if (pos == -1)
                pos++;
            if (pos < no.getTL() && (info == no.getvInfo(pos)))
                flag = true;
            else
                no = ((NoPonteiro) no).getvLig(pos);
        }
        return no;
    }

    private void split(No no, No pai) {
        int limite;
        if (no instanceof NoFolha) {
            NoFolha cx1 = new NoFolha();
            NoFolha cx2 = new NoFolha();
            limite = (int) Math.ceil((double) No.N / 2);
            for (int i = 0; i < limite; i++)
                cx1.setvInfo(i, no.getvInfo(i));
            cx1.setTL(limite);
            for (int i = limite; i < no.getTL(); i++)
                cx2.setvInfo(i - limite, no.getvInfo(i));
            cx2.setTL(no.getTL() - limite);
            cx1.setProx(cx2);
            cx2.setAnt(cx1);
            if (no == pai) {
                NoPonteiro noPai = new NoPonteiro();
                noPai.setvInfo(0, cx2.getvInfo(0));
                noPai.setTL(1);
                noPai.setvLig(0, cx1);
                noPai.setvLig(1, cx2);
                raiz = noPai;
            } else {
                int pos = pai.procurarPosicao(no.getvInfo(limite));
                pai.remanejar(pos);
                pai.setvInfo(pos, cx2.getvInfo(0));
                pai.setTL(pai.getTL() + 1);
                if (pos > 0 && ((NoPonteiro) pai).getvLig(pos - 1) instanceof NoFolha) {
                    No ant = ((NoPonteiro) pai).getvLig(pos - 1);
                    ((NoFolha) ant).setProx(cx1);
                    cx1.setAnt(ant);
                }
                if (pos < pai.getTL() - 1 && ((NoPonteiro) pai).getvLig(pos + 2) instanceof NoFolha) {
                    No prox = ((NoPonteiro) pai).getvLig(pos + 2);
                    ((NoFolha) prox).setAnt(cx2);
                    cx2.setProx(prox);
                }
                ((NoPonteiro) pai).setvLig(pos, cx1);
                ((NoPonteiro) pai).setvLig(pos + 1, cx2);
                if (pai.getTL() == No.N) {
                    no = pai;
                    pai = localizarPai(pai, pai.getvInfo(0));
                    split(no, pai);
                }
            }
        } else {
            limite = (int) Math.ceil((double) No.N / 2) - 1;
            NoPonteiro cx1 = new NoPonteiro();
            NoPonteiro cx2 = new NoPonteiro();
            for (int i = 0; i < limite; i++) {
                cx1.setvInfo(i, no.getvInfo(i));
                cx1.setvLig(i, ((NoPonteiro) no).getvLig(i));
            }
            cx1.setvLig(limite, ((NoPonteiro) no).getvLig(limite));
            cx1.setTL(limite);
            for (int i = limite + 1; i < no.getTL(); i++) {
                cx2.setvInfo(i - limite - 1, no.getvInfo(i));
                cx2.setvLig(i - limite - 1, ((NoPonteiro) no).getvLig(i));
            }
            cx2.setTL(no.getTL() - limite - 1);
            cx2.setvLig(cx2.getTL(), ((NoPonteiro) no).getvLig(no.getTL()));
            if (no == pai) {
                NoPonteiro noPai = new NoPonteiro();
                noPai.setvInfo(0, no.getvInfo(limite));
                noPai.setTL(1);
                noPai.setvLig(0, cx1);
                noPai.setvLig(1, cx2);
                raiz = noPai;
            } else {
                int pos = pai.procurarPosicao(no.getvInfo(limite));
                pai.remanejar(pos);
                pai.setvInfo(pos, no.getvInfo(limite));
                pai.setTL(pai.getTL() + 1);
                ((NoPonteiro) pai).setvLig(pos, cx1);
                ((NoPonteiro) pai).setvLig(pos + 1, cx2);
                if (pai.getTL() == No.N) {
                    no = pai;
                    pai = localizarPai(pai, pai.getvInfo(0));
                    split(no, pai);
                }
            }
        }
    }

    public void inserir(int info) {
        if (raiz == null)
            raiz = new NoFolha(info);
        else {
            No folha = navegarAteFolha(info);
            int pos = folha.procurarPosicao(info);
            folha.remanejar(pos);
            folha.setvInfo(pos, info);
            folha.setTL(folha.getTL() + 1);
            if (folha.getTL() == No.N) {
                No pai = localizarPai(folha, folha.getvInfo(0));
                split(folha, pai);
            }
        }
    }

    private void redistribuirConcatenar(No no) {
        No pai = localizarPai(no, no.getvInfo(0));
        No irmaE, irmaD;
        int posPai = pai.procurarPosicao(no.getvInfo(0));
        int limite = (int) Math.ceil((double) No.N / 2) - 1;
        if (posPai > 0)
            irmaE = ((NoPonteiro) pai).getvLig(posPai - 1);
        else
            irmaE = null;
        if (posPai < pai.getTL())
            irmaD = ((NoPonteiro) pai).getvLig(posPai + 1);
        else
            irmaD = null;
        // redistribuição com a irmã da esquerda
        if (irmaE != null && irmaE.getTL() > limite) {
            if (no instanceof NoFolha) {
                no.remanejar(0);
                no.setvInfo(0, irmaE.getvInfo(irmaE.getTL() - 1));
                no.setTL(no.getTL() + 1);
                irmaE.setTL(irmaE.getTL() - 1);
                pai.setvInfo(posPai - 1, no.getvInfo(0));
            } else {
                no.remanejar(0);
                no.setvInfo(0, pai.getvInfo(posPai - 1));
                no.setTL(no.getTL() + 1);
                ((NoPonteiro) no).setvLig(0, ((NoPonteiro) irmaE).getvLig(irmaE.getTL()));
                pai.setvInfo(posPai - 1, irmaE.getvInfo(irmaE.getTL() - 1));
                irmaE.setTL(irmaE.getTL() - 1);
            }
        } else
            // redistribuição com a irmã da direita
            if (irmaD != null && irmaD.getTL() > limite) {
                if (no instanceof NoFolha) {
                    no.setvInfo(no.getTL(), irmaD.getvInfo(0));
                    no.setTL(no.getTL() + 1);
                    irmaD.remanejarExclusao(0);
                    irmaD.setTL(irmaD.getTL() - 1);
                    pai.setvInfo(posPai, irmaD.getvInfo(0));
                } else {
                    no.setvInfo(no.getTL(), pai.getvInfo(posPai));
                    no.setTL(no.getTL() + 1);
                    ((NoPonteiro) no).setvLig(no.getTL(), ((NoPonteiro) irmaD).getvLig(0));
                    pai.setvInfo(posPai, irmaD.getvInfo(0));
                    irmaD.remanejarExclusao(0);
                    irmaD.setTL(irmaD.getTL() - 1);
                }
            } else {
                // concatenação com a irmã da esquerda
                if (irmaE != null) {
                    if (no instanceof NoFolha) {
                        pai.remanejarExclusao(posPai - 1);
                        for (int i = 0; i < no.getTL(); i++) {
                            irmaE.setvInfo(irmaE.getTL(), no.getvInfo(i));
                            irmaE.setTL(irmaE.getTL() + 1);
                        }
                        ((NoFolha) irmaE).setProx(((NoFolha) no).getProx());
                        if (((NoFolha) no).getProx() != null)
                            ((NoFolha) ((NoFolha) no).getProx()).setAnt(irmaE);
                        ((NoPonteiro) pai).setvLig(posPai - 1, irmaE);
                        pai.setTL(pai.getTL() - 1);
                    } else {
                        irmaE.setvInfo(irmaE.getTL(), pai.getvInfo(posPai - 1));
                        irmaE.setTL(irmaE.getTL() + 1);
                        pai.remanejarExclusao(posPai - 1);
                        pai.setTL(pai.getTL() - 1);
                        for (int i = 0; i < no.getTL(); i++) {
                            irmaE.setvInfo(irmaE.getTL(), no.getvInfo(i));
                            ((NoPonteiro) irmaE).setvLig(irmaE.getTL(), ((NoPonteiro) no).getvLig(i));
                            irmaE.setTL(irmaE.getTL() + 1);
                        }
                        ((NoPonteiro) irmaE).setvLig(irmaE.getTL(), ((NoPonteiro) no).getvLig(no.getTL()));
                        ((NoPonteiro) pai).setvLig(posPai - 1, irmaE);
                    }
                } else {
                    // concatenação com a irmã da direita
                    if (irmaD != null) {
                        if (no instanceof NoFolha) {
                            pai.remanejarExclusao(0);
                            for (int i = 0; i < no.getTL(); i++) {
                                irmaD.remanejar(0);
                                irmaD.setvInfo(0, no.getvInfo(i));
                                irmaD.setTL(irmaD.getTL() + 1);
                            }
                            ((NoFolha) irmaD).setAnt(((NoFolha) no).getAnt());
                            if (((NoFolha) no).getAnt() != null)
                                ((NoFolha) ((NoFolha) no).getAnt()).setProx(irmaD);
                            pai.setTL(pai.getTL() - 1);
                        } else {
                            irmaD.remanejar(0);
                            irmaD.setvInfo(0, pai.getvInfo(0));
                            irmaD.setTL(irmaD.getTL() + 1);
                            pai.remanejarExclusao(0);
                            pai.setTL(pai.getTL() - 1);
                            for (int i = no.getTL() - 1; i >= 0; i--) {
                                irmaD.remanejar(0);
                                irmaD.setvInfo(0, no.getvInfo(i));
                                ((NoPonteiro) irmaD).setvLig(1, ((NoPonteiro) no).getvLig(i + 1));
                                irmaD.setTL(irmaD.getTL() + 1);
                            }
                            ((NoPonteiro) irmaD).setvLig(0, (((NoPonteiro) no).getvLig(0)));
                        }
                    }
                }
                if (pai == raiz && pai.getTL() == 0) {
                    if (irmaE != null)
                        raiz = irmaE;
                    else
                        raiz = irmaD;
                } else if (pai != raiz && pai.getTL() < limite)
                    redistribuirConcatenar(pai);
            }
    }

    public void excluir(int info) {
        No folha = navegarAteFolha(info);
        if (folha != null) {
            int pos = folha.procurarPosicao(info) - 1;
            if (pos == -1)
                pos++;
            folha.remanejarExclusao(pos);
            folha.setTL(folha.getTL() - 1);
            if (pos != 0 && ((NoFolha) folha).getAnt() != null) {
                No noRef = buscarReferencia(info);
                if (noRef instanceof NoPonteiro) {
                    pos = noRef.procurarPosicao(info) - 1;
                    if (pos == -1)
                        pos++;
                    noRef.setvInfo(pos, folha.getvInfo(0));
                }
            }
            if (folha == raiz && raiz.getTL() == 0)
                raiz = null;
            else if (folha != raiz && folha.getTL() < (int) Math.ceil((double) No.N / 2) - 1)
                redistribuirConcatenar(folha);
        }
    }

    public void exibir() {
        No no = raiz;
        while (no instanceof NoPonteiro)
            no = ((NoPonteiro) no).getvLig(0);
        while (no != null) {
            for (int i = 0; i < no.getTL(); i++)
                System.out.println(no.getvInfo(i));
            no = ((NoFolha) no).getProx();
        }
    }

    public void exibirPorNivel() {
        preOrdem(raiz, 1);
    }

    public void preOrdem(No no, int nivel) {
        if (no != null) {
            for (int i = 1; i < nivel; i ++)
                System.out.print("\t");
            System.out.print("Nível: " + nivel + "\t[");
            for (int i = 0; i < no.getTL() - 1; i++)
                System.out.print(no.getvInfo(i) + ", ");
            System.out.println(no.getvInfo(no.getTL() - 1) + "]");
            if (no instanceof NoPonteiro)
                for (int i = 0; i <= no.getTL(); i++)
                    preOrdem(((NoPonteiro) no).getvLig(i), nivel + 1);
        }
    }

    /*void arvore(Bloco *disco, int end, int nivel, int *vet) {
        int i = 1, j, k;
        char espaco[400] = "", traco[10], curva[12], te[12];

        traco[0] = 179;
        traco[1] = ' ';
        traco[2] = ' ';
        traco[3] = ' ';
        traco[4] = '\0';
        traco[5] = '\0';
        curva[0] = 192;
        curva[1] = 196;
        curva[2] = 196;
        curva[3] = ' ';
        curva[4] = '\0';
        te[0] = 195;
        te[1] = 196;
        te[2] = 196;
        te[3] = ' ';
        te[4] = '\0';

        if (disco[disco[end].inode.endDireto[0]].dir.TL == 3)
            vet[nivel] = 1; // 1 significa o último diretorio!
        while (i < nivel) {
            if (vet[i] == 0)
                strcat(espaco, traco);
            else
                strcat(espaco, "    ");
            i++;
        }
        i = 0;
        while (i < QTDE_INODE_DIRETO && disco[end].inode.endDireto[i] != endNulo()) {
            for (j = 2; j < disco[disco[end].inode.endDireto[i]].dir.TL; j++) {
                k = nivel * 4 - 4;
                espaco[k] = '\0';
                if (j + 1 == disco[disco[end].inode.endDireto[i]].dir.TL && i < QTDE_INODE_DIRETO && disco[end].inode.
                        endDireto[i + 1] == endNulo()) {
                    vet[nivel] = 1;
                    strcat(espaco, curva);
                } else
                    strcat(espaco, te);
                printf("%s", espaco);
                if (disco[disco[disco[end].inode.endDireto[i]].dir.arquivo[j].endInode].inode.permissao[0] == 'd')
                    printf(AZUL);
                else if (disco[disco[disco[end].inode.endDireto[i]].dir.arquivo[j].endInode].inode.permissao[0] == 'l')
                    printf(CIANO);
                printf("%s\n%s", disco[disco[end].inode.endDireto[i]].dir.arquivo[j].nome, RESET);
                arvore(disco, disco[disco[end].inode.endDireto[i]].dir.arquivo[j].endInode, nivel + 1, vet);
            }
            i++;
        }
    }

    void exibirArvore(Bloco *disco, int raiz) {
        int i, vet[100];

        vet[0] = 1;
        for (i = 1; i < 100; i++)
            vet[i] = 0;
        printf("/.\n");
        arvore(disco, raiz, 1, vet);
    }*/
}
