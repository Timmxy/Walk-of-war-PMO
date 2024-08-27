package Model;

import Controller.PlayerController;
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

    private PlayerController playerController;
    private Inventory inventory;
    private Pawn pawn;
    
    public Player(int id, String name, PlayerController pc){
        this.id = id;
        this.name = name;
        this.playerController = pc;

        this.money = 0;
        this. winCounter = 0;
        
        this.inventory = new Inventory(this.playerController, this);
        this.pawn = new Pawn();
    }

    //DEFINIZIONE METODI
    public void addItemToInventory(Equipment equipment){
        this.inventory.equip(equipment);
    }

    public void removeItemFromInventory(Equipment equipment){

    }
    
    
    public void resetToDefaultStats(){
        this.currentHp = Player.DEFAULT_HP;
        this.currentAtks = Player.DEFAULT_ATKS;
        this.currentShields = Player.DEFAULT_SHIELDS;
    }
    
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
    
    public void addMoney(int value) {
        this.money += value;
    }
    
    public void updatePosition(int value) {
        this.pawn.newPosition(value);
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

    public Pawn getPawn() {
        return this.pawn;    
    }

    @Override
    public String toString() {
        return this.name;
    }

}
