import java.util.List;
import java.util.ArrayList;

/**
 * Questa classe mutabile rappresenta una griglia bidimensionale di {@link Cella} appartenente a Game Of Life.
 * Ogni griglia è definita da due valori, "base" e "altezza", che è possibile scegliere al momento dell'inizializzazione.
 * I valori di default sono 20 per la base e 10 per l'altezza.
 * Al momento della creazione dell'oggetto, la disposizione delle celle vive e morte è scelta randomicamente.
 * E' possibile, data una griglia, modificarne lo stato computando lo stato successivo secondo le seguenti regole:
 *      1) ogni cella viva se è circondata da 2 o 3 celle vive sopravvive.
 *      2) ogni cella morta se è circondata da 3 celle vive diventa una cella viva.
 *      3) ogni altra cella permane nello stesso stato.
 */
public final class Griglia {

    //griglia bidimensionale di celle
    private Cella[][] mtrx;
    //base della griglia
    private final int base;
    //altezza della griglia
    private final int altezza;

    /**
     * Costruttore che inizializza una griglia di base 20 e altezza 10.
     * La disposizione delle celle è scelta in modo casuale.
     */
    public Griglia() {
        this.base = 20;
        this.altezza = 10;
        this.mtrx = new Cella[altezza][base];
        for(int i = 0; i < altezza; i++)
            for(int j = 0; j < base; j++)
                mtrx[i][j] = Cella.newCell();
    }

    /**
     * Costruttore che inizializza una griglia di base e altezza scelte dall'utente.
     * La disposizione delle celle è scelta in modo casuale.
     * 
     * @param base la base della griglia
     * @param altezza l'altezza della griglia
     */
    public Griglia(final int base, final int altezza) {
        this.base = base;
        this.altezza = altezza;
        this.mtrx = new Cella[altezza][base];
        for(int i = 0; i < altezza; i++)
            for(int j = 0; j < base; j++)
                mtrx[i][j] = Cella.newCell();
    }

    private List<Cella> celleVicine(final int i, final int j) {
        List<Cella> lista = new ArrayList<>();
        if(i > 0) lista.add(mtrx[i-1][j]);
        if(j > 0) lista.add(mtrx[i][j-1]);
        if(i > 0 && j > 0) lista.add(mtrx[i-1][j-1]);
        if(i < this.altezza - 1) lista.add(mtrx[i+1][j]);
        if(j < this.base - 1) lista.add(mtrx[i][j+1]);
        if(i < this.altezza - 1 && j < this.base - 1) lista.add(mtrx[i+1][j+1]);
        return lista; 
    }

    private int numeroCelleVive(final List<Cella> lista) {
        int res = 0;
        for(Cella c : lista)
            if(c.isAlive()) res++;
        return res;
    }



    private Cella nuovaCella(final Cella c, final int i, final int j) {
        List<Cella> celleVicine = this.celleVicine(i, j);
        int numCelleVive = this.numeroCelleVive(celleVicine);
        if(c.isAlive() && numCelleVive != 2 && numCelleVive != 3) return Cella.newDeadCell();
        if(!c.isAlive() && numCelleVive == 3) return Cella.newAliveCell();
        return c;
    }

    public int base() {
        return this.base;
    }

    public int altezza() {
        return this.altezza;
    }

    /**
     * Metodo osservazionale che permette di conoscere lo stato di una cella 
     * posizionata alle coordinate passate come parametri.
     * 
     * @param i coordinata altezza.
     * @param j coordinata base.
     * @return {@code true} sse la cella in posizione {@code (i,j)} è viva.
     * @throws IndexOutOfBoundsException se {@code i < 0 || i > this.altezza} oppure {@code j < 0 || j > this.base}
     */
    public boolean isAlive(final int i, final int j) throws IndexOutOfBoundsException {
        if(i < 0 || i > this.altezza) throw new IndexOutOfBoundsException();
        if(j < 0 || j > this.base) throw new IndexOutOfBoundsException();
        return this.mtrx[i][j].isAlive();
    }

    /**
     * Metodo mutazionale che evolve la griglia secondo le regole del gioco.
     */
    public void evolve() {
        Cella[][] nuovaMtrx = new Cella[this.altezza][this.base];
        for(int i = 0; i < this.altezza; i++)
            for(int j = 0; j < this.base; j++)
                nuovaMtrx[i][j] = nuovaCella(mtrx[i][j], i, j);
        this.mtrx = nuovaMtrx;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < mtrx.length; i++) {
            for(int j = 0; j < mtrx[0].length; j++) 
                sb.append(mtrx[i][j].toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Griglia g = new Griglia();
        System.out.println("### T_0 ### \n");
        System.out.println(g.toString());
        g.evolve();
        System.out.println("### T_1 ### \n");
        System.out.println(g.toString());
    }
}