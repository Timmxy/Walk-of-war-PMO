package Controller;

import java.util.ArrayList;

import Equipment.Equipment;
import Model.Match;
import Model.Player;

//Controller che regola le azioni dei Player in gioco
public class PlayerController {
    //DICHIARAZIONE VARIABILI
    private ArrayList<Player> players = new ArrayList<>();

    private Match match;

    public PlayerController(Match m) {
        this.match = m;
    }

    //ES.: chiama la funzione addItemToInventory()
    //quando un Player compra (e quindi equipaggia) un Equipment. Chiamato dal bottone della view
    public void equipmentBought(Player p, Equipment e) {
        p.addItemToInventory(e);
    }

    // metodo chiamato a fine turno per avviare l'effetto della casella su cui è arrivato il player
    public void activateEndTurnActions(Player p, boolean shopVisit) {
        this.match.getBoardController().checkTileEffect(p, p.getPawn().getPosition());
    }

    //ES.: aumenta/diminuisce le statistiche quando occorre furto di armatura/arma/scudo oppure a fine scontro
    public void updateHP(Player player, int value) {
        player.addOrRemoveHP(value);
    }

    public void updateAttacks(Player player, int value) {
        player.addOrRemoveAttacks(value);
    }

    public void updateShields(Player player, int value) {
        player.addOrRemoveShields(value);
    }










    public void addPlayer(Player player) {
        this.players.add(player);
    }

    // non penso serva, dovrei poter chiamare i metodi del player direttamente dal 
    //riferimento che mi passano, quindi non servirebbe manco tenersi una lista di player
    private Player findPlayer(Player player){
        Player playerx = new Player(0, null, null);
        for (Player p : players) {
            if (p.equals(player)) {
                return p;
            }
        }
        return playerx;
    }
}
