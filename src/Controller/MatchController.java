package Controller;

import java.util.concurrent.TimeUnit;

import Model.Board;
import Model.Fight;
import Model.Match;
import Model.Player;
import Model.Shop;
import View.MatchView;
import javafx.stage.Stage;

import java.util.List;


// FATTO DA CHATGPT !!!!

public class MatchController {
    // COMPONENTI
    private Match match;
    private PlayerController playerController;
    private BoardController boardController;
    private ShopController shopController;
    private FightController fightController;

    // later
    private MatchView matchView; // conterrà un'aggregazione di tutte le view

    // INFO UTILI
    private int currentRoundNumber;


    // GPT ha detto che avrebbe più senso crearli direttamente qui i vari altri controller
    public MatchController(Match match, List<Player> players, Board board, Shop shop, Fight fight, Stage stage) {
        this.match = match;
        this.playerController = new PlayerController(players);
        this.boardController = new BoardController(board);
        this.shopController = new ShopController(shop);
        this.fightController = new FightController(fight);


        // da spostare su matchController
        for (int i = 0; i < board.getNumberOfTiles(); i++) {
            board.getTileAt(i).addTileEffectListener(playerController);
        }

        // later
        this.matchView = new MatchView(stage, this.boardController.getView(),
                                                this.shopController.getView(),
                                                this.fightController.getView());
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

    
    // da modificare: chiamare tipo handleTurnSystem() -> deve regolare lo svolgimento generale del gioco,
    // implementare takeTurn() su player per cose specifiche al player (tirare dado, muoversi, scela shop, reroll, mod. posiz. -> differenziare tra real e cpu)
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

        // DEBUG
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
        // gestione shop
        if (currentPlayer.isVisitingShop()) {
            // accedo allo Shop
        }
                                                        // fare funzione apposita per questi 2 step
        // gestione fight
        if (this.currentRoundNumber % 3 == 0) {
            // avviare il Fight
        }
        System.out.println("\n ******************************************************************\n");
        this.nextTurn(); // passa al turno successivo
    }

    private void nextTurn() {
        int nextPlayerIndex = (match.getCurrentPlayerIndex() + 1) % match.getPlayers().size();
        this.match.setCurrentPlayerIndex(nextPlayerIndex);
    }
}
