package View;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MatchView {
    
    // DICHIARAZIONE VARIABILI
    
    private Stage stage;
    private BorderPane rootPane;
    
    // Viste principali
    private BoardView boardView;
    private ShopView shopView;

    
    // COSTRUTTORE
    public MatchView(Stage s, BoardView boardView) {
        this.stage = s;
        this.boardView = boardView;
        try {
            this.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void initialize() throws Exception {
        this.rootPane = FXMLLoader.load(getClass().getResource("/Controller/MatchScene.fxml"));
        this.rootPane.setMaxSize(1280, 720);
        this.rootPane.setMinSize(1280, 720);
        this.rootPane.setCenter(boardView);
        this.rootPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        
        
        // Imposta la BoardView
        
        
        // Aggiungi PlayerView o PawnView
        
        // Aggiungi ShopView
        
        // Aggiungi margini e padding
        
        // Crea e mostra la scena
        Scene scene = new Scene(rootPane);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setTitle("Walk of War - Game Scene");
        this.stage.sizeToScene();
        this.stage.show();
    }
}
