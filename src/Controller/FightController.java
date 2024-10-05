package Controller;

import Model.Fight;
import Model.Player;
import View.FightView;

public class FightController {
    private final Fight fight;
    private final Player player1;
    private final Player player2;


    // DICHIARAZIONE CAMPI


    // COSTRUTTORE
    public FightController(Fight f, Player p1, Player p2) {
        this.fight = f;
        this.player1 = p1;
        this.player2 = p2;
    }

    public FightView getView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getView'");
    }

    
    public void resolveFight(){
        while (!isPlayerOut(player1) && !isPlayerOut(player2)) {
            /*this.fight.resolveRound(//prendere in ingresso quello che sceglie l'utente);
            // Verifica se il giocatore 1 è stato eliminato
            // Resetto le statistiche dei giocatori che hanno partecipato allo scontro*/
            if (isPlayerOut(player1)) {
                System.out.println(player2.getName() + " ha VINTO lo scontro!"); 
            }
            // Verifica se il giocatore 2 è stato eliminato
            if (isPlayerOut(player2)) {
                System.out.println(player1.getName() + " ha VINTO lo scontro!"); 
            }
            player1.resetCurrentToMaxStats();
            player2.resetCurrentToMaxStats();
        }
    }
        // Metodo per verificare se un giocatore è eliminato
    public boolean isPlayerOut(Player player) {
        return  player.getCurrentHp() <= 0;
    }
}
