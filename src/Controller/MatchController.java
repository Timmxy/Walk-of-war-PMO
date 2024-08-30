package Controller;

import java.util.concurrent.TimeUnit;

import Model.Match;
import Model.Player;


// FATTO DA CHATGPT !!!!

public class MatchController {
    private Match match;
    private PlayerController playerController;
    private BoardController boardController;

    public MatchController(Match match, PlayerController playerController, BoardController boardController) {
        this.match = match;
        this.playerController = playerController;
        this.boardController = boardController;
    }

    public void startGame() {
        this.match.setGameOver(false);
        this.match.setCurrentPlayerIndex(0); // inizia con il primo giocatore

        while (!this.match.isGameOver()) {
            this.playTurn();
        }

        System.out.println("Il gioco è finito!");
        // tornare al menu principale / rigioca
    }

    private void playTurn() {
        Player currentPlayer = this.match.getPlayers().get(this.match.getCurrentPlayerIndex());

        // se player reale...
        /*  -> chiede al player se visitare lo shop
         *  -> aspetta la view per rollare dado
         *  -> (helmet)  reroll si/no
         *  -> (greaves) spostarsi avanti/indietro di una posizione
         *  -> effetto casella
         *  -> entra shop -> torna indietro di k + rarità_oggetto_comprato caselle
         *  -> finisce il turno con check win condition
         */

        //se cpu
        int diceRoll = this.playerController.rollDice(); // simula il lancio del dado tramite PlayerController
        // aggiungere logica per reroll
        System.out.println(currentPlayer.getName() + " ha tirato un " + diceRoll);

        this.playerController.movePlayer(currentPlayer, diceRoll, this.boardController); // muove il giocatore tramite PlayerController

        if (this.playerController.checkWinCondition(currentPlayer, this.boardController)) {
            System.out.println(currentPlayer.getName() + " ha vinto!");
            this.match.setGameOver(true);
            return;
        }

        /* try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
       
        System.out.println("\n ******************************************************************\n");
        this.nextTurn(); // passa al turno successivo
    }

    private void nextTurn() {
        int nextPlayerIndex = (match.getCurrentPlayerIndex() + 1) % match.getPlayers().size();
        this.match.setCurrentPlayerIndex(nextPlayerIndex);
    }
}
