package Controller;

import java.util.List;
import java.util.Optional;

import Equipment.Equipment;
import Model.Player;
import Model.Shop;
import View.ShopView;

public class ShopController {
    
    // DICHIARAZIONE VARIABILI
    private Shop shop;
    private ShopView view;
    private MatchController matchController;

    // COSTRUTTORE
    public ShopController(Shop s, MatchController mc) {
        this.shop = s;
        this.view = new ShopView(this);
        this.matchController = mc;
    }

    public ShopView getView() {
        return this.view;
    }

    //metodo per la view
    public void onClick(){
        //se clicco un oggetto nello shop dalla view esso sparisce 
        //e appare nel mio equipaggiamento
    }

    public void visitShop(Player player) {
        System.out.println("-> "+player.toString()+" visita lo Shop!");
        // inizializza lo shop
        this.shop.rerollShop();
        // comunico alla view cosa far visualizzare
        this.view.displayShopContents(this.shop.getShopContents(), player.getMoney());
    }

    public List<Equipment> getAvailableEquipments() {
        return this.shop.getShopContents();
    }

    public void handleExitShop(Optional<Equipment> equipment) {
        // dare a MatchController equipment se comprato -> disattiverà il pannello dello shop
        this.matchController.shopVisitEnded(equipment);
    }

}
