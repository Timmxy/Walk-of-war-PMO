package Model;

public class Fight {
    private Player player1;
    private Player player2;

    //scelta di progettazione: no costruttore
    
    //metodo che prende la funzione del di costruttore da passare a match controller
    //così che non ci siano più fight in scena in base al numero di player
    // gestisce l'accoppiamento dei giocatori nello scontro
    public void setPlayersFighting(Player p1, Player p2) {
        this.player1 = p1;
        this.player2 = p2;
    }

    public Player getPlayer1() {
        return this.player1;
    }

    public Player getPlayer2() {
        return this.player2;
    }
}
