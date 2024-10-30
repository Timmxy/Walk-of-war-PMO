package Controller;

import Equipment.Equipment;
import Model.CPUPlayer;
import Model.Player;
import Model.TileEffectEvent;
import Model.TileEffectListener;
import java.util.List;
import java.util.Optional;
import java.util.Random;

//Controller che regola le azioni dei Player in gioco
public class PlayerController implements TileEffectListener {

    // DICHIARAZIONE VARIABILI
    private Random rnd;
    private List<Player> players;

    //ES.: aumenta/diminuisce le statistiche quando occorre furto di armatura/arma/scudo oppure a fine scontro
    //ES.: chiama la funzione addItemToInventory()


    // COSTRUTTORE
    public PlayerController(List<Player> ps) {
        this.players = ps;
        this.rnd = new Random();
    }


    // quando un Player compra (e quindi equipaggia) un Equipment
    public void equipmentBought(Player p, Equipment e) {
        p.addItemToInventory(e);
    }
    

    // muove la pedina del giocatore sulla Board
    public int movePlayer(Player player, int diceRoll, int lastTile) {
        int newPosition = player.getPawnPosition() + diceRoll;

        if (newPosition >= lastTile) {
            newPosition = lastTile - 1;
        }

        if (newPosition <= 0) {
            newPosition = 0;
        }

        player.updatePosition(newPosition);
        
        System.out.println("-> "+player.getName() + " si muove alla casella " + player.getPawnPosition());
        return player.getPawnPosition();
    }

    // metodo per verificare la condizione di vittoria
    public boolean checkWinCondition(Player player, int lastTile) {
        System.out.println("Win Condition? " + (player.getPawnPosition() == lastTile));
        return player.getPawnPosition() == lastTile;
    }

    

    // metodo che modifica lo stato del player quando occorre l'evento delle tile
    public void onTileEffectActivated(TileEffectEvent event) {

        // DEBUG
        System.out.println("Attivazione Casella...");

        Player player = event.getPlayer();
        switch (event.getVariant()) {
            case BONUS_MONEY:
                player.addMoney(event.getEffectValue());
                System.out.println(player.getName() + " ha guadagnato " + event.getEffectValue() + " monete.");
                break;

            case MALUS_MONEY:
                player.removeMoney(event.getEffectValue());
                System.out.println(player.getName() + " ha perso " + event.getEffectValue() + " monete.");
                break;

            case BONUS_POSITION:
                player.updatePosition(player.getPawnPosition() + event.getEffectValue());
                System.out.println(player.getName() + " avanza di " + event.getEffectValue() + " posizioni.");
                break;

            case MALUS_POSITION:
                player.updatePosition(player.getPawnPosition() - event.getEffectValue());
                System.out.println(player.getName() + " arretra di " + event.getEffectValue() + " posizioni.");
                break;

            case STEAL:
                // controlla quanti player ci sono -> randomizza un altro player (vittima) -> cerca un equipment vittima
                // -> lo rimuove e lo da al player invocante -> rimuove/aggiunge stats ai player 
                this.handleStealEffect(player);
                break;
            
            case EMPTY: // do nothing
                System.out.println("Casella Vuota.");
                break;

            default:
                throw new IllegalArgumentException("Tipo di casella non supportato: " + event.getVariant());
        }
    }

    // si occupa della logica dell'effetto Steal
    private void handleStealEffect(Player player) {
        // trova un altro player casuale
        Player victim = selectRandomPlayer(player);

        if (victim != null) {
            // tenta di rimuovere un equipment casuale dalla vittima
            System.out.println(player.toString() + " cerca di rubare Equipment a: " + victim.toString() + "...");
            if (!victim.hasStealProtection()) {
                // posso rubare
                Equipment stolenEquipment = victim.getStolenEquipment();
    
                if (stolenEquipment != null) {
                    // Aggiungi l'equipment rubato all'inventario del player corrente
                    player.addItemToInventory(stolenEquipment);
                    System.out.println(player.toString() + " ha rubato " + stolenEquipment + " da " + victim.toString() + "!");
                }
            } else {
                // uso automaticamente la protezione
                victim.useStealProtection();
            }
        }
        // DEBUG visualizzo l'inventario di entrambi per verificare
        player.inventoryContents();
        player.printStats();
        victim.inventoryContents();
        victim.printStats();

    }

    // seleziona casualmente un giocatore vittima del furto, che sia diverso da chi lo perpetra
    private Player selectRandomPlayer(Player currentPlayer) {
        Player victim;
        try{
            do {
                victim = this.players.get(this.rnd.nextInt(this.players.size()));
            } while (victim == currentPlayer); // assicura che il player corrente non sia selezionato

            return victim;
        } catch (Exception e) {
            System.out.println("Nessun altro giocatore in gara.");
            return null;
        }
    }

    // contatta il CPU Player per risolvere l'algoritmo: rirollare oppure no
    public boolean getDecisionOnReroll(CPUPlayer player, int currentDiceResult) {
        return player.wantsToRerollDice(currentDiceResult);
    }

    // contatta il CPU Player per risolvere l'algoritmo: usare position modifier o no
    // ritorna 0 se non vuole muoversi, 1 se andare avanti di uno, -1 se tornare indietro di 1
    public int getDecisionOnPositionModifier(CPUPlayer player) {
        return player.wantsToMovePosition();
    }

    // contatta il CPU Player per risolvere l'algoritmo: visitare shop o no
    public boolean getDecisionOnShopVisit(CPUPlayer player) {
        return player.wantsToVisitShop();
    }

    public Optional<Equipment> getDecisionOnShopPurchase(CPUPlayer player, List<Equipment> availableEquipments) {
        return player.wantsToBuySomething(availableEquipments);
    }

    // aggiorna il numero di reroll del player in seguito all'utilizzo tramite bottone view
    public void updateRerollsCount(Player player) {
        player.useReroll();
    }

    // aggiorna il numero di position modifiers del player in seguito all'utilizzo tramite bottone view
    public void updatePosModifiersCount(Player player) {
        player.usePositionModifiers();
    }


}
