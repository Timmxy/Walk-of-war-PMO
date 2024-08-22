
public class Player {

    private String name;
    private int money,
                id,
                winCounter,
                hp,
                maxAtks, //numero massimo di attacchi 
                currentAtks, //contatore attacchi disponibili
                maxShields, //numero massimo di scudi
                currentShields; //contatore scudi disponibili
    private Inventory inventory;
    
    public Player(int id, String name){
        this.id = id;
        this.name = name;
        this.inventory = new Inventory();
        this.money = 0;
        this. winCounter = 0;
    }

    //metodi
    public void addItemToInvetory(Equipment equipment){
        this.inventory.equip(equipment);
    }
      
    public int getMoney(){
        return this.money;
    }

    public int getId(){
        return this.id;
    }

    public int getWinCounter(){
        return this.winCounter;
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
}
