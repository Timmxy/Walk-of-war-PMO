package Equipment;

public class Helmet extends Armor{

    public Helmet(String name, Rarity rarity) {
        super(name, rarity);
    }

    // fa scegliere al player se rirollare il dado dopo il primo lancio, funziona k volte
    @Override
    public void specialAbility() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'specialAbility'");
    }
}
