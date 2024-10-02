package Controller;

import Model.Shop;
import View.ShopView;

public class ShopController {
    
    // DICHIARAZIONE VARIABILI
    private Shop shop;
    private ShopView view;

    // COSTRUTTORE
    public ShopController(Shop s) {
        this.shop = s;
        this.view = new ShopView();
    }

    public ShopView getView() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getView'");
    }
}
