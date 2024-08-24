package Equipment;

public abstract class Armor implements Equipment {

    private String name;
    private Rarity rarity;
    private int hpValue; //quantità di hp che fornisce
    
    public Armor(String name, Rarity rarity){
        this.name = name;
        this.rarity = rarity;
    }

    //metodi
    public int getHpValue(){
        return this.hpValue;
    }

    @Override
    public Rarity getRarity(){
        return this.rarity;
    }

    @Override
    public String toString() {
        return this.name  + "\t" + this.rarity;
    }

    
}
