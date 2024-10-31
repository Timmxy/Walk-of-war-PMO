package Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Board.TileVariant;

public class Tile {
    
    //DICHIARAZIONE VARIABILI
    private int position;           // viene inizializzata all'atto della creazione, in base all'indice corrente della ArrayList
    private int effectValue;        // valore dell'effetto (ES.: +5 monete, -1 posizione) 
    private TileVariant variant;    // tipo di casella (effetto)
    private Random rnd;             // per valore casuale di effetto

    private List<TileEffectListener> listeners = new ArrayList<>();


    //COSTRUTTORE
    public Tile(int index, TileVariant type) {
        this.position = index;
        this.variant = type;
        this.rnd = new Random();
        this.chooseRandomValue();
    }

    // METODI

    private void chooseRandomValue() {
        if (this.variant == TileVariant.BONUS_MONEY || this.variant == TileVariant.MALUS_MONEY) {
            this.effectValue = this.rnd.nextInt(5)+1;
        }
        else if (this.variant == TileVariant.BONUS_POSITION || this.variant == TileVariant.MALUS_POSITION) {
            this.effectValue = this.rnd.nextInt(2)+1;
        }
        else {
            this.effectValue = 0;
        }
    }

    // metodo per registrare i listener
    public void addTileEffectListener(TileEffectListener listener) {
        this.listeners.add(listener);
    }

    // metodo privato per notificare tutti i listener
    private void notifyListeners(TileEffectEvent event) {
        for (TileEffectListener listener : this.listeners) {
            listener.onTileEffectActivated(event);
        }
    }
    
    // attiva l'effetto della casella: crea l'evento e notifica ascoltatori -> PlayerController 
    public void activateEffect(Player p) {
        TileEffectEvent event = new TileEffectEvent(p, this.variant, this.effectValue);
        System.out.println(p.toString() + " finisce sulla " +this.toString() +" val: "+ this.effectValue);
        this.notifyListeners(event);
    }


    // ritorna la posizione ordinata della casella nella board
    public int getPosition() {
        return this.position;
    }

    // ritorna il tipo di casella
    public TileVariant getVariant() {
        return this.variant;
    }

    // ritorna il valore effetto della Tile
    public int getEffectValue() {
        return this.effectValue;
    }

    public String getEffectValueString() {
        if (this.effectValue != 0) {
            return "" + this.effectValue;
        }
        else return "";
    }

    @Override
    public String toString() {
        return "Casella num. "+ this.position +" di tipo: "+this.variant+" "+this.effectValue;
    }
}
