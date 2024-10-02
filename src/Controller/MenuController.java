package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

import MatchInfo.GameMode;
import Model.Match;

public class MenuController {

    // view
    private Stage stage;
    private Scene scene;
    private Parent root;


    public void leaderboards(ActionEvent e) {
        System.out.println("Accedendo alle Leaderboards...");
    }

    public void exitGame(ActionEvent e) {
        System.out.println("Uscendo dal Gioco...");
        this.stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        this.stage.close();
    }

    // metodo per inizializzare Match a seconda della GameMode scelta
    public void initializeMatch(final GameMode g, ActionEvent e) {
        
        //lanciare il pannello di gioco
        Stage currentStage = (Stage) ((MenuItem) e.getSource()).getParentPopup().getOwnerWindow();
        
        System.out.println("MenuController inizializza il match..");
        new Match(g, currentStage);
    }

    public void twoRealPlayers(ActionEvent e) {
        this.initializeMatch(GameMode.P2, e);
    }

    public void playerVsCPU(ActionEvent e) {
        this.initializeMatch(GameMode.P1_C1, e);
    }

    public void fourRealPlayers(ActionEvent e) {
        this.initializeMatch(GameMode.P4, e);
    }

    public void threePlayersVsCPU(ActionEvent e) {
        this.initializeMatch(GameMode.P3_C1, e);
    }

    public void twoPlayersVsTwoCPU(ActionEvent e) {
        this.initializeMatch(GameMode.P2_C2, e);
    }

    public void playerVsThreeCPU(ActionEvent e) {
        this.initializeMatch(GameMode.P1_C3, e);
    }


    public void switchToGameLauncherScene(ActionEvent e) {
        System.out.println("Ritorno al Menu principale...");
        try {
            this.root = FXMLLoader.load(getClass().getResource("/Controller/GameLauncher.fxml"));
            this.stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            this.scene = new Scene(this.root);
            this.stage.setScene(scene);
            this.stage.show();
        } catch (IOException ex) {
            // TODO: handle exception
        }
    }

    public void switchToGameModeSelectScene(ActionEvent e) {
        System.out.println("Seleziona una Modalità di Gioco...");
        try {
            this.root = FXMLLoader.load(getClass().getResource("/Controller/GameModeSelect.fxml"));
            this.stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            this.scene = new Scene(this.root);
            this.stage.setScene(scene);
            this.stage.show();
        } catch (IOException ex) {
            // TODO: handle exception
        }
    }
}
