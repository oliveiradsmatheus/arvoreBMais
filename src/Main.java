public class Main {
    public static void main(String[] args) {
        BMais arv = new BMais();

        for (int i = 1; i <= 100000; i++)
            arv.inserir(i);

        for (int i = 6; i <= 99995; i++)
            arv.excluir(i);

        arv.exibirPorNivel();
        arv.exibir();
    }
}