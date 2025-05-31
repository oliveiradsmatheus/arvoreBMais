public class Main {
    public static void main(String[] args) {
        BMais arv = new BMais();

        for (int i = 1; i <= 50; i++)
            arv.inserir(i);

        for (int i = 1; i <= 50; i+=2)
            arv.excluir(i);

        arv.exibirPorNivel();
        arv.exibir();
    }
}