package Controller;

import Model.Fight;
import Model.Player;
import View.FightView;

public class FightController {

    // DICHIARAZIONE CAMPI
    private final Fight fight;
    private Player player1;
    private Player player2;

    // view
    private FightView view;


    // TODO: penso di passargli PlayerController per facilitare ls comunicazione tra i due
    // COSTRUTTORE
    public FightController(Fight f) {
        this.fight = f;
        this.view = new FightView();
    }

    // METODI

    // metodo per aggiungere player alla fight
    public void addPlayersToFight(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    
    // metodo per lo svolgimento della battaglia, va avanti finchè uno esaurisce hp
    public void resolveFight(){
        while (!isPlayerOut(player1) && !isPlayerOut(player2)) {
            /* TODO: this.fight.resolveRound(//prendere in ingresso quello che sceglie l'utente: GESTIRE INPUT TASTIERA o cpu);*/
            // TODO: l'input DEVE essere preso da PlayerController

            // Verifica se il giocatore 1 è stato eliminato
            if (isPlayerOut(player1)) {
                System.out.println(player2.getName() + " ha VINTO lo scontro!"); 
            }
            // Verifica se il giocatore 2 è stato eliminato
            if (isPlayerOut(player2)) {
                System.out.println(player1.getName() + " ha VINTO lo scontro!"); 
            }
            // Resetto le statistiche dei giocatori che hanno partecipato allo scontro
            player1.resetCurrentToMaxStats();
            player2.resetCurrentToMaxStats();
        }
    }

    // metodo per verificare se un giocatore è eliminato
    public boolean isPlayerOut(Player player) {
        return  player.getCurrentHp() <= 0;
    }


    // metodo per ritornare la propria view al match controller
    public FightView getView() {
        return this.view;
    }
}
