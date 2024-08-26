package Player;

import Controller.PlayerController;
import Equipment.Armor;
import Equipment.Chestplate;
import Equipment.Equipment;
import Equipment.Greaves;
import Equipment.Helmet;
import Equipment.Shield;
import Equipment.Weapon;
import Model.Player;

public class Inventory {

    //DICHIARAZIONI VARIABILI
    
    private Greaves greaves;
    private Chestplate chestplate;
    private Helmet helmet;
    private Weapon weapon;
    private Shield shield;

    private PlayerController playerController;
    private Player player;

    //COSTRUTTORE
    public Inventory(PlayerController pc, Player p){
        this.playerController = pc;
        this.player = p;
    }

    // RISOLTO!!! a me da errore -> The Java feature 'Pattern Matching in Switch' is only available with source level 21 and above.
    public void equip(Equipment equipment){
        switch (equipment) {
            case Armor a -> this.equipArmor(a);
            case Weapon w -> this.equipWeapon(w);
            case Shield s -> this.equipShield(s);
            default -> {}
        }
    }

    private void equipArmor(Equipment equipment){
        switch (equipment) {
            case Greaves g -> equipGreaves(g);
            case Chestplate c -> equipChestplate(c);
            case Helmet h -> equipHelmet(h);
            default -> {}
        }   
    }

    private void equipWeapon(Weapon weapon){
        this.weapon = weapon;
        // invocare l'aggiornamento statistiche di PlayerController / aggiornamento: non c'è bisogno passare per fuori nuovamente..
        //..l'aumento delle statistiche è diretta conseguenza dell'equipaggiamento, quindi non viene dall'esterno
        // ha senso invece farlo per Furto
        this.playerController.updateAttacks(this.player);
        System.out.println("Weapon equipped: " + weapon);
    }

    private void equipShield(Shield shield){
        this.shield = shield;
        // invocare l'aggiornamento statistiche di PlayerController
        this.playerController.updateShields(this.player);
        System.out.println("Shield equipped: " + shield);
    }

    private void equipGreaves(Greaves greaves) {
        this.greaves = greaves;
        // invocare l'aggiornamento statistiche di PlayerController
        this.playerController.updateHP(this.player, greaves.getHpValue());
        System.out.println("Greaves equipped: " + greaves);
    }

    private void equipChestplate(Chestplate chestplate) {
        this.chestplate = chestplate;
        // invocare l'aggiornamento statistiche di PlayerController
        this.playerController.updateHP(this.player, chestplate.getHpValue());
        System.out.println("Chestplate equipped: " + chestplate);
    }

    private void equipHelmet(Helmet helmet) {
        this.helmet = helmet;
        // invocare l'aggiornamento statistiche di PlayerController
        this.playerController.updateHP(this.player, helmet.getHpValue());
        System.out.println("Helmet equipped: " + helmet);
    }
}
