package Model;
import java.util.ArrayList;

import Controller.PlayerController;
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


    public Match(GameMode gameMode){
        this.initGameMode(gameMode);
    }

    private void initGameMode(GameMode g){

        //creo i player con le pedine
        //creo la board
        //creo lo shop 
        //creo "Fight"?


        //debug
        System.out.println("sono dentro match");

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
            this.realPlayers.add(new Player(i, "Player " + i, playerController));
            this.playerController.addPlayer(realPlayers.get(i));
        }
        
        for (int i = 0; i < cpu; i++) {
            this.cpuPlayers.add(new Player(i+real, "(CPU) Player " + i+real, playerController));
            this.playerController.addPlayer(cpuPlayers.get(i));
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
}