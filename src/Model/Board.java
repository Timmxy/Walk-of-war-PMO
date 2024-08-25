package Model;

import java.util.*;

import Board.Tile;

public class Board {

    //DEFINIZIONE VARIABILI
    private final static int MAX_NUM_TILES = 60;

    // forse la faremo variare in base al num di Player?
    private ArrayList<Tile> tilePath = new ArrayList<>(Board.MAX_NUM_TILES);

    //COSTRUTTORE
    public Board() {
        this.tileDisposition();
    }

    // METODI
    // algoritmo per generare la board e disporre le caselle
    // N.B. ancora non so come collegarle alla View per disporle anche graficamente
    // suppongo che il Controller accederà direttamente alla lista delle caselle..
    // ..una volta che è già stata popolata
    private void tileDisposition() {
        // variabili locali

        // ultima occorrenza della casella furto, quando raggiunge 0 (o inf) si può posizionare una casella furto..
        //..inizializzata a 15 perchè non ha senso metterla vicino all'inizio
        int lastStealTile = 15;

        // corpo
        for (int i = 0; i < Board.MAX_NUM_TILES; i++) {

            // se l'ultima Furto non è troppo vicina 
            if (lastStealTile <= 0) {
                // posso considerare Furto nel sorteggio

            }
        }
    }

    /* 
    private Tile randomTile(int index) {

    }
    */
}
