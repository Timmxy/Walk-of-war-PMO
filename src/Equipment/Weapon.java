package Equipment;

public class Weapon extends Equipment {

    private String name;
    private Rarity rarity;
    private int attackValue; //quantità di attacchi che fornisce
    
    public Weapon(String name, Rarity rarity){
        super(name, rarity);
    }

}
