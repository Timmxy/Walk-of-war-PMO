package Model;

import Controller.BoardController;
import Equipment.Equipment;
import java.util.Random;

public class CPUPlayer extends Player {
    private Random random = new Random();
    //private boolean visitingShop;
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
            return  probability < 70; // Visita lo shop il 70% dei casi
        }
        return random.nextBoolean(); // Visita lo shop il 50% dei casi
    }

    // logica per il reroll dei dadi
    @Override
    public boolean wantsToRerollDice() {
        //se ha reroll disponibili e se fa 3 o meno col dado decide di ritirare
        return this.hasRerolls() && this.rollDice() <= MIN_NUM_FOR_REROLL;
    }

    // logica per il riposizionamento
    @Override
    public int wantsToMovePosition() {
        if(this.hasPositionModifiers()) // se ha modificatori di posizione disponibili
            return random.nextInt(2) - 1; // il CPUPlayer decide casualmente see come usare il riposizionamento
        return 0;
    }

    @Override
    public boolean hasWon() {
        // Logica per determinare se la CPU ha vinto
        return this.hasWon;
    }

    //TODO: dopo ogni azione deve aspettare?
    @Override
    public void playTurn() {
        //logica del turno del cpuPlayer
        // azione1: [simula il lancio del dado]
        int diceRoll = this.rollDice();
        System.out.println(this.getName() + " ha tirato un " + diceRoll);

        // azione 2 [reroll]: decisione di ritirare il dado
        if(this.wantsToRerollDice()){
            this.useReroll();
        }
        // azione 3 [visita shop]: decisione di visitare lo shop
        //TODO: capire questa come gestirla, se qui o nel matchController
        /*if(this.wantsToVisitShop()){
            System.out.println(this.getName() + " ha visitato lo shop");
            this.setVisitingShop(true); //setti a true la variabile nel player vistingShop
        }*/
        // azione 4 [riposizionamento]: decisione di muoversi ulteriormente
        if(this.wantsToMovePosition()){
            this.usePositionModifiers();
            //TODO:di quanto si muove?
        }
    }

        
    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    public Equipment getRandomItem(int i){
        return  null;
    }
}

