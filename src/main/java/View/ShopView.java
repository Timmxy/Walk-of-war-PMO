package View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import Controller.ShopController;
import Equipment.Equipment;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import Model.Pair;

public class ShopView extends Pane {

    // DICHIARAZIONE VARIABILI
    private static final int PANE_WIDTH = 1050; // larghezza pannello
    private static final int PANE_HEIGTH = 450; // altezza pannello

    private static final float FIRST_COLUMN_POS_X = 91.25f;
    private static final float SECOND_COLUMN_POS_X = 353.75f;
    private static final float THIRD_COLUMN_POS_X = 616.25f;
    private static final float FOURTH_COLUMN_POS_X = 878.75f;

    // bottoni e label
    private Button buyEquipment1;
    private Button buyEquipment2;
    private Button buyEquipment3;
    private Button buyEquipment4;
    private Button exitShop;

    private Label equipmentNameAndRarity1;
    private Label equipmentNameAndRarity2;
    private Label equipmentNameAndRarity3;
    private Label equipmentNameAndRarity4;

    private Text infoText;

    private ShopController controller;

    private List<Pair<Button, Optional<Equipment>>>  buttonsToBuyEquipment;

    // COSTRUTTORE
    public ShopView(ShopController s) {
       // imposta la larghezza e altezza del Pane
       this.setPrefSize(ShopView.PANE_WIDTH, ShopView.PANE_HEIGTH);
       this.setMaxSize(1050, 520);

        this.controller = s;
        this.buttonsToBuyEquipment = new ArrayList<>();

       this.setupButtonsAndLabels();
       this.setButtonActions();
    }

    // mostrare gli oggetti acquistabili nello shop
    public void displayShopContents(List<Equipment> content, int playerMoney) {
        // controllo di avere solo quattro oggetti al massimo
        int itemsToDisplay = Math.min(content.size(), 4);

        // mostra gli oggetti e abilita/disabilita i bottoni in base alle monete del giocatore
        for (int i = 0; i < itemsToDisplay; i++) {
            Equipment equipment = content.get(i);
            String itemNameAndRarity = content.get(i).toString();
            int itemPrice = equipment.getCost(); // Presumendo che ci sia un metodo per ottenere il prezzo

            // aggiorna label e button
            switch (i) {
                case 0:
                    this.equipmentNameAndRarity1.setText(itemNameAndRarity);
                    this.buyEquipment1.setText("Buy: $" + itemPrice);
                    this.buyEquipment1.setDisable(itemPrice > playerMoney); // disabilita se il prezzo è maggiore delle monete
                    break;
                case 1:
                    this.equipmentNameAndRarity2.setText(itemNameAndRarity);
                    this.buyEquipment2.setText("Buy: $" + itemPrice);
                    this.buyEquipment2.setDisable(itemPrice > playerMoney);
                    break;
                case 2:
                    this.equipmentNameAndRarity3.setText(itemNameAndRarity);
                    this.buyEquipment3.setText("Buy: $" + itemPrice);
                    this.buyEquipment3.setDisable(itemPrice > playerMoney);
                    break;
                case 3:
                    this.equipmentNameAndRarity4.setText(itemNameAndRarity);
                    this.buyEquipment4.setText("Buy: $" + itemPrice);
                    this.buyEquipment4.setDisable(itemPrice > playerMoney);
                    break;
            }
            this.buttonsToBuyEquipment.get(i).setY(Optional.of(equipment));
        }
    }

    private void setButtonActions() {
        this.buyEquipment1.setOnAction(e -> buyEquipmentButtonClicked(0));
        this.buyEquipment2.setOnAction(e -> buyEquipmentButtonClicked(1));
        this.buyEquipment3.setOnAction(e -> buyEquipmentButtonClicked(2));
        this.buyEquipment4.setOnAction(e -> buyEquipmentButtonClicked(3));

        this.exitShop.setOnAction(e -> this.exitShopButtonClicked());
    }


    private void exitShopButtonClicked() {
        this.controller.handleExitShop(Optional.empty());
    }

    private void buyEquipmentButtonClicked(int i) {
        this.controller.handleExitShop(this.buttonsToBuyEquipment.get(i).getSecond());
    }

