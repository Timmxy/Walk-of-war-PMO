package Equipment;

public abstract class Equipment {
    
    private String name;
    private Rarity rarity;
    private int value;  // quantità di stats che fornisce
    private int cost;   // valore allo shop
    
    public Equipment(String name, Rarity rarity){
        this.name = name;
        this.rarity = rarity;
        this.value = this.rarity.getValue();
        this.cost = this.rarity.getValue() + 3;
    }
    
    // METODI
    public int getValue(){
        return this.value;
    }


    public Rarity getRarity(){
        return this.rarity;
    }

    public int getCost() {
        return this.cost;
    }


    @Override
    public String toString() {
        return this.name  + " " + this.rarity;
    }
}
