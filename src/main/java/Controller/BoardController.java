package Controller;

import Model.Board;
import Model.Player;
import View.BoardView;

public class BoardController {

    // DICHIARAZIONE VARIABILI
    private Board board;
    private BoardView view;

    // COSTRUTTORE
    public BoardController(Board b) {
        this.board = b;
        this.view = new BoardView(this.board.getTilePath());
    }

    // METODI
    // richiede alla Board di attivare l'effetto casella
    public void performActionOnTile(Player player) {
        this.board.activateCorrespondingEffect(player);
    }

    // ritorna il numero massimo di Caselle nella Board
    public int getNumberOfTiles() {
        return this.board.getNumberOfTiles();
    }

    // ritorna la View della Board
    public BoardView getView() {
        return this.view;
    }

}

