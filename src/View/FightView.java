package View;

import Model.FightActions;
import java.util.Scanner;

import Model.FightActions;

public class FightView {
    public void displayCombatStatus(String status) {
        // Mostra lo stato attuale del combattimento (in console o GUI)
        System.out.println(status);
    }

    public FightActions getPlayerChoice() throws Exception {
        //TODO:controlli input
        // Ottieni la scelta del giocatore reale (da console o GUI)
        // Questo potrebbe essere un input dall'utente
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scegli la tua mossa tra attacco, difesa, ricarica: ");
        String choice = scanner.next();
        
        // Restituisci la mossa corrispondente
        switch (choice) {
            case "attacco":
                return FightActions.ATTACK;
            case "difesa":
                return FightActions.DEFEND;
            case "ricarica":
                return FightActions.RECHARGE;
            default:
                throw new Exception();
        }
    }
}

