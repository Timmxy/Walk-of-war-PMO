package Controller;

import java.util.concurrent.TimeUnit;

import Model.Board;
import Model.CPUPlayer;
import Model.Fight;
import Model.Match;
import Model.Player;
import Model.RealPlayer;
import Model.Shop;
import View.MatchView;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;


// FATTO DA CHATGPT !!!!

public class MatchController {
    // COMPONENTI
    private Match match;
    private PlayerController playerController;
    private BoardController boardController;
    private ShopController shopController;
    private Shop shop;
    private FightController fightController;
    private RealPlayer realPlayer;
    private CPUPlayer cpuPlayer;

    // View
    private MatchView matchView; // conterrà un'aggregazione di tutte le view

    // INFO UTILI
    private int currentRoundNumber;
    private Random rnd;


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
                                                this.fightController.getView(),
                                                players);
    }

    public void startGame() {
        this.match.setGameOver(false);
        this.match.setCurrentPlayerIndex(0); // inizia con il primo giocatore

        
        
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Logica di gioco che non coinvolge la GUI
                handleTurnSystem();
                return null;
            }
        };

        new Thread(task).start();  // Esegui in un thread separato
        
        System.out.println("Il gioco è finito!");
        // tornare al menu principale / rigioca
    }

    // metodo gestire le scelte dei Player Reali durante il turno (effettuate tramite interazione utente con la View)
    public void handlePlayerTurn(Player genericPlayer) {
        //wait del click del giocatore
        // TODO
    }
    
    // metodo per gestire l'algoritmo di tutte le scelte che deve compiere CPU Player durante il suo turno
    public void handleCPUTurn() {
        // TODO
    }
    

    // NUOVO GAMELOOP: non ci sarà più un while, ma si gestisce uno alla volta i Player e si passa al prossimo con un endTurn()
    private void handleTurnSystem() {
        // passo al prossimo Player
        Player currentPlayer = this.match.getPlayers().get(this.match.getCurrentPlayerIndex());
        boolean visitingShop;
        int diceResult;
        int currentPlayerPosition;
        // se è il turno di un CPU
        // TODO: mettere tutto il contenuto dell'if dentro il metodo handleCpuTurn()
        if (currentPlayer instanceof CPUPlayer) {
            // gestisco le sue azioni:
            // [azione 1] -> scegliere se visitare lo Shop a fine turno
            visitingShop = this.playerController.getDecisionOnShopVisit((CPUPlayer)currentPlayer);

            // [azione 2] -> rollare il dado
            diceResult = this.rollDice(); // l'ho spostato su MatchController perchè non serviva necessariamente su PlayerController

            // [azione 3] -> scegliere se rirollare (abilità elmo)
            // devo passare sempre per playerController
            if (this.playerController.getDecisionOnReroll((CPUPlayer)currentPlayer))
            {
                diceResult = this.rollDice();
            }

            // [azione 4] -> muovere il giocatore
            currentPlayerPosition = this.playerController.movePlayer(currentPlayer, diceResult, this.boardController.getNumberOfTiles());

            // [azione 5] -> scegliere se spostarsi leggermente (abilità gambali)
            currentPlayerPosition = this.playerController.movePlayer(currentPlayer,
                                                                     this.playerController.getDecisionOnPositionModifier((CPUPlayer) currentPlayer), // scelta se muoversi o no e in che direzione
                                                                     this.boardController.getNumberOfTiles());
            // quando ha finito di muoversi... si attiva l'effetto della casella
            this.boardController.performActionOnTile(currentPlayer, currentPlayerPosition);

            // se ha deciso di visitare lo Shop
            if (visitingShop) {
                // [azione 6] -> scegliere cosa fare nello Shop
                // TODO
                // metodo: shop mi ritorna la lista dei contenuti, la passo a CPU player che sceglie cosa fare e mi ritorna cosa ha comprato, chiamo playercontroller e gli dico di equipaggiare
            }
        }

        // se sono passati tre turni dall'ultimo Fight
        if (currentRoundNumber % 3 == 0) {
            // inizializzare e far partire il Fight
            // TODO
        }
    }


    // da modificare: chiamare tipo handleTurnSystem() -> deve regolare lo svolgimento generale del gioco,
    // implementare takeTurn() su player per cose specifiche al player (tirare dado, muoversi, scela shop, reroll, mod. posiz. -> differenziare tra real e cpu)
    private void handleTurnSyyystem() {
        while (!this.match.isGameOver()) {
            Player currentPlayer = this.match.getPlayers().get(this.match.getCurrentPlayerIndex());

            currentPlayer.playTurn();
            if(currentPlayer.wantsToVisitShop()){
            }
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
            /*
        this.movePlayer(this, diceRoll, boardController); // muove il giocatore tramite PlayerController
        this.matchView.movePlayerToTile(currentPlayer);

        if (this.playerController.checkWinCondition(currentPlayer, this.boardController)) {
            System.out.println(currentPlayer.getName() + " ha vinto!");
            this.match.setGameOver(true);
            return;
        }

        // DEBUG
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

        // gestione shop
        if (this.isVisitingShop()) {
            // accedo allo Shop
        }
                                                        // fare funzione apposita per questi 2 step
        // gestione fight
        if (this.currentRoundNumber % 3 == 0) {
            // avviare il Fight
        }
        System.out.println("\n ******************************************************************\n");
        this.nextTurn(); // passa al turno successivo
        */
        }
    }

    private void nextTurn() {
        int nextPlayerIndex = (match.getCurrentPlayerIndex() + 1) % match.getPlayers().size();
        this.match.setCurrentPlayerIndex(nextPlayerIndex);
    }

    // metodo per simulare il lancio del dado
    public int rollDice() {
        return this.rnd.nextInt(6) + 1; // Numero casuale tra 1 e 6
    }
}
