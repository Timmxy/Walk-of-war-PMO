package Model;

import java.util.Random;

public class CPUPlayer extends Player {
    private Random random = new Random();
    private boolean hasWon;
    
    //Costanti
    private static int MONEY_COUNTER = 5;

    public CPUPlayer(int id, String name) {
        super(id, name);
    }

    //logica di entrata nello shop
    @Override
    public boolean wantsToVisitShop() {
        if(this.getMoney() >= MONEY_COUNTER){ //se ha 5 o più monete alzo la probabilità di entrare nello shop
            int probability = random.nextInt(101);
            return probability < 70; // Visita lo shop il 70% dei casi
        }
        return random.nextBoolean(); // Visita lo shop il 50% dei casi
    }

    // Il CPUPlayer decide se fare reroll dei dadi
    @Override
    public boolean wantsToRerollDice() {
        return this.hasRerolls() && this.rollDice() <= 3;
    }

    @Override
    public boolean wantsToMovePosition() {
        return random.nextBoolean(); // Il CPUPlayer decide casualmente se muoversi avanti o indietro
    }

    @Override
    public boolean hasWon() {
        // Logica per determinare se la CPU ha vinto
        return this.hasWon;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }
}