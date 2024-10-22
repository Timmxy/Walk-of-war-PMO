package Controller;

import Equipment.Equipment;
import Model.Player;
import Model.TileEffectEvent;
import Model.TileEffectListener;
import java.util.List;
import java.util.Random;

//Controller che regola le azioni dei Player in gioco
public class PlayerController implements TileEffectListener {

    private final static int SHOP_POSITION_MALUS = 2;

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

    // *****************************************************************************************
    // quando finisce la visita allo shop
    public void exitShop(Player p, Equipment e) {
        // calcola quante pos. arretrare dopo la visita allo shop
        int k = PlayerController.SHOP_POSITION_MALUS;
        if (e != null) {
            this.equipmentBought(p, e);
        }
        // SHIT NON HO BOARDCONTROLLER !!!
        this.movePlayer(p, -k, null);
    }

    // quando un Player compra (e quindi equipaggia) un Equipment. Chiamato dal bottone della view (SHOP)
    public void equipmentBought(Player p, Equipment e) {
        p.addItemToInventory(e);
    }
    // *******************************************************************************************
    

    // muove la pedina del giocatore sulla Board
    // ha bisogno di BoardController per controllare che non superi il numero max di caselle della board
    // non vorrei passare alcun controller in giro, quindi potrei usare la costante Board.MAX_TILES spostandola in una classe archivio "Util"
    public void movePlayer(Player player, int diceRoll, BoardController boardController) {
        int newPosition = player.getPawnPosition() + diceRoll;

        if (newPosition >= boardController.getNumberOfTiles()) {
            newPosition = boardController.getNumberOfTiles() - 1;
        }

        System.out.println(newPosition);
        player.updatePosition(newPosition);
        //possiamo risolverlo in modo da non usare board controller?
        System.out.println(newPosition);
        boardController.performActionOnTile(player, newPosition);

        System.out.println(player.getName() + " finisce il turno alla casella " + player.getPawnPosition());
    }

    // metodo per verificare la condizione di vittoria
    public boolean checkWinCondition(Player player, BoardController boardController) {
        System.out.println("win condition? " + player.toString() +" pos: "+ player.getPawnPosition());
        return player.getPawnPosition() == boardController.getNumberOfTiles() - 1;
    }

    // metodo per simulare il lancio del dado
    public int rollDice() {
        return this.rnd.nextInt(6) + 1; // Numero casuale tra 1 e 6
    }

    // metodo che modifica lo stato del player quando occorre l'evento delle tile
    public void onTileEffectActivated(TileEffectEvent event) {

        // DEBUG
        System.out.println("Attivazione Casella...");

        Player player = event.getPlayer();
        switch (event.getVariant()) {
            case BONUS_MONEY:
                player.addMoney(event.getEffectValue());
                System.out.println(player.getName() + " ha guadagna " + event.getEffectValue() + " monete.");
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
        // DEBUG
        player.inventoryContents();
        player.printStats();
        victim.inventoryContents();
        victim.printStats();

    }

    private Player selectRandomPlayer(Player currentPlayer) {
       try{
            Player victim;
            do {
                victim = this.players.get(this.rnd.nextInt(this.players.size()));
            } while (victim == currentPlayer); // assicura che il player corrente non sia selezionato

            return victim;
        } catch (Exception e) {
            System.out.println("Nessun altro giocatore in gara.");
            return null;
        }
    }
}
