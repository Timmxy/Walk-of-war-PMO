package MatchInfo;

import Controller.MenuController;
import View.Window;

public class GameLauncher {

    public static void main(String[] args) {
        //avviare il menu' -> scelta della modalita' di gioco
        //MatchController prende info dalla gamemode scelta (click) -> crea Match
        //Match crea tutto il necessario 

        MenuController menuController = new MenuController();
        Window window = new Window(menuController);
    }
}
