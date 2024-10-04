package Model;
public class Fight {
    private Player player1;
    private Player player2;

    public Fight(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    // Metodo per risolvere un turno di combattimento
    public String resolveRound(String actionPlayer1, String actionPlayer2) {
        String result = "";

        // Risolvi l'azione del giocatore 1 contro il giocatore 2
        result += resolveAction(player1, player2, actionPlayer1);

        // Verifica se il giocatore 2 è stato eliminato
        if (isPlayerOut(player2)) {
            result += "\n" + player1.getName() + " ha VINTO lo scontro!";
            return result;
        }

        // Risolvi l'azione del giocatore 2 contro il giocatore 1
        result += resolveAction(player2, player1, actionPlayer2);

        // Verifica se il giocatore 1 è stato eliminato
        if (isPlayerOut(player1)) {
            result += "\n" + player2.getName() + " ha VINTO lo scontro!";
        }

        // Resetto le statistiche dei giocatori che hanno partecipato allo scontro
        player1.resetCurrentToMaxStats();
        player2.resetCurrentToMaxStats();

        return result;
    }

    // Metodo per risolvere un'azione di un giocatore contro l'altro
    private String resolveAction(Player player1, Player player2, String action) {
        String result = player1.getName() + " ha scelto " + action + ". ";
        //ITERARE TOT VOLTE E RICORDARSI DI METTERE ISDEFENDING FALSE AD OGNI FINE TURNO CON LA FUNZIONE RESET DEFENDING
        switch (action.toLowerCase()) {
            case "spara":
                if (player1.getCurrentAtks() > 0) {
                    if (!player2.isDefending()) {
                        player2.addOrRemoveHP(-1);  // Decremento vita in base all'attacco
                        result += player2.getName() + " ha perso 1 punto vita.";
                    } else {
                        result += player2.getName() + " si è difeso e non ha subito danni.";
                    }
                    player1.addOrRemoveAttacks(-1);  // Decrementa le munizioni dell'attaccante
                } else {
                    result += "Ma non ha munizioni!";
                }
                break;
            case "ricarica":
                player1.addOrRemoveAttacks(1);  // Ricarica una munizione
                result += player1.getName() + " ha ricaricato una munizione.";
                break;
            case "proteggiti":
                player1.setDefending(true);  // Il giocatore si protegge
                player1.addOrRemoveShields(-1); // Decremento del contatore shields
                result += player1.getName() + " si protegge.";
                break;
            default:
                result += "Azione non valida.";
                break;
        }

        return result;
    }

    // Metodo per verificare se un giocatore è eliminato
    public boolean isPlayerOut(Player player) {
        return player.getCurrentHp() <= 0;
    }
}
