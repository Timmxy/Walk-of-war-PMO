import java.util.Optional;

public class Player {

    private int money;
    private int id;
    private String name;
    private int winCounter;
    private int hp;
    private int maxAtks; //numero massimo di attacchi 
    private int currentAtks; //contatore attacchi disponibili
    private int maxShields; //numero massimo di scudi
    private int currentShields; //contatore scudi disponibili
    private Optional<Armor> pippo = Optional.empty(); 
    
    public Player(int id, String name){
        this.id = id;
        this.name = name;
        this.money = 0;
        this. winCounter = 0;
    }

    //metodi
    public void equip(Equipment equipment){
        if (equipment instanceof Armor) {
            this.equipArmor();
        }
        else if (equipment instanceof Weapon) {
            this.equipWeapon();
        }
        else if (equipment instanceof Shield) {
            this.equipShield();
        }
    }

    private void equipArmor(){

    }

    private void equipWeapon(){

    }

    private void equipShield(){

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
