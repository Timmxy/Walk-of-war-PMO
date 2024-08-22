public class Inventory {

    private Greaves greaves;
    private Chestplate chestplate;
    private Helmet helmet;
    private Weapon weapon;
    private Shield shield;

    public void equip(Equipment equipment){
        switch (equipment) {
            case Armor a -> this.equipArmor(a);
            case Weapon w -> this.equipWeapon(w);
            case Shield s -> this.equipShield(s);
            default -> {
            }
        }
    }

    private void equipArmor(Equipment equipment){
        switch (equipment) {
            case Greaves g -> equipGreaves(g);
            case Chestplate c -> equipChestplate(c);
            case Helmet h -> equipHelmet(h);
            default -> {
            }
        }   
    }

    private void equipWeapon(Weapon weapon){
        this.weapon = weapon;
        System.out.println("Weapon equipped: " + weapon);
    }

    private void equipShield(Shield shield){
        this.shield = shield;
        System.out.println("Shield equipped: " + shield);
    }

    private void equipGreaves(Greaves greaves) {
        this.greaves = greaves;
        System.out.println("Greaves equipped: " + greaves);
    }

    private void equipChestplate(Chestplate chestplate) {
        this.chestplate = chestplate;
        System.out.println("Chestplate equipped: " + chestplate);
    }

    private void equipHelmet(Helmet helmet) {
        this.helmet = helmet;
        System.out.println("Helmet equipped: " + helmet);
    }
}
