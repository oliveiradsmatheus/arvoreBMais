public class No {
    public static final int N = 3;
    private int[] vInfo;
    private int TL;

    public No() {
        vInfo = new int[N];
        TL = 0;
    }

    public No(int info) {
        this();
        vInfo[0] = info;
        TL = 1;
    }

    public int getvInfo(int pos) {
        return vInfo[pos];
    }

    public void setvInfo(int pos, int info) {
        vInfo[pos] = info;
    }

    public int getTL() {
        return TL;
    }

    public void setTL(int TL) {
        this.TL = TL;
    }

    public int procurarPosicaoDuplicado(int info) {
        int pos = 0;
        while (pos < TL && info > vInfo[pos])
            pos++;
        return pos;
    }

    public int procurarPosicao(int info) {
        int pos = 0;
        while (pos < TL && info >= vInfo[pos])
            pos++;
        return pos;
    }

    public void remanejar(int pos) {
        for (int i = TL; i > pos; i--)
            vInfo[i] = vInfo[i - 1];
    }

    public void remanejarExclusao(int pos) {
        for (int i = pos; i < TL - 1; i++)
            vInfo[i] = vInfo[i + 1];
    }
}
