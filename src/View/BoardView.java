package View;

import Model.Tile;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
    private static final int BOARD_PADDING = 20; // Padding attorno alla board
    private static final int TILE_SPACING = 5; // Spazio tra le tiles

    // DICHIARAZIONE VARIABILI

    // COSTRUTTORE
    public BoardView(List<Tile> tiles) {
        this.setMaxSize(1080, 520);
        // Creazione delle righe per il pattern a S
        VBox topRow = new VBox(TILE_SPACING);
        VBox bottomRow = new VBox(TILE_SPACING);

        // Crea HBox per la riga inferiore
        HBox currentRow = new HBox(TILE_SPACING);

        // Flag per determinare la direzione della riga
        boolean isLeftToRight = true;

        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);

            // Crea una casella
            Rectangle tileShape = new Rectangle(TILE_SIZE, TILE_SIZE);
            tileShape.setFill(getTileColor(tile));
            tileShape.setStroke(Color.BLACK);

            // Crea un testo per la casella
            Text tileLabel = new Text(getTileSymbol(tile)+" "+tile.getEffectValueString()+"\n"+i);
            tileLabel.setFill(Color.BLACK); // Colore del testo
            tileLabel.setStyle("-fx-font-size: 15;"); // Dimensione del testo

            // Crea uno StackPane per combinare Rectangle e Text
            StackPane tileStack = new StackPane(tileShape, tileLabel);
            tileStack.setPrefSize(TILE_SIZE, TILE_SIZE);
            tileStack.setAlignment(Pos.CENTER); // Centro il testo sopra la casella

            // Aggiungi la casella all'HBox
            currentRow.getChildren().add(tileStack);

            // Se è il momento di cambiare direzione
            if (i % 10 == 9) { // Cambia riga dopo ogni 5 caselle
                if (isLeftToRight) {
                    topRow.getChildren().add(currentRow);
                } else {
                    bottomRow.getChildren().add(currentRow);
                }
                isLeftToRight = !isLeftToRight; // Cambia direzione
                currentRow = new HBox(TILE_SPACING); // Nuova riga
            }
        }

        // Aggiungi l'ultima riga se necessario
        if (!currentRow.getChildren().isEmpty()) {
            if (isLeftToRight) {
                topRow.getChildren().add(currentRow);
            } else {
                bottomRow.getChildren().add(currentRow);
            }
        }

        // Aggiungi le righe al layout principale
        HBox layout = new HBox(TILE_SPACING);
        if (isLeftToRight) {
            layout.getChildren().addAll(topRow, bottomRow);
        } else {
            layout.getChildren().addAll(bottomRow, topRow);
        }
        layout.setAlignment(Pos.CENTER);

        this.getChildren().add(layout);
        this.setPrefSize(layout.getWidth(), layout.getHeight());
    }
    
    private Color getTileColor(Tile tile) {
        // Definisci i colori per i vari tipi di tile
        switch (tile.getVariant()) {
            case BONUS_MONEY:
            return Color.GREEN;
            case MALUS_MONEY:
            return Color.RED;
            case BONUS_POSITION:
            return Color.BLUE;
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
                return "$";
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