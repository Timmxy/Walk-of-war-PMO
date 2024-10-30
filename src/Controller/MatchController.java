package Controller;


import Equipment.Equipment;
import Model.Board;
import Model.CPUPlayer;
import Model.Fight;
import Model.Match;
import Model.Player;
import Model.Shop;
import View.MatchView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


public class MatchController {
    private static final int MALUS_FIGHT = 2;

    // COMPONENTI
    private Match match;
    private PlayerController playerController;
    private BoardController boardController;
    private ShopController shopController;
    private FightController fightController;
    
    // View
    private MatchView matchView; // conterrà un'aggregazione di tutte le view
    
    // riferimenti a componenti della UI: da modificare per corretto MVC
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
    private int currentRoundNumber = 1;
    private Player currentPlayer;
    private int currentDiceResult;
    
    private Random rnd;
    private List<Player> nextOpponents;
    


    // COSTRUTTORE
    public MatchController(Match match, List<Player> players, Board board, Shop shop, Fight fight, Stage stage) {
        this.match = match;
        this.playerController = new PlayerController(players);
        this.boardController = new BoardController(board);
        this.shopController = new ShopController(shop, this);
        this.fightController = new FightController(fight, this);

        this.rnd = new Random();

        // imposto Listener per l'evento di attivazione casella
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

    // fa partire il primo turno di gioco
    public void startGame() {
        this.match.setGameOver(false);
        this.match.setCurrentPlayerIndex(0); // inizia con il primo giocatore
        System.out.println("Il gioco è cominciato!");
        
        this.currentPlayer = this.match.getPlayers().get(this.match.getCurrentPlayerIndex());
        this.handleCurrentTurn();
    }
    
    // sceglie quali player dare al Fight
    private void chooseFightPairing() {
        List<Player> fightPlayers = new ArrayList<>(this.match.getPlayers());
        // se ci sono solo 2 giocatori in gioco
        if (fightPlayers.size() == 2) {
            this.fightController.addPlayersToFight(fightPlayers.getFirst(), fightPlayers.getLast());
        } else {
            // ci sono 4 player
            Player p1 = fightPlayers.remove(this.rnd.nextInt(fightPlayers.size()));
            Player p2 = fightPlayers.remove(this.rnd.nextInt(fightPlayers.size()));
            
            this.nextOpponents = new ArrayList<Player>(fightPlayers);
            this.fightController.addPlayersToFight(p1, p2);
        }
    }

    // NUOVO GAMELOOP: non ci sarà più un while, ma si gestisce uno alla volta i Player e si passa al prossimo con un endTurn()
    private void handleCurrentTurn() {
        this.setupViewForNextTurn();
        // debug
        System.out.println("++ Turno di: "+this.currentPlayer.toString()+ " alla casella: " +this.currentPlayer.getPawnPosition());
        // se è il turno di un CPU
        if (this.currentPlayer instanceof CPUPlayer) {
            // disattivo i pulsanti sulla MatchView
            // gestisco le sue azioni:
            this.handleCPUTurn();
        }

        // se sono passati tre turni dall'ultimo Fight
        if (currentRoundNumber % 3 == 0) {
            this.disableAllButtons();
            // debug
            System.out.println("Sta cominciando il Fight");
            // inizializzare e far partire il Fight

            // mostro fight view
            this.matchView.displayFightPanel();
            // scelgo coppia di sfidanti per Fight
            this.chooseFightPairing();
        }
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

        // // aspetto un po'
        // try {
        //     TimeUnit.SECONDS.sleep(2);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }

        // [azione 1] -> scegliere se visitare lo Shop a fine turno
        visitingShop = this.playerController.getDecisionOnShopVisit((CPUPlayer)this.currentPlayer);
        
        // [azione 2] -> scegliere cosa fare nello Shop
        if (visitingShop) {
            // imposto lo shop
            this.shopController.visitShop(this.currentPlayer);
            // in base alla decisione dell'algoritmo assegno un valore alla variabile
            Optional<Equipment> toEquip = this.playerController.getDecisionOnShopPurchase((CPUPlayer)this.currentPlayer, this.shopController.getAvailableEquipments());

            // se ha comprato qualcosa
            if (!toEquip.isEmpty()) {
                this.playerController.equipmentBought(this.currentPlayer, toEquip.get());
                System.out.println(this.currentPlayer.toString()+ " ha comprato: "+toEquip.get().toString());
                System.out.println(this.currentPlayer.toString()+ " subisce il malus extra dello Shop: torna indietro di " +(Shop.SHOP_POSITION_MALUS+toEquip.get().getValue())+ " caselle!");
                this.playerController.movePlayer(currentPlayer, -(Shop.SHOP_POSITION_MALUS+toEquip.get().getValue()), this.boardController.getNumberOfTiles());
            } else {
                // non ha comprato
                System.out.println(this.currentPlayer.toString()+ " non ha comprato nulla");
                System.out.println(this.currentPlayer.toString()+ " subisce il malus base dello Shop: torna indietro di " +Shop.SHOP_POSITION_MALUS+ " caselle!");
                this.playerController.movePlayer(currentPlayer, -Shop.SHOP_POSITION_MALUS, this.boardController.getNumberOfTiles());
            }
        }

        // [azione 3] -> rollare il dado
        diceResult = this.rollDice(); // l'ho spostato su MatchController perchè non serviva necessariamente su PlayerController
        this.refreshInfoLabels();

        // [azione 4] -> scegliere se rirollare (abilità elmo)
        // devo passare sempre per playerController
        if (this.playerController.getDecisionOnReroll((CPUPlayer)this.currentPlayer, this.currentDiceResult))
        {
            diceResult = this.rollDice();
            this.refreshInfoLabels();
        }

        // [azione 5] -> muovere il giocatore
        this.playerController.movePlayer(this.currentPlayer, diceResult, this.boardController.getNumberOfTiles());
        // aggiorno posizione sulla View
        this.matchView.movePlayerToTile(this.currentPlayer);

        // [azione 6] -> scegliere se spostarsi leggermente (abilità gambali)
        this.playerController.movePlayer(this.currentPlayer,
                                            this.playerController.getDecisionOnPositionModifier((CPUPlayer) currentPlayer), // scelta se muoversi o no e in che direzione
                                            this.boardController.getNumberOfTiles());
        // quando ha finito di muoversi... si attiva l'effetto della casella
        this.boardController.performActionOnTile(this.currentPlayer);
        // aggiorno posizione sulla View
        this.matchView.movePlayerToTile(this.currentPlayer);

        // passa al turno successivo
        this.nextTurn();
    }

    // metodo per simulare il lancio del dado
    public int rollDice() {
        int result = this.rnd.nextInt(6) + 1; // Numero casuale tra 1 e 6
        System.out.println(this.currentPlayer.toString()+" ha tirato un: "+result);
        return result;
    }
    
    //#region METODI PER AZIONI DEI BOTTONI (interazione utente)
    
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
        this.disableAcceptRollButton();
        // aggiornare stats
        this.playerController.updateRerollsCount(this.currentPlayer);
        
        // rirollo il dado
        this.currentDiceResult = this.rollDice();
        this.refreshInfoLabels();
        
        // se non ha più reroll passo direttamente al movimento
        if (!this.currentPlayer.hasRerolls()) {
            // muovo la pedina
            this.playerController.movePlayer(this.currentPlayer, this.currentDiceResult, this.boardController.getNumberOfTiles());
            // controllo se  ha position modifiers, se sì attivo il bottone corrispondente, altrimenti procedo con attivare effetto casella
            this.checkPositionModifiers();
            // aggiorno posizione sulla View
            this.matchView.movePlayerToTile(this.currentPlayer);
            this.refreshInfoLabels();
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
        this.refreshInfoLabels();
        // controllo se  ha position modifiers, se sì attivo il bottone corrispondente, altrimenti procedo con attivare effetto casella
        this.checkPositionModifiers();
        // aggiorno posizione sulla View
        this.matchView.movePlayerToTile(this.currentPlayer);
    }
    
    private void greavesAbilityButtonClicked(int howToMove) {
        // aggiornare stats
        this.playerController.updatePosModifiersCount(this.currentPlayer);
        // muovere player secondo param scelto
        this.playerController.movePlayer(this.currentPlayer, howToMove, this.boardController.getNumberOfTiles());
        // attivare effetto casella
        this.boardController.performActionOnTile(this.currentPlayer);
        // aggiorno posizione sulla View
        this.matchView.movePlayerToTile(this.currentPlayer);
        this.refreshInfoLabels();
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
        // disattivo i bottoni
        this.disableAllButtons();
        
        this.shopController.visitShop(this.currentPlayer);
        this.matchView.displayShopPanel();
    }

    public void shopVisitEnded(Optional<Equipment> equipment) {
        // chiudo lo shop
        this.matchView.displayBoardPanel();
        // se è stato comprato qualcosa lo equipaggio
        if (!equipment.isEmpty()) {
            this.playerController.equipmentBought(currentPlayer, equipment.get());
            System.out.println(this.currentPlayer.toString()+ " ha comprato: "+equipment.get().toString());
            System.out.println(this.currentPlayer.toString()+ " subisce il malus extra dello Shop: torna indietro di " +(Shop.SHOP_POSITION_MALUS+equipment.get().getValue())+ " caselle!");
            this.playerController.movePlayer(currentPlayer, -(Shop.SHOP_POSITION_MALUS+equipment.get().getValue()), this.boardController.getNumberOfTiles());
        } else {
            System.out.println(this.currentPlayer.toString()+ " non ha comprato nulla");
            System.out.println(this.currentPlayer.toString()+ " subisce il malus base dello Shop: torna indietro di " +Shop.SHOP_POSITION_MALUS+ " caselle!");
            this.playerController.movePlayer(currentPlayer, -Shop.SHOP_POSITION_MALUS, this.boardController.getNumberOfTiles());
        }
        this.matchView.movePlayerToTile(currentPlayer);
        // riattivo roll dice button
        this.enableRollDiceButton();
        // refresh label
        this.refreshInfoLabels();
    }
    
    public void endTurnButtonClicked() {
        // disattivo il bottone
        this.disableEndTurnButton();
        // passo al prossimo turno
        this.nextTurn();
    }
    
    private void checkPositionModifiers() {
        // se non ha position modifiers (quindi questa è la posizione finale)
        if (!this.currentPlayer.hasPositionModifiers()) {
            // attivo effetto casella
            this.boardController.performActionOnTile(this.currentPlayer);
            this.refreshInfoLabels();
        }
        else {
            // attivo bottone "Use Greaves Ability"
            this.enableGreavesAbilityButton();
        }
    }
    //#endregion

    //#region DISABLERS
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
    //#endregion

    //#region ENABLERS
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
    //#endregion

    //#region gestione componenti UI
    // permette di passare un controller già creato alla view del Match
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
    
    // mostra il risultato del lancio corrente
    public void displayDiceResult() {
        this.diceResultText.setText("You rolled a: "+this.currentDiceResult);
    }

    // aggiorna tutti i Label
    private void refreshInfoLabels() {
        this.displayDiceResult();
        this.currentPlayerTurn.setText(this.currentPlayer.getName()+ " Turn");
        this.currentPlayerRerolls.setText("Number of Rerolls: "+this.currentPlayer.getNumberOfRerolls());
        this.currentPlayerPosModifiers.setText("Number of Position Modifiers: "+this.currentPlayer.getNumberOfPosModifiers());
        this.currentPlayerMoney.setText("Money: "+this.currentPlayer.getMoney());
    }
    //#endregion


    // gestisce la fine di questo turno e imposta il prossimo
    private void nextTurn() {
        this.refreshInfoLabels();
        // se il giocatore corrente non ha vinto, si passa al prossimo turno
        System.out.println("NUMERO MAX DI CASELLE: "+this.boardController.getNumberOfTiles());
        System.out.println("posizione giocatore corrente: "+ this.currentPlayer.getPawnPosition());
        if (!this.playerController.checkWinCondition(this.currentPlayer, this.boardController.getNumberOfTiles())) {
            
            // aggiorno i parametri del turno corrente
            // resetto diceResult a 0
            this.currentDiceResult = 0;
            

            // imposto indice del Player successivo
            int nextPlayerIndex = (match.getCurrentPlayerIndex() + 1) % match.getPlayers().size();
            this.match.setCurrentPlayerIndex(nextPlayerIndex);
            // imposto prossimo player
            this.currentPlayer = this.match.getPlayers().get(this.match.getCurrentPlayerIndex());
            

            // se sono tornato al player 0
            if (this.match.getCurrentPlayerIndex() == 0) {
                // divisore turni
                System.out.println("-------------------------------------------------");
                // ho finito un round, quindi incremento il contatore
                this.currentRoundNumber++;
                System.out.println("***************** Inizia il Round "+this.currentRoundNumber+" *****************");
            }

            // divisore turni
            System.out.println("-------------------------------------------------");
            // gestisco il turno successivo
            this.handleCurrentTurn();
        }
        else {
            System.out.println("Il gioco è finito! Vince il giocatore: "+this.currentPlayer.getName());
        }
    }

    public void fightEnded(Player player) {
        System.out.println(player.toString()+" ha perso il Fight! Arretra di 5 caselle.");
        this.playerController.movePlayer(player, -MatchController.MALUS_FIGHT, this.boardController.getNumberOfTiles());
        this.matchView.movePlayerToTile(player);
        if (this.nextOpponents != null && !this.nextOpponents.isEmpty()) {
            // faccio partire l'altro fight
            this.fightController.addPlayersToFight(this.nextOpponents.removeFirst(), this.nextOpponents.removeLast());
        } else {
            // riprendo il gioco dalla Board
            this.matchView.displayBoardPanel();
            this.setupViewForNextTurn();
        }
    }
}
