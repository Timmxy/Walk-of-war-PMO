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
    public void performActionOnTile(Player player) {
        this.board.activateCorrespondingEffect(player);
    }

    public int getNumberOfTiles() {
        return this.board.getNumberOfTiles();
    }

    public BoardView getView() {
        return this.view;
    }

}
