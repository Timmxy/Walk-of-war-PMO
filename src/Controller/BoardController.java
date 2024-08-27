package Controller;

import Model.Match;
import Model.Player;

public class BoardController {

    private Match match;

    public BoardController(Match m) {
        this.match = m;
    }

    public void checkTileEffect(Player p, int position) {
        this.match.getBoard().activateCorrespondingEffect(position, p);
    }

    public void updatePlayerStatus(int value) {
        // mi sembra che l'unico modo sia di trasferire la logica di..
        // ..activateEffect() di Tile qui, ma poi Tile a che cazzo serve?
        // inoltre risparmierebbe di fare avanti e indietro tra questa classe e Tile
    }

    public void updateMoney() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateMoney'");
    }
}
