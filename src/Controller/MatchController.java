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
    private Player currentPlayer;
    private int currentDiceResult;
  
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
                nextTurn();
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
        // attivo solo pulsanti "Roll Dice" e "Visit Shop"
    }
    
    
    
    // NUOVO GAMELOOP: non ci sarà più un while, ma si gestisce uno alla volta i Player e si passa al prossimo con un endTurn()
    private void handleCurrentTurn() {
        // se è il turno di un CPU
        // TODO: mettere tutto il contenuto dell'if dentro il metodo handleCpuTurn()
        if (this.currentPlayer instanceof CPUPlayer) {
            // disattivo i pulsanti sulla MatchView
            // gestisco le sue azioni:
            this.handleCPUTurn();
        }
        else if (this.currentPlayer instanceof RealPlayer) {
            // TODO: gestire utente reale
        }
        
        // se sono passati tre turni dall'ultimo Fight
        if (currentRoundNumber % 3 == 0) {
            // inizializzare e far partire il Fight
            // TODO
        }
        
        // DEBUG
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("\n ******************************************************************\n");
        this.nextTurn(); // passa al turno successivo
    }
    
    // metodo per gestire l'algoritmo di tutte le scelte che deve compiere CPU Player durante il suo turno
    public void handleCPUTurn() {
        // VARIABILI LOCALI
        boolean visitingShop;
        int diceResult;
        
        // CORPO
        // [azione 1] -> scegliere se visitare lo Shop a fine turno
        visitingShop = this.playerController.getDecisionOnShopVisit((CPUPlayer)this.currentPlayer);

        // [azione 2] -> rollare il dado
        diceResult = this.rollDice(); // l'ho spostato su MatchController perchè non serviva necessariamente su PlayerController

        // [azione 3] -> scegliere se rirollare (abilità elmo)
        // devo passare sempre per playerController
        if (this.playerController.getDecisionOnReroll((CPUPlayer)this.currentPlayer))
        {
            diceResult = this.rollDice();
        }

        // [azione 4] -> muovere il giocatore
        this.playerController.movePlayer(this.currentPlayer, diceResult, this.boardController.getNumberOfTiles());

        // [azione 5] -> scegliere se spostarsi leggermente (abilità gambali)
        this.playerController.movePlayer(this.currentPlayer,
                                            this.playerController.getDecisionOnPositionModifier((CPUPlayer) currentPlayer), // scelta se muoversi o no e in che direzione
                                            this.boardController.getNumberOfTiles());
        // quando ha finito di muoversi... si attiva l'effetto della casella
        this.boardController.performActionOnTile(this.currentPlayer);

        // se ha deciso di visitare lo Shop
        if (visitingShop) {
            // [azione 6] -> scegliere cosa fare nello Shop
            // TODO
            // metodo: shop mi ritorna la lista dei contenuti, la passo a CPU player che sceglie cosa fare e mi ritorna cosa ha comprato, chiamo playercontroller e gli dico di equipaggiare
        }
    }

    // metodo per simulare il lancio del dado
    public int rollDice() {
        return this.rnd.nextInt(6) + 1; // Numero casuale tra 1 e 6
    }
    
    //#region METODI PER COMUNICAZIONE VIEW
    
    // quando clicco il bottone Roll Dice nella View
    public void rollDiceButtonClicked() {
        this.currentDiceResult = this.rollDice();
        // chiedo alla View di visualizzare l'esito
        this.matchView.displayDiceResult(this.currentDiceResult);
        
        // disattivo questo bottone
        this.matchView.disableRollDiceButton();
        
        // se non ha rerolls
        if (!this.currentPlayer.hasRerolls()) {
            this.playerController.movePlayer(this.currentPlayer, this.currentDiceResult, this.boardController.getNumberOfTiles());
            // controllo se  ha position modifiers, se sì attivo il bottone corrispondente, altrimenti procedo con attivare effetto casella
            this.checkPositionModifiers();
        }
        else {
            // attivo bottone reroll (se ha i reroll)
            this.matchView.enableRerollDiceButton();
        }
    }
    
    // quando clicco il bottone Reroll Dice nella View
    public void helmetAbilityButtonClicked() {
        // rirollo il dado
        this.currentDiceResult = this.rollDice();
        
        // chiedo alla View di visualizzare l'esito
        this.matchView.displayDiceResult(this.currentDiceResult);
        
        // se non ha più reroll passo direttamente al movimento
        if (!this.currentPlayer.hasRerolls()) {
            // disattivo bottone Reroll Dice
            this.matchView.disableRerollDiceButton();
            // muovo la pedina
            this.playerController.movePlayer(this.currentPlayer, this.currentDiceResult, this.boardController.getNumberOfTiles());
            // controllo se  ha position modifiers, se sì attivo il bottone corrispondente, altrimenti procedo con attivare effetto casella
            this.checkPositionModifiers();
        }
    }
    
    // attivato da un bottone che deve spawnare solo dopo reroll
    public void confirmReroll() {
        // bottone di conferma per poter muovere la pedina dopo reroll (solo se ha altri reroll rimanenti)
        this.playerController.movePlayer(this.currentPlayer, this.currentDiceResult, this.boardController.getNumberOfTiles());
        // controllo se  ha position modifiers, se sì attivo il bottone corrispondente, altrimenti procedo con attivare effetto casella
        this.checkPositionModifiers();
    }
    
    public void greavesAbilityButtonClicked(int howToMove) {
        // muovere player secondo param scelto
        this.playerController.movePlayer(this.currentPlayer, howToMove, this.boardController.getNumberOfTiles());
        // attivare effetto casella
        this.boardController.performActionOnTile(this.currentPlayer);
    }
    
    public void visitShopButtonClicked() {
        // TODO 
        this.currentPlayer.setVisitingShop(true);
    }
    
    public void endTurnButtonClicked() {
        // passo al prossimo turno
        this.nextTurn();
    }
    
    private void checkPositionModifiers() {
        // se non ha position modifiers (quindi questa è la posizione finale)
        if (!this.currentPlayer.hasPositionModifiers()) {
            // attivo effetto casella
            this.boardController.performActionOnTile(this.currentPlayer);
        }
        else {
            // attivo bottone "Use Greaves Ability"
            this.matchView.enableGreavesAbilityButton();
        }
    }
    
    //#endregion

    private void nextTurn() {
        // aggiorno i parametri del turno corrente
        // resetto diceResult a 0
        this.currentDiceResult = 0;
        // passo al prossimo Player
        this.currentPlayer = this.match.getPlayers().get(this.match.getCurrentPlayerIndex());

        if (this.currentPlayer instanceof CPUPlayer) {
            // disattivo la ui
            this.matchView.disableAllButtons();
        }
        // imposto indice del Player successivo
        int nextPlayerIndex = (match.getCurrentPlayerIndex() + 1) % match.getPlayers().size();
        this.match.setCurrentPlayerIndex(nextPlayerIndex);
        // gestisco il turno successivo
        this.handleCurrentTurn();
    }
}
