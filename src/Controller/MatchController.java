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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;

import java.nio.channels.Pipe.SourceChannel;
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
    @FXML
    private Button rollDiceButton;
    @FXML
    private Button rerollDiceButton;
    @FXML
    private Button acceptRerollButton;
    @FXML
    private SplitMenuButton greavesAbilityButton;
    @FXML
    private Button visitShopButton;
    @FXML
    private Button endTurnButton;
    @FXML
    private Label currentPlayerTurn;
    @FXML
    private Label diceResultText;
    @FXML
    private Label currentPlayerRerolls;
    @FXML
    private Label currentPlayerPosModifiers;
    @FXML
    private Label currentPlayerMoney;

    // INFO UTILI
    private int currentRoundNumber;
    private Random rnd;


    public MatchController(Match match, List<Player> players, Board board, Shop shop, Fight fight, Stage stage) {
        this.match = match;
        this.playerController = new PlayerController(players);
        this.boardController = new BoardController(board);
        this.shopController = new ShopController(shop);
        this.fightController = new FightController(fight);

        this.rnd = new Random();


        // da spostare su matchController
        for (int i = 0; i < board.getNumberOfTiles(); i++) {
            board.getTileAt(i).addTileEffectListener(playerController);
        }

        // creo la View
        this.matchView = new MatchView(stage, this.boardController.getView(),
                                                this.shopController.getView(),
                                                this.fightController.getView(),
                                                players);

        
        // per inizializzare il pannello dela View                                       
        this.setControllerForView();
    }

    public void startGame() {
        this.match.setGameOver(false);
        this.match.setCurrentPlayerIndex(0); // inizia con il primo giocatore
        
        this.nextTurn();

        System.out.println("Il gioco è cominciato!");
        // tornare al menu principale / rigioca
    }
    
    // NUOVO GAMELOOP: non ci sarà più un while, ma si gestisce uno alla volta i Player e si passa al prossimo con un endTurn()
    private void handleCurrentTurn() {
        // debug
        System.out.println("Turno di: "+this.currentPlayer.toString());

        // se è il turno di un CPU
        if (this.currentPlayer instanceof CPUPlayer) {
            // disattivo i pulsanti sulla MatchView
            // gestisco le sue azioni:
            this.handleCPUTurn();
        }
        
        // se sono passati tre turni dall'ultimo Fight
        if (currentRoundNumber % 3 == 0) {
            // debug
            System.out.println("Sta cominciando il Fight");
            // inizializzare e far partire il Fight

            // TODO: gestire evento fight
        }
        System.out.println("\n ******************************************************************\n");
    }
    
    // metodo per gestire l'algoritmo di tutte le scelte che deve compiere CPU Player durante il suo turno
    public void handleCPUTurn() {

        // TODO: fixare aggiornamento view nei turni della cpu -> ora si freeza, bisognerebbe tornare a separare le cose con due thread
        // VARIABILI LOCALI
        boolean visitingShop;
        int diceResult;
        
        // CORPO
        // aggiorno labels
        this.refreshInfoLabels();
        // aspetto un po'
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // [azione 1] -> scegliere se visitare lo Shop a fine turno
        visitingShop = this.playerController.getDecisionOnShopVisit((CPUPlayer)this.currentPlayer);

        // [azione 2] -> rollare il dado
        diceResult = this.rollDice(); // l'ho spostato su MatchController perchè non serviva necessariamente su PlayerController
        this.diceResultText.setText("You rolled a: "+diceResult);

        // [azione 3] -> scegliere se rirollare (abilità elmo)
        // devo passare sempre per playerController
        if (this.playerController.getDecisionOnReroll((CPUPlayer)this.currentPlayer))
        {
            diceResult = this.rollDice();
            this.diceResultText.setText("You rolled a: "+diceResult);
        }

        // [azione 4] -> muovere il giocatore
        this.playerController.movePlayer(this.currentPlayer, diceResult, this.boardController.getNumberOfTiles());
        // aggiorno posizione sulla View
        this.matchView.movePlayerToTile(this.currentPlayer);

        // [azione 5] -> scegliere se spostarsi leggermente (abilità gambali)
        this.playerController.movePlayer(this.currentPlayer,
                                            this.playerController.getDecisionOnPositionModifier((CPUPlayer) currentPlayer), // scelta se muoversi o no e in che direzione
                                            this.boardController.getNumberOfTiles());
        // quando ha finito di muoversi... si attiva l'effetto della casella
        this.boardController.performActionOnTile(this.currentPlayer);
        // aggiorno posizione sulla View
        this.matchView.movePlayerToTile(this.currentPlayer);

        // se ha deciso di visitare lo Shop
        if (visitingShop) {
            // debug
            System.out.println("Sto accedendo allo Shop...");
            // [azione 6] -> scegliere cosa fare nello Shop

            // TODO: gestire scelta azioni shop (CPU)

            // metodo: shop mi ritorna la lista dei contenuti, la passo a CPU player che sceglie cosa fare e mi ritorna cosa ha comprato, chiamo playercontroller e gli dico di equipaggiare
        }

        // passa al turno successivo
        this.nextTurn(); // passa al turno successivo
    }

    // metodo per simulare il lancio del dado
    public int rollDice() {
        return this.rnd.nextInt(6) + 1; // Numero casuale tra 1 e 6
    }
    
    //#region METODI PER COMUNICAZIONE VIEW
    
    // quando clicco il bottone Roll Dice nella View
    public void rollDiceButtonClicked() {
        // disattivo questo bottone
        this.disableRollDiceButton();
        // disattivo bottone Shop
        this.disableVisitShopButton();
        this.currentDiceResult = this.rollDice();
        // chiedo alla View di visualizzare l'esito
        this.displayDiceResult();
        
        // attivo tasto per finire turno
        this.enableEndTurnButton();
        
        // se non ha rerolls
        if (!this.currentPlayer.hasRerolls()) {
            this.playerController.movePlayer(this.currentPlayer, this.currentDiceResult, this.boardController.getNumberOfTiles());
            // controllo se  ha position modifiers, se sì attivo il bottone corrispondente, altrimenti procedo con attivare effetto casella
            this.checkPositionModifiers();
            // aggiorno posizione sulla View
            this.matchView.movePlayerToTile(this.currentPlayer);
        }
        else {
            // attivo bottone reroll (se ha i reroll)
            this.enableRerollDiceButton();
        }
    }

    // quando clicco il bottone Reroll Dice nella View
    public void helmetAbilityButtonClicked() {
        // disattivo bottone Reroll Dice
        this.disableRerollDiceButton();
        // aggiornare stats
        this.playerController.updateRerollsCount(this.currentPlayer);
        // rirollo il dado
        this.currentDiceResult = this.rollDice();
        
        // chiedo alla View di visualizzare l'esito
        this.displayDiceResult();
        
        // se non ha più reroll passo direttamente al movimento
        if (!this.currentPlayer.hasRerolls()) {
            // muovo la pedina
            this.playerController.movePlayer(this.currentPlayer, this.currentDiceResult, this.boardController.getNumberOfTiles());
            // controllo se  ha position modifiers, se sì attivo il bottone corrispondente, altrimenti procedo con attivare effetto casella
            this.checkPositionModifiers();
            // aggiorno posizione sulla View
            this.matchView.movePlayerToTile(this.currentPlayer);
        }
        else {
            this.enableAcceptRerollButton();
            this.enableRerollDiceButton();
        }
    }

    // attivato da un bottone che deve spawnare solo dopo reroll
    public void confirmReroll() {
        // disattivo il bottone
        this.disableAcceptRollButton();
        // bottone di conferma per poter muovere la pedina dopo reroll (solo se ha altri reroll rimanenti)
        this.playerController.movePlayer(this.currentPlayer, this.currentDiceResult, this.boardController.getNumberOfTiles());
        // controllo se  ha position modifiers, se sì attivo il bottone corrispondente, altrimenti procedo con attivare effetto casella
        this.checkPositionModifiers();
        // aggiorno posizione sulla View
        this.matchView.movePlayerToTile(this.currentPlayer);
    }
    
    private void greavesAbilityButtonClicked(int howToMove) {
        // muovere player secondo param scelto
        this.playerController.movePlayer(this.currentPlayer, howToMove, this.boardController.getNumberOfTiles());
        // attivare effetto casella
        this.boardController.performActionOnTile(this.currentPlayer);
        // aggiorno posizione sulla View
        this.matchView.movePlayerToTile(this.currentPlayer);
    }

    public void greavesAbilityActionForward() {
        // disattivo il bottone
        this.disableGreavesAbilityButton();
        this.greavesAbilityButtonClicked(1);
    }

    public void greavesAbilityActionBack() {
        // disattivo il bottone
        this.disableGreavesAbilityButton();
        this.greavesAbilityButtonClicked(-1);
    }
    
    public void visitShopButtonClicked() {
        // disattivo il bottone
        this.disableVisitShopButton();

        // TODO: gestire logica shop

        this.currentPlayer.setVisitingShop(true);
    }
    
    public void endTurnButtonClicked() {
        // disattivo il bottone
        this.disableEndTurnButton();
        // resetto flag per shop
        this.currentPlayer.setVisitingShop(false);
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
            this.enableGreavesAbilityButton();
        }
    }

    public void displayDiceResult() {
        this.diceResultText.setText("You rolled a: "+this.currentDiceResult);
    }
    // DISABLERS
    private void disableAllButtons() {
        this.disableRollDiceButton();
        this.disableRerollDiceButton();
        this.disableGreavesAbilityButton();
        this.disableVisitShopButton();
        this.disableAcceptRollButton();
        this.disableEndTurnButton();
    }
    
    private void disableAcceptRollButton() {
        this.acceptRerollButton.setDisable(true);
    }

    private void disableRollDiceButton() {
        this.rollDiceButton.setDisable(true);
    }
    
    private void disableRerollDiceButton() {
        this.rerollDiceButton.setDisable(true);
    }
    
    private void disableGreavesAbilityButton() {
        this.greavesAbilityButton.setDisable(true);
    }

    private void disableVisitShopButton() {
        this.visitShopButton.setDisable(true);
    }

    private void disableEndTurnButton() {
        this.endTurnButton.setDisable(true);
    }

    // ENABLERS
    private void enableVisitShopButton() {
        this.visitShopButton.setDisable(false);
    }

    private void enableRollDiceButton() {
        this.rollDiceButton.setDisable(false);
    }

    private void enableAcceptRerollButton() {
        this.acceptRerollButton.setDisable(false);
    }

    private void enableRerollDiceButton() {
        this.rerollDiceButton.setDisable(false); 
    }

    private void enableGreavesAbilityButton() {
        this.greavesAbilityButton.setDisable(false);
    }

    private void enableEndTurnButton() {
        this.endTurnButton.setDisable(false);
    }

    private void setControllerForView() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MatchScene.fxml"));
        loader.setController(this);
        try {
            this.matchView.initializePanel(loader);   
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private void setupViewForNextTurn() {
        if (this.currentPlayer instanceof CPUPlayer) {
            // disattivo la ui
            this.disableAllButtons();
        }
        else {
            this.disableAllButtons();
            this.enableVisitShopButton();
            this.enableRollDiceButton();
            // aggiornare informazioni contestuali nei label
            this.refreshInfoLabels();
            
        }
    }
    
    private void refreshInfoLabels() {
        this.currentPlayerTurn.setText(this.currentPlayer.getName()+ " Turn");
        this.diceResultText.setText("You rolled a: ");
        this.currentPlayerRerolls.setText("Number of Rerolls: "+this.currentPlayer.getNumberOfRerolls());
        this.currentPlayerPosModifiers.setText("Number of Position Modifiers: "+this.currentPlayer.getNumberOfPosModifiers());
        this.currentPlayerMoney.setText("Money: "+this.currentPlayer.getMoney());
    }
    //#endregion


    private void nextTurn() {
        // TODO: penso si possa togliere campo isGameOver in Match
        // se il giocatore corrente non ha vinto, si passa al prossimo turno
        if (!this.playerController.checkWinCondition(this.currentPlayer, this.boardController.getNumberOfTiles())) {
            // aggiorno i parametri del turno corrente
            // resetto diceResult a 0
            this.currentDiceResult = 0;
            // passo al prossimo Player
            this.currentPlayer = this.match.getPlayers().get(this.match.getCurrentPlayerIndex());
    
            // gestisco bottoni e label della view
            this.setupViewForNextTurn();
            // imposto indice del Player successivo
            int nextPlayerIndex = (match.getCurrentPlayerIndex() + 1) % match.getPlayers().size();
            this.match.setCurrentPlayerIndex(nextPlayerIndex);
            // se sono tornato al player 0
            if (nextPlayerIndex == 0) {
                // ho finito un round, quindi incremento il contatore
                this.currentRoundNumber++;
                // TODO: questo campo potrebbe stare in Match
            }
            // gestisco il turno successivo
            this.handleCurrentTurn();
        }
        else {
            System.out.println("Il gioco è finito! Vince il giocatore: "+this.currentPlayer.getName());
        }
    }
}
