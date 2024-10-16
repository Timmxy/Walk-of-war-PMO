package View;

import java.security.KeyStore.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Model.Player;
import Model.Tile;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MatchView {
    
    // DICHIARAZIONE VARIABILI
    
    private Stage stage;
    private BorderPane rootPane;
    
    // Viste principali
    private BoardView boardView;
    private ShopView shopView;
    private FightView fightView;

    private List<Player> players;  // La lista dei giocatori nel match
    private Map<Player, Polygon> playersPawns;  // Mappa che associa un Player alla sua pedina grafica

    
    // COSTRUTTORE
    public MatchView(Stage s, BoardView bView, ShopView sView, FightView fView, List<Player> players) {
        this.stage = s;
        this.boardView = bView;
        this.shopView = sView;
        this.fightView = fView;
        this.players = players;
        this.playersPawns = new HashMap<>();

        // Inizializza la grafica delle pedine per ogni giocatore
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            Polygon playerPawn = createPlayerPawn(i + 1);
            this.playersPawns.put(player, playerPawn);
        }
        
        try {
            this.initializePanel();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private void initializePanel() throws Exception {
        this.rootPane = FXMLLoader.load(getClass().getResource("/Controller/MatchScene.fxml"));
        this.rootPane.setMaxSize(1280, 720);
        this.rootPane.setMinSize(1280, 720);
        this.rootPane.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        
        
        // Imposta la BoardView
        this.rootPane.setCenter(boardView);
        
        
        // Aggiungi PlayerView o PawnView
        for (Map.Entry<Player, Polygon> entry : playersPawns.entrySet()) {
            this.boardView.getChildren().add(entry.getValue());  // Aggiungi le pedine alla scena
        }
        // Aggiungi ShopView
        
        // Crea e mostra la scena
        Scene scene = new Scene(rootPane);
        this.stage.setScene(scene);
        this.stage.setTitle("Walk of War - Game Scene");
        this.stage.sizeToScene();   // adatta lo stage alla scena contenuta
        this.stage.show();
    }

    // metodo per creare una pedina grafica (triangolo con numero all'interno)
    private Polygon createPlayerPawn(int playerNumber) {
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
            0.0, 20.0,  // Punto in basso a sinistra
            10.0, 0.0,  // Punto in alto
            20.0, 20.0  // Punto in basso a destra
        );
        triangle.setRotate(90);
        triangle.setFill(Color.WHITE);  // Colore di riempimento bianco
        triangle.setStroke(Color.RED);  // Contorno rosso
        triangle.setStrokeWidth(2);

        // Aggiungi un testo con il numero del giocatore sopra il triangolo
        Text playerNumberText = new Text(String.valueOf(playerNumber));
        playerNumberText.setFill(Color.BLUE);
        playerNumberText.setX(5);  // Posiziona il testo
        playerNumberText.setY(15);

        // Crea uno StackPane per posizionare il numero sopra la pedina
        StackPane playerStack = new StackPane();
        playerStack.getChildren().addAll(triangle, playerNumberText);

        return triangle;
    }

    // Metodo per posizionare la pedina del giocatore su una Tile specifica
    public void movePlayerToTile(Player player) {
        Polygon playerPawn = playersPawns.get(player);  // Ottieni la pedina del giocatore
        if (playerPawn != null) {
            // Ottieni la posizione della Tile
            double tileX = boardView.getTilePosition(player.getPawnPosition()).getX();
            double tileY = boardView.getTilePosition(player.getPawnPosition()).getY();

            // Posiziona la pedina del giocatore sopra la Tile
            playerPawn.setLayoutX(tileX);  // Aggiusta posizione orizzontale
            playerPawn.setLayoutY(tileY);  // Aggiusta posizione verticale
        }
    }
}
