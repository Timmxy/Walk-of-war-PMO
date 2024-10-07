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
    private FightView fightView;

    
    // COSTRUTTORE
    public MatchView(Stage s, BoardView bView, ShopView sView, FightView fView) {
        this.stage = s;
        this.boardView = bView;
        this.shopView = sView;
        this.fightView = fView;
        
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
        this.rootPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        
        // Imposta la BoardView
        this.rootPane.setCenter(boardView);
        
        
        // Aggiungi PlayerView o PawnView
        
        // Aggiungi ShopView
        
        // Crea e mostra la scena
        Scene scene = new Scene(rootPane);
        this.stage.setScene(scene);
        this.stage.setTitle("Walk of War - Game Scene");
        this.stage.sizeToScene();   // adatta lo stage alla scena contenuta
        this.stage.show();
    }
}
