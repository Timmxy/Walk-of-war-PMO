package Model;

public class Fight {
    private Player player1;
    private Player player2;
    int p2Hp; //punti vita del difensore
    int dmg = 1; // danno inflitto
    int currentAtks; //contatore attacchi del giocatore in attacco
    int currentShields; //contatore utilizzo protezioni
    
    public Fight(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.p2Hp = player2.getCurrentHp();
        this.currentAtks = player1.getMaxAtks();
        this.currentShields = player1.getMaxShields();
    }

    // Metodo per risolvere un turno di combattimento
    public String resolveRound(String actionPlayer1, String actionPlayer2) {
        String result = "";
        while (player1.getCurrentHp() > 0 && player2.getCurrentHp() > 0) {

            // Risolvi l'azione del giocatore 1 contro il giocatore 2
            result += resolveAction(player1, player2, actionPlayer1);
            
            // Risolvi l'azione del giocatore 2 contro il giocatore 1
            result += resolveAction(player2, player1, actionPlayer2);  
        }
        // Verifica se il giocatore 1 è stato eliminato
        if (isPlayerOut(player1)) {
            result += "\n" + player2.getName() + " ha VINTO lo scontro!";
        }
        // Verifica se il giocatore 2 è stato eliminato
        if (isPlayerOut(player2)) {
            result += "\n" + player1.getName() + " ha VINTO lo scontro!";
        }
        // Resetto le statistiche dei giocatori che hanno partecipato allo scontro
        player1.resetCurrentToMaxStats();
        player2.resetCurrentToMaxStats();
        return result;
    }

    // Metodo per risolvere un'azione di un giocatore contro l'altro
    private String resolveAction(Player player1, Player player2, String action) {
        String result = player1.getName() + " ha scelto " + action + ". ";
        switch (action.toLowerCase()) {
            case "spara" -> {
                if (currentAtks > 0) {
                    if (!player2.isDefending()) {
                        p2Hp -= dmg;  // Decremento vita in base all'attacco
                        result += player2.getName() + " ha perso 1 punto vita.";
                    } else {
                        result += player2.getName() + " si è difeso e non ha subito danni.";
                    }
                    currentAtks -= 1;  // Decrementa le munizioni dell'attaccante
                } else {
                    result += "Ma non ha munizioni!";
                }
            }
            case "ricarica" -> {
                if(currentAtks < player1.getMaxAtks()){
                    currentAtks += 1;  // Ricarica una munizione
                    result += player1.getName() + " ha ricaricato una munizione.";
                } else {
                    System.out.println("Ma l'arma è già carica");
                }
            }
            case "proteggiti" -> {
                if (currentShields > 0){
                    player1.setDefending(true);  // Il giocatore si protegge
                    currentShields -= 1; // Decremento del contatore shields
                    result += player1.getName() + " si protegge.";
                } else {
                    System.out.println("Ma ha terminato le protezioni");
                }
            }
            default -> result += "Azione non valida.";
        }
        return result;
    }

    // Metodo per verificare se un giocatore è eliminato
    public boolean isPlayerOut(Player player) {
        return player.getCurrentHp() <= 0;
    }
}
