public class NoPonteiro extends No {
    private No[] vLig;

    public NoPonteiro() {
        super();
        vLig = new No[N + 1];
    }

    public No getvLig(int pos) {
        return vLig[pos];
    }

    public void setvLig(int pos, No lig) {
        vLig[pos] = lig;
    }

    @Override
    public void remanejar(int pos) {
        super.remanejar(pos);
        vLig[getTL() + 1] = vLig[getTL()];
        for (int i = getTL(); i > pos; i--)
            vLig[i] = vLig[i - 1];
    }

    @Override
    public void remanejarExclusao(int pos) {
        super.remanejarExclusao(pos);
        for (int i = pos; i < getTL(); i++)
            vLig[i] = vLig[i + 1];
        vLig[getTL() - 1] = vLig[getTL()];
    }
}