    private void setupButtonsAndLabels() {
        // creo componenti
        this.buyEquipment1 = new Button();
        this.buyEquipment2 = new Button();
        this.buyEquipment3 = new Button();
        this.buyEquipment4 = new Button();
        this.buttonsToBuyEquipment.add(new Pair<Button,Optional<Equipment>>(buyEquipment1, Optional.empty()));
        this.buttonsToBuyEquipment.add(new Pair<Button,Optional<Equipment>>(buyEquipment2, Optional.empty()));
        this.buttonsToBuyEquipment.add(new Pair<Button,Optional<Equipment>>(buyEquipment3, Optional.empty()));
        this.buttonsToBuyEquipment.add(new Pair<Button,Optional<Equipment>>(buyEquipment4, Optional.empty()));
        
        this.exitShop = new Button("Exit Shop");

        this.equipmentNameAndRarity1 = new Label();
        this.equipmentNameAndRarity2 = new Label();
        this.equipmentNameAndRarity3 = new Label();
        this.equipmentNameAndRarity4 = new Label();


        this.infoText = new Text("Here you can find Weapons, Shields and Armor to help you on your Fights!\n"+
                                     "\t- WEAPONS give additional attacks, while SHIELDS give you extra protection.\n"+
                                     "\t- ARMOR pieces increase your life points and give you SPECIAL ABILITIES to use on your journey.");

        // setto il colore del testo
        this.equipmentNameAndRarity1.setTextFill(Color.WHITE);
        this.equipmentNameAndRarity2.setTextFill(Color.WHITE);
        this.equipmentNameAndRarity3.setTextFill(Color.WHITE);
        this.equipmentNameAndRarity4.setTextFill(Color.WHITE);
        
        this.infoText.setStroke(Color.WHITE);

        // setto la scala
        this.buyEquipment1.setScaleX(1.5);
        this.buyEquipment2.setScaleX(1.5);
        this.buyEquipment3.setScaleX(1.5);
        this.buyEquipment4.setScaleX(1.5);

        this.buyEquipment1.setScaleY(1.5);
        this.buyEquipment2.setScaleY(1.5);
        this.buyEquipment3.setScaleY(1.5);
        this.buyEquipment4.setScaleY(1.5);

        this.equipmentNameAndRarity1.setScaleX(1.5);
        this.equipmentNameAndRarity2.setScaleX(1.5);
        this.equipmentNameAndRarity3.setScaleX(1.5);
        this.equipmentNameAndRarity4.setScaleX(1.5);

        this.equipmentNameAndRarity1.setScaleY(1.5);
        this.equipmentNameAndRarity2.setScaleY(1.5);
        this.equipmentNameAndRarity3.setScaleY(1.5);
        this.equipmentNameAndRarity4.setScaleY(1.5);

        this.exitShop.setScaleX(1.5);
        this.exitShop.setScaleY(1.5);

        this.infoText.setScaleX(1.5);
        this.infoText.setScaleY(1.5);

        // setto la posizione
        this.buyEquipment1.setTranslateX(ShopView.FIRST_COLUMN_POS_X);
        this.buyEquipment1.setTranslateY(ShopView.PANE_HEIGTH -100);
        
        this.buyEquipment2.setTranslateX(ShopView.SECOND_COLUMN_POS_X);
        this.buyEquipment2.setTranslateY(ShopView.PANE_HEIGTH -100);
        
        this.buyEquipment3.setTranslateX(ShopView.THIRD_COLUMN_POS_X);
        this.buyEquipment3.setTranslateY(ShopView.PANE_HEIGTH -100);
        
        this.buyEquipment4.setTranslateX(ShopView.FOURTH_COLUMN_POS_X);
        this.buyEquipment4.setTranslateY(ShopView.PANE_HEIGTH -100);
        
        this.equipmentNameAndRarity1.setTranslateX(ShopView.FIRST_COLUMN_POS_X);
        this.equipmentNameAndRarity1.setTranslateY(ShopView.PANE_HEIGTH -200);
        
        this.equipmentNameAndRarity2.setTranslateX(ShopView.SECOND_COLUMN_POS_X);
        this.equipmentNameAndRarity2.setTranslateY(ShopView.PANE_HEIGTH -200);
        
        this.equipmentNameAndRarity3.setTranslateX(ShopView.THIRD_COLUMN_POS_X);
        this.equipmentNameAndRarity3.setTranslateY(ShopView.PANE_HEIGTH -200);
        
        this.equipmentNameAndRarity4.setTranslateX(ShopView.FOURTH_COLUMN_POS_X);
        this.equipmentNameAndRarity4.setTranslateY(ShopView.PANE_HEIGTH -200);

        this.exitShop.setTranslateX(ShopView.FOURTH_COLUMN_POS_X);
        this.exitShop.setTranslateY(ShopView.PANE_HEIGTH);

        this.infoText.setTranslateX(150);
        this.infoText.setTranslateY(70);
        
        // aggiungo al pannello
        this.getChildren().add(buyEquipment1);
        this.getChildren().add(buyEquipment2);
        this.getChildren().add(buyEquipment3);
        this.getChildren().add(buyEquipment4);

        this.getChildren().add(equipmentNameAndRarity1);
        this.getChildren().add(equipmentNameAndRarity2);
        this.getChildren().add(equipmentNameAndRarity3);
        this.getChildren().add(equipmentNameAndRarity4);

        this.getChildren().add(exitShop);

        this.getChildren().add(this.infoText);
    }

}
