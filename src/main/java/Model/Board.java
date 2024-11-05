package Model;

import Board.TileVariant;
import java.util.*;

public class Board {

    //DEFINIZIONE VARIABILI
    private final static int MAX_NUM_TILES = 109;

    private ArrayList<Tile> tilePath = new ArrayList<>(Board.MAX_NUM_TILES);

    //COSTRUTTORE
    public Board() {
        this.tileDisposition();
    }

    // METODI
    // ritorna la casella desiderata
    public Tile getTileAt(int index) {
        return this.tilePath.get(index);
    }

    // chiede alla tile corrispondente di attivare il suo effetto
    public void activateCorrespondingEffect(Player p) {
        this.getTileAt(p.getPawnPosition()).activateEffect(p);
    }
    
    // ritorna il numero di Caselle nella board
    public int getNumberOfTiles() {
        return this.tilePath.size();
    }

    public List<Tile> getTilePath() {
        return this.tilePath;
    }

    // algoritmo per generare la board e disporre le caselle
    private void tileDisposition() {
        // TODO: rimuovere magic numbers

        // ultima occorrenza della casella furto, quando raggiunge 0 (o inf) si può posizionare una casella furto..
        //..inizializzata a 15 perchè non ha senso metterla vicino all'inizio
        int lastStealTile = 15;

        // CORPO
        // aggiungo la prima Tile che DEVE essere empty
        this.tilePath.addFirst(new Tile(0, TileVariant.EMPTY));
        for (int i = 1; i < Board.MAX_NUM_TILES - 1; i++) {

            // se l'ultima Furto non è troppo vicina 
            if (lastStealTile <= 0) {
                // posso considerare Furto nel sorteggio
                this.tilePath.add(i, randomTileIncludingSteal(i));
                // se è stata inserita Furto, resetto il counter a 5
                if (this.tilePath.get(i).getVariant() == TileVariant.STEAL) {
                    lastStealTile = 5;
                }
            }
            // escludo Furto
            else
            {
                this.tilePath.add(i, randomTile(i));
                lastStealTile--;
            }
        }
        // aggiungo l'ultima Tile che DEVE essere empty
        this.tilePath.addLast(new Tile(Board.MAX_NUM_TILES - 1, TileVariant.EMPTY));

        // DEBUG stampo mappa completa
        for (Tile tile : tilePath) {
            System.out.println(tile.toString());
        }
    }

    // sorteggia una casella random con queste percentuali:
    // 32% EMPTY, 20% bonus, 14% malus
    private Tile randomTile(int index) {
        Tile tile = null;
        double outcome = Math.random() * 100;


        if (outcome < 32) {
            tile = new Tile(index, TileVariant.EMPTY);
        }
        else if (outcome >= 32 && outcome < 52) {
            tile = new Tile(index, TileVariant.BONUS_POSITION);
        }
        else if (outcome >= 52 && outcome < 66) {
            tile = new Tile(index, TileVariant.MALUS_POSITION);
        }
        else if (outcome >= 66 && outcome < 86) {
            tile = new Tile(index, TileVariant.BONUS_MONEY);
        }
        else if (outcome >= 86 && outcome < 100) {
            tile = new Tile(index, TileVariant.MALUS_MONEY);
        }
        return tile;
    }

    // sorteggia casella random includendo Furto
    // 30% Empty, 30% Furto, 12% bonus, 8% malus
    private Tile randomTileIncludingSteal(int index) {
        Tile tile = null;
        double outcome = Math.random() * 100;


        if (outcome < 30) {
            tile = new Tile(index, TileVariant.EMPTY);
        }
        else if (outcome >= 30 && outcome < 42) {
            tile = new Tile(index, TileVariant.BONUS_POSITION);
        }
        else if (outcome >= 42 && outcome < 50) {
            tile = new Tile(index, TileVariant.MALUS_POSITION);
        }
        else if (outcome >= 50 && outcome < 62) {
            tile = new Tile(index, TileVariant.BONUS_MONEY);
        }
        else if (outcome >= 62 && outcome < 70) {
            tile = new Tile(index, TileVariant.MALUS_MONEY);
        }
        else if (outcome >= 70 && outcome < 100) {
            tile = new Tile(index, TileVariant.STEAL);
        }
        return tile;
    } 
}
