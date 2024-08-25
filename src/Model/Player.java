package Model;

import Equipment.Equipment;
import Player.Inventory;

public class Player {

    //DICHIARAZIONE VARIABILI
    private String name;
    private int id;
    private int winCounter;
    private int money;
    private int hp;             
    private int maxAtks;        //numero massimo di attacchi 
    private int currentAtks;    //contatore attacchi disponibili
    private int maxShields;     //numero massimo di scudi
    private int currentShields; //contatore scudi disponibili

    private Inventory inventory;
    
    public Player(int id, String name){
        this.id = id;
        this.name = name;
        this.money = 0;
        this. winCounter = 0;
        
        this.inventory = new Inventory();
    }

    //DEFINIZIONE METODI
    public void addItemToInventory(Equipment equipment){
        this.inventory.equip(equipment);
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

    @Override
    public String toString() {
        return this.name;
    }
}
