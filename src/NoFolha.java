public class NoFolha extends No {
    private No ant;
    private No prox;

    NoFolha() {
        super();
        ant = prox = null;
    }

    NoFolha(int info) {
        super(info);
        ant = prox = null;
    }

    public No getAnt() {
        return ant;
    }

    public void setAnt(No ant) {
        this.ant = ant;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }
}
