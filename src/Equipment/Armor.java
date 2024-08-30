package Equipment;

public abstract class Armor implements Equipment {

    private String name;
    private Rarity rarity;
    private int hpValue; //quantità di hp che fornisce
    
    public Armor(String name, Rarity rarity){
        this.name = name;
        this.rarity = rarity;
        this.hpValue = this.rarity.getValue();
    }
    
    // METODI

    // da chiamare al momento opportuno
    // probabilmente necessita di un counter per sapere quante volte può usare l'abilità
    public abstract void specialAbility();

    public int getValue(){
        return this.hpValue;
    }


    public Rarity getRarity(){
        return this.rarity;
    }


    @Override
    public String toString() {
        return this.name  + " " + this.rarity;
    }

    
}
