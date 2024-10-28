package View;

import javafx.scene.layout.Pane;

public class ShopView extends Pane {

    // DICHIARAZIONE VARIABILI
    private static final int PANE_WIDTH = 1050; // larghezza pannello
    private static final int PANE_HEIGTH = 450; // altezza pannello

    // COSTRUTTORE
    public ShopView() {
       // imposta la larghezza e altezza del Pane
       this.setPrefSize(ShopView.PANE_WIDTH, ShopView.PANE_HEIGTH);
       this.setMaxSize(1050, 520);

       
    }

}
