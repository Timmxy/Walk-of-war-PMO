package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;


public class GameLauncher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        
        // impostare gestore nodi: menu. Deriva da file fxml modificato con SceneBuilder
        Parent menu = FXMLLoader.load(getClass().getResource("/Controller/GameLauncher.fxml"));
        Scene scene = new Scene(menu);
        
        //impostare l'icona della finestra
        Image icon = new Image("/Images/Window_Logo.jpg");
        stage.getIcons().add(icon);
        // impostare titolo della finestra
        stage.setTitle("Walk of War - Game Launcher");
        // impostare la dimensione della finestra
        stage.setWidth(1280);
        stage.setHeight(720);
        // impostare la posizione in cui apparirà su schermo
        stage.setX(400); 
        stage.setY(150);
        stage.setResizable(false);
        
        // impostazioni modalità schermo intero
        stage.setFullScreen(false);
        stage.setFullScreenExitHint("Attivata la modalità a Schermo Intero.\nPremere 'Q' per uscire.");
        // per cambiare binding uscita fullscreen
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));
        
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        //avviare il menu' -> scelta della modalita' di gioco
        //MatchController prende info dalla gamemode scelta (click) -> crea Match
        //Match crea tutto il necessario 
        
        System.out.println("Launching...");
        // Con JavaFX !!!
        Application.launch(args);
    }
}
