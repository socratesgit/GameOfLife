import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GameOfLife {

    private static void checkArgs(String[] args) throws IllegalArgumentException {
        if(args.length != 3) throw new IllegalArgumentException("Il numero di argomenti è sbagliato! Devono essere 3.");
        int i,j,ev;
        try {
            i = Integer.parseInt(args[0]);
            j = Integer.parseInt(args[1]);
            ev = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("I parametri passati devono essere numeri!");
        }
        if(i < 0 || j < 0 || ev < 0) throw new IllegalArgumentException("I parametri devono essere positivi!");
    }

    public static void main(String[] args) {

        try {
            GameOfLife.checkArgs(args);
        } catch (IllegalArgumentException e) {
            System.out.print(e.toString());
            System.exit(1);
        }

        int altezza = Integer.parseInt(args[0]);
        int base =  Integer.parseInt(args[1]);
        int evoluzione = Integer.parseInt(args[2]);

        Griglia g = new Griglia(altezza,base);
        File f = new File("output.txt");
        if(!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("Errore nella creazione del file!!!");
                System.exit(1);
            }
        } else {
            try {
                f.delete();
                f.createNewFile();
            } catch (IOException e) {
                System.out.println("Errore nella creazione o nella rimozione del file!!!");
                System.exit(1);
            }
        }
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(f));
            for(int step = 0; step < evoluzione; step++) {
                if(step == 0) {
                    writer.write("GAME OF LIFE\n\n#### T_0 #### \n\n"+g.toString()+"\n");
                } else {
                    String header = String.format("\n#### T_%d ####\n\n", step);
                    writer.append(header + g.toString() + "\n");
                }
                g.evolve();
            }
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("Errore nella chiusura del writer!!!");
                System.exit(1);
            }
        } catch (IOException e) {
            System.out.println("Errore nell'inizializzazione del writer!!!");
            System.exit(1);
        }
        
        
    }

}