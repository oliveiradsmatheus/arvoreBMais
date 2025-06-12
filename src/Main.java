public class Main {
    public static void main(String[] args) {
        BMais arv = new BMais();

        for (int i = 1; i <= 100000; i++)
            arv.inserir(i);

        for (int i = 6; i <= 99995; i++)
            arv.excluir(i);

        System.out.println("Todos os nós (Em-ordem):\n");
        arv.emOrdem();
        System.out.println("\nPor nível (Pré-ordem):\n");
        arv.exibirPorNivel();
        System.out.println("\nPor lista encadeada:\n");
        arv.exibir();
    }
}