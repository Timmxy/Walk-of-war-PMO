package Controller;

import Model.Board;
import Model.Player;

public class BoardController {

    // DICHIARAZIONE VARIABILI
    private Board board;

    // COSTRUTTORE
    public BoardController(Board b) {
        this.board = b;
    }

    // METODI
    public void performActionOnTile(Player p, int position) {
        //gpt qui si salva Tile temporaneamente
        this.board.activateCorrespondingEffect(position, p);
    }

    public int getNumberOfTiles() {
        return this.board.getNumberOfTiles();
    }
}
