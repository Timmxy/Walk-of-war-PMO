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
        return this.view;
    }

    //metodo per la view
    public void onClick(){
        //se clicco un oggetto nello shop dalla view esso sparisce 
        //e appare nel mio equipaggiamento
    }
}
