package Board;

public class Tile {
    
    //DICHIARAZIONE VARIABILI
    private int position;           // viene inizializzata all'atto della creazione, in base all'indice corrente della ArrayList
    private TileVariant variant;    // tipo di casella (effetto)

    //COSTRUTTORE
    public Tile(int index, TileVariant type) {
        this.position = index;
        this.variant = type;
    }

    // METODI
    // ritorna la posizione ordinata della casella nella board
    public int getPosition() {
        return this.position;
    }

    // ritorna il tipo di casella
    public TileVariant getVariant() {
        return this.variant;
    }

    // attiva l'effetto della casella bonus/malus, non fa niente se blank
    public void activateEffect() {

    }

}
