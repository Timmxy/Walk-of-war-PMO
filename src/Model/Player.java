package Model;

import Equipment.Chestplate;
import Equipment.Equipment;
import Equipment.Greaves;
import Equipment.Helmet;
import Equipment.Shield;
import Equipment.Weapon;
import Player.Inventory;
import Player.Pawn;
import java.util.List;
import java.util.Random;

public class Player {

    //DICHIARAZIONE VARIABILI
    private static final int DEFAULT_HP = 3;
    private static final int DEFAULT_ATKS = 1;
    private static final int DEFAULT_SHIELDS = 1;

    // statistiche base
    private int money;
    private int maxHp;
    private int currentHp;             
    private int maxAtks;        //numero massimo di attacchi 
    private int currentAtks;    //contatore attacchi disponibili
    private int maxShields;     //numero massimo di scudi
    private int currentShields; //contatore scudi disponibili
    private boolean defending;  //booleano per gestire la difesa nello scontro
    
    // info gioco
    private String name;
    private int id;
    private int winCounter;
    private int turnsTaken;
    private boolean wantsToVisitShop;
    private Random rnd;             // simula il lancio del dado
    
    // derivanti da effetti speciali armatura
    private int rerolls;
    private int stealProtections;
    private int positionModifiers;
    
    // composizioni
    private Inventory inventory;
    private Pawn pawn;
    
    // COSTRUTTORE
    public Player(int id, String name){
        this.id = id;
        this.name = name;
        this.winCounter = 0;
        this.turnsTaken = 0;
        this.defending = false;
        
        this.resetToDefaultStats();
        this.money = 0;
        this. winCounter = 0;
        
        this.inventory = new Inventory(this);
        this.pawn = new Pawn(0);
    }
    
    //DEFINIZIONE METODI
    
    // gestione sistema INVENTORY
    public void addItemToInventory(Equipment equipment){
        this.inventory.addEquipment(equipment);
    }
    
    // potrebbe servire?
    public void removeItemFromInventory(Equipment equipment){
        
    }
    
    // da chiamare a fine scontro
    public void resetCurrentToMaxStats(){
        this.currentHp = this.maxHp;
        this.currentAtks = this.maxAtks;
        this.currentShields = this.maxShields;
    }
    
    // chiamato da computeStats() per partire dai valori default
    private void resetToDefaultStats() {
        this.maxHp = Player.DEFAULT_HP;
        this.maxAtks = Player.DEFAULT_ATKS;
        this.maxShields = Player.DEFAULT_SHIELDS;
        resetCurrentToMaxStats();
    }
    
    // gestione statistiche EQUIPMENT
    private void addOrRemoveHP(int value){
        this.maxHp += value;
        this.currentHp = this.maxHp;
    }
    
    private void addOrRemoveAttacks(int value){
        this.maxAtks += value;
        this.currentAtks = this.maxAtks;
    }
    
    private void addOrRemoveShields(int value){
        this.maxShields += value;
        this.currentShields = this.maxShields;
    }
    
    public void computeStats(List<Equipment> list) {
        // resetto al default per ricalcolare tutto
        this.resetToDefaultStats();
        // scorro la lista e aggiorno stats
        for (Equipment equipment : list) {
            switch (equipment){
                case Helmet h -> {
                    this.addOrRemoveHP(h.getValue());
                    this.addRerolls(h.getValue());
                }
                case Chestplate c -> {
                    this.addOrRemoveHP(c.getValue());
                    this.addStealProtections(c.getValue());
                }
                case Greaves g -> {
                    this.addOrRemoveHP(g.getValue());
                    this.addPositionModifiers(g.getValue());
                }
                case Weapon w -> this.addOrRemoveAttacks(w.getValue());
                
                case Shield s -> this.addOrRemoveShields(s.getValue());
                
                default -> {System.out.println("Errore: computeStats() ha trovato oggetto non supportato nell'inventario.");}
            }
        }
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
    
    //aggiungo funzioni per il fight
    public boolean isDefending() {
        return defending;
    }
    
    public void setDefending(boolean defending) {
        this.defending = defending;
    }
    
    public void takeDmg(){// funzione per prendere danno
        this.currentHp--;
    }
    
    public void attackUsed(){
        this.currentAtks--;
    }
    
    public void increaseStamina(){
        this.currentAtks++;
    }
    
    public void shieldUsed(){
        this.currentShields--;
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
    
    // metodo per simulare il lancio del dado
    public int rollDice() {
        return this.rnd.nextInt(6) + 1; // Numero casuale tra 1 e 6
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
    
    // per SHOP
    public boolean setVisitingShop(boolean b) {
        return this.wantsToVisitShop = b;
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

    // quando subisce un furto
    public Equipment getStolenEquipment() {
        return this.inventory.removeRandomEquipment();
    }

    public int getMaxHp(){
        return this.maxHp;
    }

    public int getCurrentHp(){
        return this.currentHp;
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

    public int getNumberOfRerolls() {
        return this.rerolls;
    }

    public int getNumberOfPosModifiers() {
        return this.positionModifiers;
    }

    public int getPawnPosition() {
        return this.pawn.getPosition();
    }

// stampe utili
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
