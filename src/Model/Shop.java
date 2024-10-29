package Model;

import Equipment.Chestplate;
import Equipment.Equipment;
import Equipment.Greaves;
import Equipment.Helmet;
import Equipment.Rarity;
import Equipment.Shield;
import Equipment.Weapon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Shop {

    public final static int SHOP_POSITION_MALUS = 2;
    private final static int SHOP_CAPACITY = 4;

    // DICHIARAZIONE VARIABILI
    private List<Equipment> contents;
    private Random rnd;
    //da spostare
    private List<Equipment> equipmentArchive;

    // COSTRUTTORE
    public Shop() {
        this.contents =  new ArrayList<>();
        this.rnd = new Random();
        this.equipmentArchive = new ArrayList<>();
        this.initArchive();
    }
    
    // METODI
    public List<Equipment> rerollShop() {
        if (!this.contents.isEmpty()) {
            this.contents = new ArrayList<>();
        }
        // rollare degli equipaggiamenti random
        Equipment e;
        for (int i = 0; i < Shop.SHOP_CAPACITY; i++) {
            // sorteggio un equip. dall'archivio di possibili equip.
            e = this.equipmentArchive.get(this.rnd.nextInt(this.equipmentArchive.size()));
            this.contents.add(e);
        }
        return this.contents;
    }

    // attivato dal bottone della view
    public Equipment buyItem() {
        // scala i soldi
        // ritorna l'oggetto comprato
        return null;
    }

    public List<Equipment> getShopContents() {
        return this.contents;
    }

    public void printShopContents() {
        System.out.println("\nLo Shop contiene:");
        for (Equipment equipment : contents) {
            System.out.println(equipment.toString());
        }
    }
    
    private void initArchive() {
        this.equipmentArchive.add(new Helmet("Elmo di Cuoio", Rarity.COMMON));
        this.equipmentArchive.add(new Chestplate("Corazza di Cuoio", Rarity.COMMON));
        this.equipmentArchive.add(new Greaves("Gambali di Cuoio", Rarity.COMMON));

        this.equipmentArchive.add(new Helmet("Elmo di Bronzo", Rarity.RARE));
        this.equipmentArchive.add(new Chestplate("Corazza di Bronzo", Rarity.RARE));
        this.equipmentArchive.add(new Greaves("Gambali di Bronzo", Rarity.RARE));

        this.equipmentArchive.add(new Helmet("Elmo di Argento", Rarity.EPIC));
        this.equipmentArchive.add(new Chestplate("Corazza di Argento", Rarity.EPIC));
        this.equipmentArchive.add(new Greaves("Gambali di Argento", Rarity.EPIC));

        this.equipmentArchive.add(new Helmet("Elmo dell'Eroe", Rarity.LEGENDARY));
        this.equipmentArchive.add(new Chestplate("Corazza dell'Eroe", Rarity.LEGENDARY));
        this.equipmentArchive.add(new Greaves("Gambali dell'Eroe", Rarity.LEGENDARY));

        this.equipmentArchive.add(new Weapon("Pugnale d'Osso", Rarity.COMMON));
        this.equipmentArchive.add(new Weapon("Mazza Ferrata", Rarity.RARE));
        this.equipmentArchive.add(new Weapon("Alabarda d'Oro ", Rarity.EPIC));
        this.equipmentArchive.add(new Weapon("Spada dell'Eroe", Rarity.LEGENDARY));

        this.equipmentArchive.add(new Shield("Scudo di Legno", Rarity.COMMON));
        this.equipmentArchive.add(new Shield("Scudo di Bronzo", Rarity.RARE));
        this.equipmentArchive.add(new Shield("Scudo di Argento", Rarity.EPIC));
        this.equipmentArchive.add(new Shield("Scudo dell'Eroe", Rarity.LEGENDARY));
    }



}
