package Equipment;

public class Shield implements Equipment {

    private String name;
    private Rarity rarity;
    private int shieldValue; //quantità di scudi che fornisce
    
    public Shield(String name, Rarity rarity){
        this.name = name;
        this.rarity = rarity;
    }

    //metodi
    public int getValue(){
        return this.shieldValue;
    }

    
    public Rarity getRarity(){
        return this.rarity;
    }

    @Override
    public String toString() {
        return this.name  + "\t" + this.rarity;
    }

}
