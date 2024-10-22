package View;

import Model.FightActions;
import java.util.Scanner;

public class FightView {
    public void displayCombatStatus(String status) {
        // Mostra lo stato attuale del combattimento (in console o GUI)
        System.out.println(status);
    }

    public FightActions getPlayerChoice(){
        //TODO:controlli input
        // Ottieni la scelta del giocatore reale (da console o GUI)
        // Questo potrebbe essere un input dall'utente
        Scanner scanner = new Scanner(System.in);
        System.out.println("Scegli la tua mossa tra attacco, difesa, ricarica: ");
        String choice = scanner.next();
        
        // Restituisci la mossa corrispondente
        //player 1 [q: attack, w: defend, e: recharge]
        //player 2 [i: attack, o: defend, p: recharge]
        switch (choice) {
            case "q":
                return FightActions.ATTACK;
            case "w":
                return FightActions.DEFEND;
            case "e":
                return FightActions.RECHARGE;
            case "i":
                return FightActions.ATTACK;
            case "o":
                return FightActions.DEFEND;
            case "p":
                return FightActions.RECHARGE;
            default:
                throw new IllegalArgumentException("Input errato!");
        }
    }
}

