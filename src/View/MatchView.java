package View;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
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
        this.rootPane.setMaxSize(1080, 520);
        this.rootPane.setCenter(boardView);
        
        

        // Imposta la BoardView
        

        // Aggiungi PlayerView o PawnView

        // Aggiungi ShopView

        // Aggiungi margini e padding
        rootPane.setPadding(new Insets(10));

        // Crea e mostra la scena
        Scene scene = new Scene(rootPane);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setTitle("Walk of War - Game Scene");
        this.stage.show();
    }
}
