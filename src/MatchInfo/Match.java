package MatchInfo;
import java.util.ArrayList;

import Player.Player;

public class Match {

    //DICHIARAZIONE VARIABILI
    private ArrayList<Player> realPlayers = new ArrayList<>();
    private ArrayList<Player> cpuPlayers = new ArrayList<>();

    public Match(GameMode gameMode){
        this.gameSetup(gameMode);
    }

    private void gameSetup(GameMode g){

        //creo i player con le pedine
        //creo la board
        //creo lo shop 
        //creo "Fight"?

        //controllo quale GameMode e' stata scelta,
        //agisco di conseguenza
        switch (g) {
            case GameMode.P2    -> this.playersSetup(2, 0);
            case GameMode.P1_C1 -> this.playersSetup(1, 1);
            case GameMode.P4    -> this.playersSetup(4, 0);
            case GameMode.P3_C  -> this.playersSetup(3, 1);
            case GameMode.P2_C2 -> this.playersSetup(2, 2);
            case GameMode.P1_C3 -> this.playersSetup(1, 3);
            default -> {}
        }
    }

    private void playersSetup(int real, int cpu){
        for (int i = 0; i < real; i++) {
            this.realPlayers.add(new Player(i, "Player " + i));
        }

        for (int i = 0; i < cpu; i++) {
            this.cpuPlayers.add(new Player(i+real, "(CPU) Player " + i+real));
        }
    }
}