package Model;

import Equipment.Equipment;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class CPUPlayer extends Player {

    // DICHIARAZIONE VARIBILI
    //Costanti
    private static final int MONEY_COUNTER = 5;
    private static final int MIN_NUM_FOR_REROLL = 3;
    

    private Random rnd = new Random();
    
    // COSTRUTTORE
    public CPUPlayer(int id, String name) {
        super(id, name);
    }

    //logica di entrata nello shop
    public boolean wantsToVisitShop() {
        if(this.getMoney() >= MONEY_COUNTER){ //se ha 5 o più monete alzo la probabilità di entrare nello shop
            int probability = rnd.nextInt(101);
            return  probability < 70; // visita lo shop il 70% dei casi
        }
        return this.rnd.nextInt(10) > 2 ? false : true;
    }

    public Optional<Equipment> wantsToBuySomething(List<Equipment> availableEquipments) {
        // prendo un oggetto random tra i 4
        Equipment potentialPurchase = availableEquipments.get(this.rnd.nextInt(3));
        if (this.getMoney() >= potentialPurchase.getCost()) {
            if (this.rnd.nextInt(1) == 0) {
                // compra
                return Optional.of(potentialPurchase);
            }
        }
        // altrimenti non compra
        return Optional.empty();
    }

    // logica per il reroll dei dadi
    public boolean wantsToRerollDice(int currentDiceResult) {
        //se ha reroll disponibili e se fa 3 o meno col dado decide di ritirare
        return this.hasRerolls() && currentDiceResult <= MIN_NUM_FOR_REROLL;
    }

    // logica per il riposizionamento
    public int wantsToMovePosition() {
        if(this.hasPositionModifiers()) // se ha modificatori di posizione disponibili
            return rnd.nextInt(2) - 1; // il CPUPlayer decide casualmente see come usare il riposizionamento
        return 0;
    }
}

