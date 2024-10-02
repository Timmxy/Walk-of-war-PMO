package View;

import Model.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.List;

public class BoardView extends Pane {

    private static final int TILE_SIZE = 50; // Dimensione di ogni tile
    private static final int BOARD_PADDING = 30; // Padding attorno alla board
    private static final int PANE_WIDTH = 1050    ;
    private static final int PANE_HEIGTH = 450;

    // DICHIARAZIONE VARIABILI

    // COSTRUTTORE
    public BoardView(List<Tile> tiles) {
        // Imposta la larghezza e altezza del Pane
        this.setPrefSize(BoardView.PANE_WIDTH, BoardView.PANE_HEIGTH);
        this.setMaxSize(1050, 520);

        // flag per determinare la direzione della riga
        boolean isUpToDown = true;
        boolean changingDirection = false;
        int numOfColumns = 0;
        int numOfRows = 0;
        int numOfLoops = 0;


        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);

            // Crea una casella
            Rectangle tileShape = new Rectangle(TILE_SIZE, TILE_SIZE);
            tileShape.setFill(getTileColor(tile));
            tileShape.setStroke(Color.WHITE);

            // Crea un testo per la casella
            Text tileLabel = new Text(getTileSymbol(tile)+" "+tile.getEffectValueString() + "\n" + (i));
            tileLabel.setFill(Color.BLACK); // Colore del testo
            tileLabel.setStyle("-fx-font-size: 12;"); // Dimensione del testo

            // Crea uno StackPane per combinare Rectangle e Text
            StackPane tileStack = new StackPane(tileShape, tileLabel);
            tileStack.setPrefSize(TILE_SIZE, TILE_SIZE);
            tileStack.setAlignment(Pos.CENTER); // Centro il testo sopra la casella

            
            
            // se non sono ancora state inserite 10 caselle dall'ultimo cambio direzione...
            if ((i+1) % 10 != 0 && changingDirection == false) { 
                
                // continua con la direzione corrente
                if (isUpToDown) {
                    tileStack.setLayoutX(numOfColumns * BoardView.TILE_SIZE);
                    tileStack.setLayoutY((++numOfRows * BoardView.TILE_SIZE));
                    this.getChildren().add(tileStack);
                } 
                // vado alla direzione opposta
                else {
                    tileStack.setLayoutX(numOfColumns * BoardView.TILE_SIZE);
                    tileStack.setLayoutY((--numOfRows * BoardView.TILE_SIZE));
                    this.getChildren().add(tileStack);
                }
            }
            // altrimenti cambio direzione
            else {
                if (isUpToDown) {
                    tileStack.setLayoutX(++numOfColumns * BoardView.TILE_SIZE);
                    tileStack.setLayoutY((numOfRows * BoardView.TILE_SIZE));
                    this.getChildren().add(tileStack);
                } 
                else {
                    tileStack.setLayoutX(++numOfColumns * BoardView.TILE_SIZE);
                    tileStack.setLayoutY((numOfRows * BoardView.TILE_SIZE));
                    this.getChildren().add(tileStack);
                }
                // se ho già impilato due caselle in vertivale per il cambio direzione...
                if (++numOfLoops >= 2) {
                    // faccio sì che le prossime caselle continuino in verticale
                    changingDirection = false;
                    numOfLoops = 0;
                    isUpToDown = !isUpToDown;
                    
                } else 
                    //altrimenti aggiungo di nuovo in orizzontale
                    changingDirection = true;
            }
        }
    }
    
    private Color getTileColor(Tile tile) {
        // Definisci i colori per i vari tipi di tile
        switch (tile.getVariant()) {
            case BONUS_MONEY:
            return Color.GREEN;
            case MALUS_MONEY:
            return Color.RED;
            case BONUS_POSITION:
            return Color.CYAN;
            case MALUS_POSITION:
            return Color.ORANGE;
            case STEAL:
            return Color.VIOLET;
            default:
            return Color.GRAY;
        }
    }
    
    private String getTileSymbol(Tile tile) {
        // Definisci i simboli per i vari tipi di tile
        switch (tile.getVariant()) {
            case BONUS_MONEY:
                return "+ $";
                case MALUS_MONEY:
                return "- $";
                case BONUS_POSITION:
                return ">>";
                case MALUS_POSITION:
                return "<<";
                case STEAL:
                return "steal";
                default:
                return "";
            }
        }
        
    }
    
    /* setPrefSize(800, 600); // Imposta la dimensione preferita della BoardView
    setPadding(new javafx.geometry.Insets(BOARD_PADDING));

    // Creare il layout a S
    VBox verticalBox = new VBox();
    HBox horizontalBox = new HBox();

    // Alterna tra verticalBox e horizontalBox per formare un percorso a S
    for (int i = 0; i < tiles.size(); i++) {
        Tile tile = tiles.get(i);
        Rectangle tileShape = new Rectangle(TILE_SIZE, TILE_SIZE);
        tileShape.setFill(getTileColor(tile));
        tileShape.setStroke(Color.BLACK);
        
        Text tileLabel = new Text(getTileSymbol(tile));
        
        // Aggiungi un label alla tile
        tileShape.setUserData(tile);
        
        VBox tileBox = new VBox(tileShape, tileLabel);
        tileBox.setAlignment(javafx.geometry.Pos.CENTER);

        if (i % 5 == 0) { // Supponiamo che ogni 5 tiles cambi la riga
            horizontalBox.getChildren().add(tileBox);
            verticalBox.getChildren().add(horizontalBox);
            horizontalBox = new HBox();
        } else {
            horizontalBox.getChildren().add(tileBox);
        }
    }
    verticalBox.getChildren().add(horizontalBox);

    getChildren().add(verticalBox); */