package View;

import Model.Pair;
import Model.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoardView extends Pane {

    // DICHIARAZIONE VARIABILI
    private static final int TILE_SIZE = 50;    // dimensione di ogni tile
    private static final int PANE_WIDTH = 1050; // larghezza pannello
    private static final int PANE_HEIGTH = 450; // altezza pannello

    //TODO: devo fare una arraylist di pair<tile, point2d> in modo da conservare le posizioni delle tile
    //TODO: creare PAIR
    private ArrayList<Pair<Tile, Point2D>> tilePositions = new ArrayList<Pair<Tile, Point2D>>(); // mappa per salvare le posizioni delle Tile

    // COSTRUTTORE
    public BoardView(List<Tile> tiles) {
        // imposta la larghezza e altezza del Pane
        this.setPrefSize(BoardView.PANE_WIDTH, BoardView.PANE_HEIGTH);
        this.setMaxSize(1050, 520);

        
        boolean isUpToDown = true;          // flag per determinare la direzione della riga
        boolean changingDirection = false;  // flag per indicare se è nel processo di cambiare direzione
        int numOfColumns = 0;               // quante colonne sono momentaneamente renderizzate
        int numOfRows = 0;                  // quante righe sono momentaneamente renderizzate
        int numOfLoops = 0;


        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);   // casella corrente
            double layoutX;             // posizione x nel pannello della view
            double layoutY;             // posizione y nel pannello della view

            // crea una casella
            Rectangle tileShape = new Rectangle(TILE_SIZE, TILE_SIZE);
            tileShape.setFill(getTileColor(tile));
            tileShape.setStroke(Color.WHITE);

            // crea un testo per la casella
            Text tileLabel = new Text(getTileSymbol(tile)+" "+tile.getEffectValueString() + "\n" + (i));
            tileLabel.setFill(Color.BLACK); // Colore del testo
            tileLabel.setStyle("-fx-font-size: 12;"); // Dimensione del testo

            // crea uno StackPane per combinare Rectangle e Text
            StackPane tileStack = new StackPane(tileShape, tileLabel);
            tileStack.setPrefSize(TILE_SIZE, TILE_SIZE);
            tileStack.setAlignment(Pos.CENTER); // Centro il testo sopra la casella

            
            // se non sono ancora state inserite 10 caselle dall'ultimo cambio direzione...
            if ((i+1) % 10 != 0 && !changingDirection) { 
                
                // continua con la direzione corrente
                if (isUpToDown) {
                    layoutX = numOfColumns * TILE_SIZE;
                    layoutY = (++numOfRows * TILE_SIZE);
                } 
                // vado alla direzione opposta
                else {
                    layoutX = numOfColumns * TILE_SIZE;
                    layoutY = (--numOfRows * TILE_SIZE);
                }
            }
            // altrimenti cambio direzione
            else {
                layoutX = (++numOfColumns * TILE_SIZE);
                layoutY = numOfRows * TILE_SIZE;
                // se ho già impilato due caselle in verticale per il cambio direzione...
                if (++numOfLoops >= 2) {
                    // faccio sì che le prossime caselle continuino in verticale
                    changingDirection = false;
                    numOfLoops = 0;
                    isUpToDown = !isUpToDown;
                } else {
                    //altrimenti aggiungo di nuovo in orizzontale
                    changingDirection = true;
                }
            }

            // imposta le coordinate del tile
            tileStack.setLayoutX(layoutX);
            tileStack.setLayoutY(layoutY);

            // aggiungi il tile al pannello
            this.getChildren().add(tileStack);

            // memorizza la posizione della Tile nella mappa
            tilePositions.add(new Pair<Tile,Point2D>(tile, new Point2D(layoutX, layoutY)));
        }
    }

    // metodo per ottenere la posizione di una Tile
    public Point2D getTilePosition(int position) {
        for (Pair<Tile,Point2D> pair : tilePositions) {
            if (pair.getFirst().getPosition() == position) {
                return pair.getSecond();
            }
        }
        // se sono qui significa che non esiste una casella con quella posizione
        throw new IllegalArgumentException();
    }
    
    private Color getTileColor(Tile tile) {
        // definisce i colori per i vari tipi di tile
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
        // definisce i simboli per i vari tipi di tile
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
    