package Model;

import Controller.BoardController;
import Equipment.Equipment;
import java.util.Random;

public class CPUPlayer extends Player {
    private Random random = new Random();
    private boolean hasWon;
    private BoardController boardController;
    private static final int MIN_NUM_FOR_REROLL = 3;
    
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
        return this.hasRerolls() && this.rollDice() <= MIN_NUM_FOR_REROLL;
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

    @Override
    public void playTurn() {
        //TODO: gestire logica del turno del cpuPlayer
        int diceRoll = this.rollDice(); // simula il lancio del dado tramite PlayerController
        System.out.println(this.getName() + " ha tirato un " + diceRoll);
        // logica pseudo casuale per reroll
        if(this.wantsToRerollDice()){
            this.useReroll();
        }
        if(this.wantsToVisitShop()){
            
        }
    }

        
    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public Equipment getRandomItem(int i){
        return  null;
    }
}

