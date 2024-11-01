package Player;

import Equipment.Chestplate;
import Equipment.Equipment;
import Equipment.Greaves;
import Equipment.Helmet;
import Equipment.Shield;
import Equipment.Weapon;
import Model.Player;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Inventory {

    //DICHIARAZIONI VARIABILI
    private List<Equipment> equipments;
    private Random rnd;

    private Player player;

    //COSTRUTTORE
    public Inventory(Player p){
        this.player = p;
        this.equipments = new ArrayList<>();
        this.rnd = new Random();
    }

    // RISOLTO!!! a me da errore -> The Java feature 'Pattern Matching in Switch' is only available with source level 21 and above.
    public void addEquipment(Equipment equipment){
        switch (equipment) {
            case Greaves g      -> {this.equip(g); System.out.println("Equipaggiato: "+ g.toString());}       // equip() -> sout azione equipaggiamento specifica
            case Chestplate c   -> {this.equip(c);  System.out.println("Equipaggiato: "+ c.toString());}
            case Helmet h       -> {this.equip(h);  System.out.println("Equipaggiato: "+ h.toString());}
            case Weapon w       -> {this.equip(w);  System.out.println("Equipaggiato: "+ w.toString());}
            case Shield s       -> {this.equip(s);  System.out.println("Equipaggiato: "+ s.toString());}
            default -> {throw new IllegalArgumentException("Non esiste questo tipo di Equipaggiamento");}
        }
    }

    private void equip(Equipment e) {
        checkAlreadyEquipped(e);
        this.equipments.add(e);
        this.player.computeStats(equipments);
    }

    private void checkAlreadyEquipped(Equipment e) {
        // oggetto da rimuovere se c'è
        Optional<Equipment> toRemove = Optional.empty();

        // scorro la lista degli equipaggiati
        for (Equipment equipment : equipments) {
            // se c'è già n equipment dello stesso tipo
            if (equipment.getClass() == e.getClass()) {
                System.out.println(this.player.toString()+" possiede gia' un Equipaggiamento dello stesso genere, percio' viene scartato: "+equipment.toString());
                toRemove = Optional.of(equipment);
            }
        }
        // se qualcosa deve essere rimosso
        if (!toRemove.isEmpty()) {
            this.equipments.remove(toRemove.get());
        }
        this.player.computeStats(equipments);
    }

    // quando viene derubato, ritorna un oggetto random per il ladro
    public Equipment removeRandomEquipment() {
        if (!this.equipments.isEmpty()) {
            Equipment toRemove = this.equipments.get(this.rnd.nextInt(equipments.size()));
            this.equipments.remove(toRemove);
            System.out.println(this.player.toString() +" perde l'Equipment: "+ toRemove.toString() +"!!!");
            // aggiorna le stats
            this.player.computeStats(this.equipments);
            return toRemove;
        }
        else {
            System.out.println(this.player.toString() +" non ha alcun Equipment da rubare!!!");
            return null;
        }
    }

    // stampa per visualizzare inventario
    public void printEquipment() {
        // separatore
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("\nInventario di "+ player.toString() +": ");
        for (Equipment equipment : equipments) {
            System.out.println(equipment.toString() +", added value -> "+ equipment.getValue());
        }
        // separatore
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
