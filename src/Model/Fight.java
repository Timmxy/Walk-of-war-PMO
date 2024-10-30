package Model;

import java.util.List;
import java.util.Optional;

import javafx.scene.input.KeyCode;

public class Fight {
    private Player player1;
    private Player player2;

    

    //scelta di progettazione: no costruttore
    
    //metodo che prende la funzione del di costruttore da passare a match controller
    //così che non ci siano più fight in scena in base al numero di player
    // gestisce l'accoppiamento dei giocatori nello scontro
    public void setPlayersFighting(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    public String getCombatStatus(Player Player1, Player Player2){
        return ("""
                Stato combattimento.
                 Player 1:\t[HP: """ + player1.getCurrentHp() + "/" + player1.getMaxHp() +
                " ATKS: " + this.player1.getCurrentAtks() + "/" + player1.getMaxAtks() +
                " SHIELD: " + player1.getcurrentShields() + "/" + player1.getMaxShields() + "]"+
                "\nPlayer 2:\t" + 
                "[HP: " + player2.getCurrentHp() + "/" + player2.getMaxHp() +
                " ATKS: " + player2.getCurrentAtks() + "/" + player2.getMaxAtks() +
                " SHIELD: " + player2.getcurrentShields() + "/" + player2.getMaxShields() + "]");
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }
}
