package Player;

import java.util.Optional;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
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
    
    // utilizzare solo lista, controllare con contains() se ha già un certo equipment
    /* private Optional<Greaves> greaves;
    private Optional<Chestplate> chestplate;
    private Optional<Helmet> helmet;
    private Optional<Weapon> weapon;
    private Optional<Shield> shield; */

    private List<Equipment> equipments;
    private Random rnd;

    private Player player;

    //COSTRUTTORE
    public Inventory(Player p){
        this.player = p;
        /* this.greaves = Optional.empty();
        this.chestplate = Optional.empty();
        this.helmet = Optional.empty();
        this.weapon = Optional.empty();
        this.shield = Optional.empty(); */
        this.equipments = new ArrayList<>();
        this.rnd = new Random();
    }

    public Equipment removeRandomEquipment() {
        if (!this.equipments.isEmpty()) {
            Equipment toRemove = this.equipments.get(this.rnd.nextInt(equipments.size()));
            this.equipments.remove(toRemove);
            System.out.println(this.player.toString() +" perde l'Equipment: "+ toRemove.toString() +"!!!");
            // aggiorna le stats
            switch (toRemove) {
                case Armor a  -> this.player.addOrRemoveHP(-toRemove.getValue());
                case Weapon w -> this.player.addOrRemoveAttacks(-toRemove.getValue());
                case Shield s -> this.player.addOrRemoveShields(-toRemove.getValue());
                default -> {/*eccezione*/}
            }
            return toRemove;
        }
        else {
            System.out.println(this.player.toString() +" non ha alcun Equipment da rubare!!!");
            return null;
        }
    }

    // RISOLTO!!! a me da errore -> The Java feature 'Pattern Matching in Switch' is only available with source level 21 and above.
    public void equip(Equipment equipment){
        /* switch (equipment) {
            case Armor a  -> this.equipArmor(a);
            case Weapon w -> this.equipWeapon(w);
            case Shield s -> this.equipShield(s);
            default -> {/*eccezione}
        } */
        switch (equipment) {
            case Armor a  -> this.equipArmor(a);
            case Weapon w -> this.equipWeapon(w);
            case Shield s -> this.equipShield(s);
            default -> {/*eccezione*/}
        }
    }

    private void equipArmor(Equipment equipment){
        switch (equipment) {
            case Greaves g    -> equipGreaves(g);
            case Chestplate c -> equipChestplate(c);
            case Helmet h     -> equipHelmet(h);
            default -> {/*eccezione*/}
        }   
    }

    private void equipWeapon(Weapon weapon){
        //this.weapon = Optional.of(weapon);

        // se c'è già qualcosa di tipo weapon
        // si può fare con streams
        for (Equipment equipment : equipments) {
            if (equipment instanceof Weapon) {
                this.equipments.remove(equipment);
                this.player.addOrRemoveAttacks(-equipment.getValue());
            }
        }
        this.equipments.add(weapon);
        this.player.addOrRemoveAttacks(weapon.getValue());
        System.out.println("Weapon equipped: " + weapon);
    }

    private void equipShield(Shield shield){
        //this.shield = Optional.of(shield);

        for (Equipment equipment : equipments) {
            if (equipment instanceof Shield) {
                this.equipments.remove(equipment);
                this.player.addOrRemoveShields(-equipment.getValue());
            }
        }
        this.equipments.add(shield);
        // invocare l'aggiornamento statistiche di PlayerController
        this.player.addOrRemoveShields(shield.getValue());
        System.out.println("Shield equipped: " + shield);
    }


    // potrei collassare questi 3 metodi in uno che aggiunge solamente le statistiche e il resto farlo in equipArmor()? 
    // unico problema forse: potrei perdere l'informazione di che tipo preciso è ogni armor quando lo inserisco in lista

    /***************** VOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO ******************************* */
    // eureka!!!!!!!! fai metodo ricalcolaStats() su Player che resetta stats al default poi cicla cosa ha nell'inventario  e aggiunge stats una per una

    // forse dovrei gestire le abilità speciali dentro Armor, dato che ho fatto metodo apposta per quello. però strippo -> inventory controller ???
    private void equipGreaves(Greaves greaves) {
        //this.greaves = Optional.of(greaves);

        for (Equipment equipment : equipments) {
            if (equipment instanceof Greaves) {
                equipments.remove(equipment);
                this.player.addOrRemoveHP(-equipment.getValue());
            }
        }
        this.equipments.add(greaves);
        // invocare l'aggiornamento statistiche di PlayerController
        this.player.addOrRemoveHP(greaves.getValue());
        this.player.addPositionModifiers(greaves.getValue());
        System.out.println("Greaves equipped: " + greaves);
    }

    private void equipChestplate(Chestplate chestplate) {
        //this.chestplate = Optional.of(chestplate);

        for (Equipment equipment : equipments) {
            if (equipment instanceof Chestplate) {
                equipments.remove(equipment);
                this.player.addOrRemoveHP(-equipment.getValue());
            }
        }
        this.equipments.add(chestplate);
        // invocare l'aggiornamento statistiche di PlayerController
        this.player.addOrRemoveHP(chestplate.getValue());
        this.player.addStealProtections(chestplate.getValue());
        System.out.println("Chestplate equipped: " + chestplate);
    }

    private void equipHelmet(Helmet helmet) {
        //this.helmet = Optional.of(helmet);

        for (Equipment equipment : equipments) {
            if (equipment instanceof Helmet) {
                equipments.remove(equipment);
                this.player.addOrRemoveHP(-equipment.getValue());
            }
        }
        this.equipments.add(helmet);
        // invocare l'aggiornamento statistiche di PlayerController
        this.player.addOrRemoveHP(helmet.getValue());
        this.player.addRerolls(helmet.getValue());
        System.out.println("Helmet equipped: " + helmet);
    }

    public void printEquipment() {
        System.out.println("--------------------------------------------");
        System.out.println("\nInventario di "+ player.toString() +": ");
        for (Equipment equipment : equipments) {
            System.out.println(equipment.toString() +", added value -> "+ equipment.getValue());
        }
        // DEBUG
        /* System.out.println("\n"+ chestplate.toString());
        System.out.println(helmet.toString());
        System.out.println(greaves.toString());
        System.out.println(weapon.toString());
        System.out.println(shield.toString()); */
    }
}
