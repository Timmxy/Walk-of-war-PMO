package Equipment;

public class Weapon implements Equipment {

    private String name;
    private Rarity rarity;
    private int attackValue; //quantità di attacchi che fornisce
    
    public Weapon(String name, Rarity rarity){
        this.name = name;
        this.rarity = rarity;
        this.attackValue = this.rarity.getValue();
    }

    //metodi
    public int getValue(){
        return this.attackValue;
    }

    
    public Rarity getRarity(){
        return this.rarity;
    }

    @Override
    public String toString() {
        return this.name  + " " + this.rarity;
    }
}
