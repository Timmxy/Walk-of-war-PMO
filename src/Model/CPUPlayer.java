package Model;

import Equipment.Equipment;
import java.util.Random;

public class CPUPlayer extends Player {

    //Costanti
    private static final int MONEY_COUNTER = 5;
    private static final int MIN_NUM_FOR_REROLL = 3;
    

    private Random rnd = new Random();
    

    public CPUPlayer(int id, String name) {
        super(id, name);
    }

    //logica di entrata nello shop
    public boolean wantsToVisitShop() {
        if(this.getMoney() >= MONEY_COUNTER){ //se ha 5 o più monete alzo la probabilità di entrare nello shop
            int probability = rnd.nextInt(101);
            return  probability < 70; // Visita lo shop il 70% dei casi
        }
        return rnd.nextBoolean(); // Visita lo shop il 50% dei casi
    }

    // logica per il reroll dei dadi
    public boolean wantsToRerollDice() {
        //se ha reroll disponibili e se fa 3 o meno col dado decide di ritirare
        return this.hasRerolls() && this.rollDice() <= MIN_NUM_FOR_REROLL;
    }

    // logica per il riposizionamento
    public int wantsToMovePosition() {
        if(this.hasPositionModifiers()) // se ha modificatori di posizione disponibili
            return rnd.nextInt(2) - 1; // il CPUPlayer decide casualmente see come usare il riposizionamento
        return 0;
    }

    public Equipment getRandomItem(int i){
        return  null;
    }

}

