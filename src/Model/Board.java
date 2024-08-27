package Model;

import java.util.*;

import Board.Tile;
import Board.TileVariant;
import Controller.BoardController;

public class Board {

    //DEFINIZIONE VARIABILI
    private final static int MAX_NUM_TILES = 60;

    // forse la faremo variare in base al num di Player?
    private ArrayList<Tile> tilePath = new ArrayList<>(Board.MAX_NUM_TILES);

    private BoardController boardController;

    //COSTRUTTORE
    public Board(BoardController bc) {
        this.boardController = bc;
        this.tileDisposition();
    }

    // METODI
    public Tile getTileAt(int index) {
        return this.tilePath.get(index);
    }

    public void activateCorrespondingEffect(int index, Player p) {
        this.getTileAt(index).activateEffect(p);
    }


    // algoritmo per generare la board e disporre le caselle
    // N.B. ancora non so come collegarle alla View per disporle anche graficamente
    // suppongo che il Controller accederà direttamente alla lista delle caselle..
    // ..una volta che è già stata popolata
    private void tileDisposition() {
        // variabili locali

        // TODO: rimuovere magic numbers

        // ultima occorrenza della casella furto, quando raggiunge 0 (o inf) si può posizionare una casella furto..
        //..inizializzata a 15 perchè non ha senso metterla vicino all'inizio
        int lastStealTile = 15;

        // corpo
        for (int i = 0; i < Board.MAX_NUM_TILES; i++) {

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
        for (Tile tile : tilePath) {
            System.out.println(tile.toString());
        }
    }

     
    // sorteggia una casella random con queste percentuali:
    // 40% EMPTY, 15% ciascuna delle restanti
    private Tile randomTile(int index) {
        Tile tile = null;
        double outcome = Math.random() * 100;

        // DEBUG
        System.out.println(index+". "+outcome);

        if (outcome < 40) {
            tile = new Tile(index, TileVariant.EMPTY, this.boardController);
        }
        else if (outcome >= 40 && outcome < 55) {
            tile = new Tile(index, TileVariant.BONUS_POSITION, this.boardController);
        }
        else if (outcome >= 55 && outcome < 70) {
            tile = new Tile(index, TileVariant.MALUS_POSITION, this.boardController);
        }
        else if (outcome >= 70 && outcome < 85) {
            tile = new Tile(index, TileVariant.BONUS_MONEY, this.boardController);
        }
        else if (outcome >= 85 && outcome < 100) {
            tile = new Tile(index, TileVariant.MALUS_MONEY, this.boardController);
        }
        return tile;
    }

    // sorteggia casella random includendo Furto
    // 40% Empty, 28% Furto, 8% ciascuna delle restanti
    private Tile randomTileIncludingSteal(int index) {
        Tile tile = null;
        double outcome = Math.random() * 100;

        // DEBUG
        System.out.println(index+". furto possibile "+outcome);

        if (outcome < 40) {
            tile = new Tile(index, TileVariant.EMPTY, this.boardController);
        }
        else if (outcome >= 40 && outcome < 48) {
            tile = new Tile(index, TileVariant.BONUS_POSITION, this.boardController);
        }
        else if (outcome >= 48 && outcome < 56) {
            tile = new Tile(index, TileVariant.MALUS_POSITION, this.boardController);
        }
        else if (outcome >= 56 && outcome < 64) {
            tile = new Tile(index, TileVariant.BONUS_MONEY, this.boardController);
        }
        else if (outcome >= 64 && outcome < 72) {
            tile = new Tile(index, TileVariant.MALUS_MONEY, this.boardController);
        }
        else if (outcome >= 72 && outcome < 100) {
            tile = new Tile(index, TileVariant.STEAL, this.boardController);
        }
        return tile;
    }
    
}
