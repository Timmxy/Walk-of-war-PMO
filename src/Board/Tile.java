package Board;

import Model.Player;

// N.B. NON SO ANCORA COME E' MEGLIO GESTIRE LE TILES, SE COSI' oppure CON CLASSE TILE ASTRATTA
// E UNA CLASSE SPECIFICA PER OGNI VARIANTE che vanno a specializzare il metodo astratto activateEffect()
public class Tile {
    
    //DICHIARAZIONE VARIABILI
    private int position;           // viene inizializzata all'atto della creazione, in base all'indice corrente della ArrayList
    private int effectValue;        // valore dell'effetto (ES.: +5 monete, -1 posizione) 

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

    // attiva l'effetto della casella bonus/malus, non fa niente se empty
    public void activateEffect(Player p1) {
        switch (variant) {
            case BONUS_MONEY:
                // TODO: si dovrebbe passare tramite player controller (1 step in piu')
                p1.addMoney(this.effectValue);
                break;
            case BONUS_POSITION:
                p1.updatePosition(this.effectValue);
                break;
            case EMPTY:
                // do nothing
                break;
            case MALUS_MONEY:
                p1.addMoney(-this.effectValue);
                break;
            case MALUS_POSITION:
                p1.updatePosition(-this.effectValue);
                break;
            case STEAL:
                // logica per la meccanica del furto 
                // dati richiesti: player invocante e player vittima (scelto casualmente)
                break;
            default:
                break;
        }
    }

    @Override
    public String toString() {
        return "Casella num. "+ this.position +" di tipo: "+this.variant;
    }
}
