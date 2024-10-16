package Controller;

import Model.Fight;
import Model.FightActions;
import Model.Player;
import View.FightView;
import java.util.Random;

public class FightController {

    // DICHIARAZIONE CAMPI
    private final Fight fight;
    private Player player1;
    private Player player2;
    private FightActions actionPlayer1;
    private FightActions actionPlayer2;


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
    public void resolveFight(boolean  isPlayer1CPU, boolean isPlayer2CPU){
        while (!isPlayerOut(player1) && !isPlayerOut(player2)) {
            /* TODO: this.fight.resolveRound(//prendere in ingresso quello che sceglie l'utente: GESTIRE INPUT TASTIERA o cpu);*/
            // TODO: l'input DEVE essere preso da PlayerController

             // Turno del giocatore 1
            actionPlayer1 = getAction(isPlayer1CPU, player1);
            fight.resolveRound(actionPlayer1, actionPlayer2);
            //TODO: aggiornare la view sullo stato attuale del combattimento tipo cosi:
            //view.displayCombatStatus(fight.getCombatStatus());

            //se uno dei due muore fare break; (non mi garba perchè il 2 potrebbe difendersi dopo il break)

            // Turno del giocatore 2
            actionPlayer2 = getAction(isPlayer2CPU, player2);
            fight.resolveRound(actionPlayer2, actionPlayer1);
            //view.displayCombatStatus(fight.getCombatStatus());
        }//fine ciclo

        //TODO: può finire in pareggio quindi riguardare queste condizioni
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

    // Restituisce la mossa a seconda che il giocatore sia reale o CPU
    private FightActions getAction(boolean isCpu, Player player) {
        if (isCpu) {
            return cpuDecision(player); // Logica CPU
        } else {
            return view.getPlayerChoice(); // Logica giocatore reale
        }
    }

    // Metodo per determinare la scelta della CPU
    public FightActions cpuDecision(Player player){
        FightActions choise;
        Random rnd = new Random();
        boolean randomChoise = rnd.nextBoolean();

        //prima azione random tra attacco/difende
        if(randomChoise){
            choise = FightActions.ATTACK;
        } else {
            choise = FightActions.DEFEND;
        }
        if(player.getCurrentAtks() < player.getMaxAtks() -1){ //TODO: numero magico
            choise = FightActions.RECHARGE;
        }
        return choise;
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
