import java.util.Random;

/**
 * Questa classe è un singleton che rappresenta una cella di Game Of Life.
 * Ogni cella può assumere due valori: "alive" e "dead".
 */
public final class Cella {

    //il valore della cella: 
    //  true = alive
    //  false = dead
    private final boolean val;

    //la cella viva
    private static Cella ALIVE = null;
    //la cella morta
    private static Cella DEAD = null;

    private Cella(final boolean val) {
        this.val = val;
    }
    
    private static void cellInit() {
        if(Cella.DEAD == null) Cella.DEAD = new Cella(false);
        if(Cella.ALIVE == null) Cella.ALIVE = new Cella(true);
    }

    /**
     * Static factory che ritorna con probabilità uniforme
     * una cella viva o una cella morta.
     * 
     * @return una cella
     */
    public static Cella newCell() {
        Cella.cellInit();
        Random rand = new Random();
        if(rand.nextBoolean())
            return Cella.ALIVE;
        return Cella.DEAD;
    }

    /**
     * Static factory che ritorna una cella viva.
     * 
     * @return una cella viva
     */
    public static Cella newAliveCell() {
        Cella.cellInit();
        return Cella.ALIVE;
    }

    /**
     * Static factory che ritorna una cella morta.
     * 
     * @return una cella morta
     */
    public static Cella newDeadCell() {
        Cella.cellInit();
        return Cella.DEAD;
    }

    /**
     * La cella è viva?
     * 
     * @return il valore della cella
     */
    public boolean isAlive() {
        return this.val;
    }

    @Override
    public String toString() {
        return this.isAlive() ? "+" : "-";
    }
}