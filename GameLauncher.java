
public class GameLauncher {

    public static void main(String[] args) {
        Player pl1 = new Player(0, "pl1");
        pl1.addItemToInvetory(new Helmet("Ciro's Helmet", Rarity.LEGENDARY));
        pl1.addItemToInvetory(new Chestplate("Ciro's Chestplate", Rarity.COMMON));
    }
}
