package Controller;

import java.util.ArrayList;

import Model.Player;

//Controller che regola le azioni dei Player in gioco
public class PlayerController {
    //DICHIARAZIONE VARIABILI
    private ArrayList<Player> players = new ArrayList<>();

    //ES.: chiama la funzione addItemToInventory()
    //quando un Player compra (e quindi equipaggia) un Equipment

    //ES.: aumenta/diminuisce le statistiche all'atto di equipaggiamento 
    //o furto di armatura/arma/scudo
    public void updateHP(Player player, int value) {
        for (Player p : players) {
            if (p.equals(player)) {
                p.addOrRemoveHP(value);
            }
        }
    }

    public void updateAttacks(Player player) {

    }

    public void updateShields(Player player) {

    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

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
