package Controller;

import Model.CPUPlayer;
import Model.RealPlayer;

public class NewMatchController {
    private RealPlayer realPlayer;
    private CPUPlayer cpuPlayer;

    public NewMatchController(RealPlayer realPlayer, CPUPlayer cpuPlayer) {
        this.realPlayer = realPlayer;
        this.cpuPlayer = cpuPlayer;
    }

    // Metodi per ricevere scelte dal giocatore reale tramite la View
    public void setPlayerWantsToVisitShop(boolean choice) {
        realPlayer.setWantsToVisitShop(choice);
    }

    public void setPlayerWantsToRerollDice(boolean choice) {
        realPlayer.setWantsToRerollDice(choice);
    }

    public void setPlayerWantsToMovePosition(boolean choice) {
        realPlayer.setWantsToMovePosition(choice);
    }

    public void setPlayerHasWon(boolean choice) {
        realPlayer.setHasWon(choice);
    }

    // Logica per far agire i giocatori
    public void handlePlayerTurn() {
        if (realPlayer.wantsToVisitShop()) {
            // Logica per il negozio
        }
        if (realPlayer.wantsToRerollDice()) {
            // Logica per rerollare i dadi
        }
        if (realPlayer.wantsToMovePosition()) {
            // Logica per cambiare posizione
        }
        if (realPlayer.hasWon()) {
            // Logica per vittoria del giocatore
        }
    }

    public void handleCPUTurn() {
        if (cpuPlayer.wantsToVisitShop()) {
            // Logica per il negozio del CPUPlayer
        }
        if (cpuPlayer.wantsToRerollDice()) {
            // Logica per rerollare i dadi del CPUPlayer
        }
        if (cpuPlayer.wantsToMovePosition()) {
            // Logica per cambiare posizione del CPUPlayer
        }
        if (cpuPlayer.hasWon()) {
            // Logica per vittoria del CPUPlayer
        }
    }
}

