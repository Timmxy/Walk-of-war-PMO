package Model;
import java.util.ArrayList;
import java.util.List;

import MatchInfo.GameMode;

import Controller.MatchController;

import javafx.stage.Stage;

public class Match {

    // per una CORRETTA IMPLEMENTAZIONE MVC -> creare qui dentro tutti i Model
    //                                      -> tenere qui dentro solo MatchController a cui passiamo i Model
    //                                      -> MatchController crea i vari Controller specifici
    //                                      -> Controller specifici creano le proprie View                                                  

    //DICHIARAZIONE VARIABILI
    // Players
    private ArrayList<Player> allPlayers = new ArrayList<>();   // contiene tutti i giocatori di una partita

    // Board
    private Board board;

    // Shop
    private Shop shop;

    // Fight
    private Fight fight;

    // Controller
    private MatchController matchController;

    // View va dentro i controller

    private int currentPlayerIndex;
    private boolean isGameOver;


    public Match(GameMode gameMode, Stage stage){   
        this.initGameMode(gameMode);
        // a questo punto tutti i Model di gioco dovrebbero essere stati creati, li passo al Controller

        this.matchController = new MatchController(this, this.allPlayers, this.board, this.shop, this.fight, stage);
        
        // credo che dovrei avviare il gioco QUI
        //this.matchController.startGame();
    }

    private void initGameMode(GameMode g){

        //creo i player con le pedine
        //creo la board
        //creo lo shop 
        //creo "Fight"?

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
        this.boardSetup(realPlayers + cpuPlayers);
        this.shopSetup();
    }
    
    // creazione player reali e cpu
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
        //renderla più grande per più giocatori??? NO
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