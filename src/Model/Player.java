package Model;

import Equipment.Equipment;
import Player.Inventory;
import Player.Pawn;

public class Player {

    //DICHIARAZIONE VARIABILI
    private static final int DEFAULT_HP = 3;
    private static final int DEFAULT_ATKS = 1;
    private static final int DEFAULT_SHIELDS = 1;

    private String name;
    private int id;
    private int winCounter;
    private int money;
    private int maxHp;
    private int currentHp;             
    private int maxAtks;        //numero massimo di attacchi 
    private int currentAtks;    //contatore attacchi disponibili
    private int maxShields;     //numero massimo di scudi
    private int currentShields; //contatore scudi disponibili

    // derivanti da effetti speciali armatura
    private int rerolls;
    private int stealProtections;

    // composizioni
    private Inventory inventory;
    private Pawn pawn;
    private int positionModifiers;
    
    public Player(int id, String name){
        this.id = id;
        this.name = name;

        this.maxHp = 4;
        this.maxAtks = 2;
        this.maxShields = 2;
        this.resetToMaxStats();
        this.money = 0;
        this. winCounter = 0;
        
        this.inventory = new Inventory(this);
        this.pawn = new Pawn(0);
    }

    //DEFINIZIONE METODI

// gestione sistema INVENTORY
    public void addItemToInventory(Equipment equipment){
        this.inventory.equip(equipment);
    }

    // potrebbe servire?
    public void removeItemFromInventory(Equipment equipment){

    }

    // quando subisce un furto
    public Equipment getStolenEquipment() {
        return this.inventory.removeRandomEquipment();
    }
    
    // da chiamare a fine scontro
    public void resetToMaxStats(){
        this.currentHp = this.maxHp;
        this.currentAtks = this.maxAtks;
        this.currentShields = this.maxShields;
    }
    
// gestione statistiche EQUIPMENT
    public void addOrRemoveHP(int value){
        this.maxHp += value;
        this.currentHp = this.maxHp;
    }
    
    public void addOrRemoveAttacks(int value){
        this.maxAtks += value;
        this.currentAtks = this.maxAtks;
    }
    
    public void addOrRemoveShields(int value){
        this.maxShields += value;
        this.currentShields = this.maxShields;
    }
    

// gestione effetti TILE
    public void addMoney(int value) {
        this.money += value;
    }

    public void removeMoney(int value) {
        if (!(this.money < value)) {
            this.money -= value;
        }
    }
    
    public void updatePosition(int value) {
        this.pawn.newPosition(value);
    }


// gestione effetti speciali ARMOR
    public boolean hasStealProtection() {
        return this.stealProtections >= 1;
    }
    
    public void useStealProtection() {
        this.stealProtections--;
        System.out.println(this.toString() + " blocca il furto! Protezioni rimaste: "+ this.stealProtections);
    }

    public void addStealProtections(int value) {
        this.stealProtections = value;
    }

    public boolean hasRerolls() {
        return this.rerolls >= 1;
    }

    public void useReroll() {
        this.rerolls--;
        System.out.println(this.toString() + " usa un reroll! Reroll rimasti: "+ this.rerolls);
    }
    
    public void addRerolls(int value) {
        this.rerolls = value;
    }
    
    public boolean hasPositionModifiers() {
        return this.positionModifiers >= 1;
    }

    public void usePositionModifiers() {
        this.positionModifiers--;
        System.out.println(this.toString() + " usa un modificatore di posizione! Modificatori rimasti: "+ this.positionModifiers);
    }
    
    public void addPositionModifiers(int value) {
        this.positionModifiers = value;
    }
    

//getter e setter
    public String getName() {
        return this.name;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getWinCounter(){
        return this.winCounter;
    }

    public int getMoney(){
        return this.money;
    }

    public int getMaxAtks(){
        return this.maxAtks;
    }

    public int getCurrentAtks(){
        return this.currentAtks;
    }

    public int getMaxShields(){
        return this.maxShields;
    }

    public int getcurrentShields(){
        return this.currentShields;
    }

    public int getPawnPosition() {
        return this.pawn.getPosition();    
    }

    public void printStats() {
        System.out.println("\nStatistiche di "+ this.toString() +":"
                                                               +"\nHP -> "                     + this.maxHp
                                                               +"\nAttacks -> "                + this.maxAtks
                                                               +"\nShields -> "                + this.maxShields
                                                               +"\nMonete -> "                 + this.money
                                                               +"\nReroll -> "                 + this.rerolls
                                                               +"\nProtezioni Furto -> "       + this.stealProtections
                                                               +"\nModificatori Posizione -> " + this.positionModifiers +"\n");
        
        System.out.println("--------------------------------------------");
    }

    public void inventoryContents() {
        this.inventory.printEquipment();
    }

    @Override
    public String toString() {
        return this.name;
    }

}
