public class Main {
    public static void main(String[] args) {
        BMais arv = new BMais();

        for (int i = 1; i <= 10000; i++)
            arv.inserir(i);

        arv.exibirPorNivel();

        for (int i = 6; i <= 9995; i++)
            arv.excluir(i);

        arv.exibirPorNivel();

        arv.exibir();
    }
}