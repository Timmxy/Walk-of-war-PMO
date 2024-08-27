package Player;

import java.util.Optional;

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
    
    private Optional<Greaves> greaves;
    private Optional<Chestplate> chestplate;
    private Optional<Helmet> helmet;
    private Optional<Weapon> weapon;
    private Optional<Shield> shield;

    private PlayerController playerController;
    private Player player;

    //COSTRUTTORE
    public Inventory(PlayerController pc, Player p){
        this.playerController = pc;
        this.player = p;
        this.greaves = Optional.empty();
        this.chestplate = Optional.empty();
        this.helmet = Optional.empty();
        this.weapon = Optional.empty();
        this.shield = Optional.empty();
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
        this.weapon = Optional.of(weapon);
        // invocare l'aggiornamento statistiche di PlayerController / aggiornamento: non c'è bisogno passare per fuori nuovamente..
        //..l'aumento delle statistiche è diretta conseguenza dell'equipaggiamento, quindi non viene dall'esterno
        // ha senso invece farlo per Furto
        this.player.addOrRemoveAttacks(weapon.getValue());
        System.out.println("Weapon equipped: " + weapon);
    }

    private void equipShield(Shield shield){
        this.shield = Optional.of(shield);
        // invocare l'aggiornamento statistiche di PlayerController
        this.player.addOrRemoveShields(shield.getValue());
        System.out.println("Shield equipped: " + shield);
    }

    private void equipGreaves(Greaves greaves) {
        this.greaves = Optional.of(greaves);
        // invocare l'aggiornamento statistiche di PlayerController
        this.player.addOrRemoveHP(greaves.getValue());
        System.out.println("Greaves equipped: " + greaves);
    }

    private void equipChestplate(Chestplate chestplate) {
        this.chestplate = Optional.of(chestplate);
        // invocare l'aggiornamento statistiche di PlayerController
        this.player.addOrRemoveHP(chestplate.getValue());
        System.out.println("Chestplate equipped: " + chestplate);
    }

    private void equipHelmet(Helmet helmet) {
        this.helmet = Optional.of(helmet);
        // invocare l'aggiornamento statistiche di PlayerController
        this.player.addOrRemoveHP(helmet.getValue());
        System.out.println("Helmet equipped: " + helmet);
    }
}
