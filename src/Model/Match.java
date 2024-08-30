package Model;
import java.util.ArrayList;
import java.util.List;

import Controller.BoardController;
import Controller.MatchController;
import Controller.PlayerController;
import Equipment.Chestplate;
import Equipment.Rarity;
import Equipment.Shield;
import Equipment.Weapon;
import MatchInfo.GameMode;

public class Match {

    //DICHIARAZIONE VARIABILI
    // Players
    private ArrayList<Player> realPlayers = new ArrayList<>();  
    private ArrayList<Player> cpuPlayers = new ArrayList<>();
    private ArrayList<Player> allPlayers = new ArrayList<>();   // contiene tutti i giocatori di una partita

    // Board
    private Board board;

    // Shop
    private Shop shop;

    // Controller
    private PlayerController playerController;
    private BoardController boardController;
    private MatchController matchController;

    private int currentPlayerIndex;
    private boolean isGameOver;


    public Match(GameMode gameMode){
        this.initGameMode(gameMode);

        this.playerController = new PlayerController(allPlayers);
        this.boardController = new BoardController(this.board);
        this.matchController = new MatchController(this, playerController, boardController);

        // non bellissimo, penso di spostarlo, non so come ancora però
        for (int i = 0; i < this.board.getNumberOfTiles(); i++) {
            this.board.getTileAt(i).addTileEffectListener(playerController);
        }

        // DEBUG
        if (this.allPlayers.size() == 2) {
            this.allPlayers.get(0).addItemToInventory(new Chestplate("Corazza Delvo", Rarity.COMMON));
            this.allPlayers.get(0).addItemToInventory(new Weapon("Pugnale Delvo", Rarity.COMMON));
            this.allPlayers.get(0).inventoryContents();
            this.allPlayers.get(0).printStats();


            this.allPlayers.get(1).addItemToInventory(new Chestplate("Corazza Deica", Rarity.RARE));
            this.allPlayers.get(1).addItemToInventory(new Shield("Scudo Deica", Rarity.RARE));
            this.allPlayers.get(1).inventoryContents();
            this.allPlayers.get(1).printStats();
        }

        // credo che dovrei avviare il gioco QUI
        this.matchController.startGame();
    }

    private void initGameMode(GameMode g){

        //creo i player con le pedine
        //creo la board
        //creo lo shop 
        //creo "Fight"?


        //debug
        System.out.println("Inizalizzando il gioco secondo la GameMode selezionata...");

        //controllo quale GameMode e' stata scelta,
        //agisco di conseguenza
        switch (g) {
            case GameMode.P2    -> this.gameSetup(2, 0);
            case GameMode.P1_C1 -> this.gameSetup(1, 1);
            case GameMode.P4    -> this.gameSetup(4, 0);
            case GameMode.P3_C1 -> this.gameSetup(3, 1);
            case GameMode.P2_C2 -> this.gameSetup(2, 2);
            case GameMode.P1_C3 -> this.gameSetup(1, 3);
            default -> {}
        }
    }

    // metodo generale per inizializzare tutta la partita
    private void gameSetup(int realPlayers, int cpuPlayers){
        
        this.playersSetup(realPlayers, cpuPlayers);
        boardSetup(realPlayers + cpuPlayers);
        //shopSetup();
    }
    
    //creazione player reali e cpu
    private void playersSetup(int real, int cpu){
        for (int i = 0; i < real; i++) {
            Player p = new Player(i, "Player " + i);
            this.allPlayers.add(p);
        }
        
        for (int i = 0; i < cpu; i++) {
            Player p = new Player(i+real, "(CPU) Player " + (i+real));
            this.allPlayers.add(p);
        }
    }

    // creazione e disposizione della Board di gioco
    private void boardSetup(int numPlayers){
        //renderla più grande per più giocatori???
        this.board = new Board();
    }

    // creazione dello Shop
    private void shopSetup(){
        this.shop = new Shop();
    }


    // CHATGPT !!!!!!!!!

    
    public boolean isGameOver() {
        return this.isGameOver;
    }

    public void setGameOver(boolean b) {
        this.isGameOver = b;
    }
    
    public List<Player> getPlayers() {
        return this.allPlayers;
    }
    
    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }
    
    public void setCurrentPlayerIndex(int i) {
        this.currentPlayerIndex = i;
    }
    
}