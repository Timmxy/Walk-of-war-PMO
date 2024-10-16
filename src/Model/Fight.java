package Model;

public class Fight {
    private final Player player1;
    private final Player player2;

    public Fight(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    // Metodo per risolvere un turno di combattimento
    public void resolveRound(FightActions actionPlayer1, FightActions actionPlayer2) {
        /*while (player1.getCurrentHp() > 0 && player2.getCurrentHp() > 0) { TODO ITERAZIONE DENTRO AL FIGHT CONTROLLER

        // Risolvi l'azione del giocatore 1 contro il giocatore 2
        result += resolveAction(player1, player2, actionPlayer1);
        
        // Risolvi l'azione del giocatore 2 contro il giocatore 1
        result += resolveAction(player2, player1, actionPlayer2);  */

        //stampa le scelte di entrambi i giocatori
        System.out.println(player1.getName() + " ha scelto " + actionPlayer1 + ". "
        + player2.getName() + " ha scelto " + actionPlayer2);
        
        //COMBINAZIONI POSSIBILI PER LO SCONTRO
        //attaccano entrambi
        if (actionPlayer1 == FightActions.ATTACK && actionPlayer2 == FightActions.ATTACK){
            if(player1.getCurrentAtks() > 0){ //controlla se ha abbastanza stamina per attaccare
                player1.attackUsed(); //decrememnta la stamina
                player2.takeDmg(); //giocatore 2 prende danno
                System.out.println(player1.getName() + " ha attaccato con successo!");
            }else
                System.out.println(player1.getName() + " non ha abbastanza stamina per eseguire l'attacco");
            if(player2.getCurrentAtks() > 0){
                player2.attackUsed();
                player1.takeDmg();
                System.out.println(player2.getName() + " ha attaccato con successo!");
            }else
                System.out.println(player2.getName() + " non ha abbastanza stamina per eseguire l'attacco");
        }
        //difendono entrambi
        else if(actionPlayer1 == FightActions.DEFEND && actionPlayer2 == FightActions.DEFEND){
            if(player1.getcurrentShields() > 0){ //controlla se può difendere
                player1.shieldUsed(); //decrementa di 1 gli scudi a disposizione
                System.out.println(player1.getName() + " si difende");
            }else
                System.out.println(player1.getName() + " ha terminato gli utilizzi dello scudo");
            if(player2.getcurrentShields() > 0){
                System.out.println(player2.getName() + " si difende");
                player2.shieldUsed();
            }else   
                System.out.println(player2.getName() + " ha terminato gli utilizzi dello scudo");
        }
        //ricaricano entrambi
        else if(actionPlayer1 == FightActions.RECHARGE && actionPlayer2 == FightActions.RECHARGE){
            if(player1.getCurrentAtks() < player1.getMaxAtks()){ //controlla se la stamina è già al massimo
                player1.increaseStamina(); //incrementa di 1 la stamina
                System.out.println(player1.getName() + " recupera un po' di stamina");
            }else //non può ricaricare più stamina di quella massima
                System.out.println(player1.getName() + " ha la stamina al massimo");
            if(player2.getCurrentAtks() < player2.getMaxAtks()){
                System.out.println(player2.getName() + " recupera un po' di stamina");
                player2.increaseStamina();
            }else
                System.out.println(player2.getName() + " ha la stamina al massimo");
        }
        //giocatore 1 attacca e giocatore 2 si difende
        else if (actionPlayer1 == FightActions.ATTACK && actionPlayer2 == FightActions.DEFEND){
            if(player1.getCurrentAtks() > 0){
                player1.attackUsed();
                System.out.println(player1.getName() + " attacca");
            }else
                System.out.println(player1.getName() + " non ha abbastanza stamina per eseguire l'attacco");
            if(player2.getcurrentShields() > 0){
                player2.shieldUsed();
                System.out.println(player2.getName() + " si difende con successo!");
            }else
                System.out.println(player2.getName() + " ha terminato gli utilizzi dello scudo");
        }
        //giocatore 1 si difende e giocatore 2 attacca
        else if (actionPlayer1 == FightActions.DEFEND && actionPlayer2 == FightActions.ATTACK){
            if(player2.getCurrentAtks() > 0){   
                player2.attackUsed();
                System.out.println(player2.getName() + " attacca");
            }else
                System.out.println(player2.getName() + " non ha abbastanza stamina per eseguire l'attacco");
            if(player1.getcurrentShields() > 0){
                player1.shieldUsed();
                System.out.println(player1.getName() + " si difende con successo!");
            }else
                System.out.println(player1.getName() + " ha terminato gli utilizzi dello scudo");
        }
        //giocatore 1 attacca e giocatore 2 recupera stamina
        else if (actionPlayer1 == FightActions.ATTACK && actionPlayer2 == FightActions.RECHARGE) {
            if(player1.getCurrentAtks() > 0){
                player1.attackUsed();
                player2.takeDmg();
                System.out.println(player1.getName() + " ha attaccato con successo!");
            }else
                System.out.println(player1.getName() + " non ha abbastanza stamina per eseguire l'attacco");
            if(player2.getCurrentAtks() < player2.getMaxAtks()){     
                player2.increaseStamina();
                System.out.println(player2.getName() + " recupera un po' di stamina");
            }else
                System.out.println(player2.getName() + " ha la stamina al massimo");
        }
        //giocatore 1 recupera stamina e giocatore 2 attacca 
        else if (actionPlayer1 == FightActions.RECHARGE && actionPlayer2 == FightActions.ATTACK) {
            if(player1.getCurrentAtks() < player1.getMaxAtks()){
                player1.increaseStamina();
                System.out.println(player1.getName() + " recupera un po' di stamina");
            }else
                System.out.println(player1.getName() + " ha la stamina al massimo");
            if(player2.getCurrentAtks() > 0){
                player2.attackUsed();
                player1.takeDmg();
                System.out.println(player1.getName() + " ha attaccato con successo!");
            }else   
                System.out.println(player1.getName() + " non ha abbastanza stamina per eseguire l'attacco");
        }
    }    
}
