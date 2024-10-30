package Controller;

import Model.CPUPlayer;
import Model.Fight;
import Model.FightActions;
import Model.Player;
import View.FightView;
import javafx.scene.input.KeyCode;
import java.util.Optional;

import java.util.Random;

public class FightController {

    // DICHIARAZIONE CAMPI
    private Fight fight;
    Optional<FightActions> actionPlayer1 = Optional.empty();
    Optional<FightActions> actionPlayer2 = Optional.empty();

    // view
    private FightView view;
    
    // COSTRUTTORE
    public FightController(Fight f) {
        this.fight = f;
        this.view = new FightView(this);
    }

    // METODI

    // metodo per aggiungere player alla fight
    public void addPlayersToFight(Player p1, Player p2) {
        this.fight.setPlayersFighting(p1, p2);
        this.view.refreshStatsTexts(p1, p2);
        this.handleFightTurn();
    }

    public void handleFightTurn() {
        
        // prendo le azioni di ciascun player
        if (this.fight.getPlayer1() instanceof CPUPlayer) {
            this.actionPlayer1 = Optional.of(this.fight.getPlayer1().chooseFightAction());
        }
        if (this.fight.getPlayer2() instanceof CPUPlayer) {
            this.actionPlayer2 = Optional.of(this.fight.getPlayer2().chooseFightAction());
        }
        
        // quando le ho tutte e due, calcolo l'esito di questo turno e aggiorno le statistiche
        // if ho tutte e sue calcolo subito, altrimenti aspetta input tastiera
        if (this.actionPlayer1.isPresent() && this.actionPlayer2.isPresent()) {
            this.computeTurnResult();
        }
    }

    // metodo per elaborare input tastiera (interazione utente reale) -> alla fine controlla se ha tutte e due e chiama calcolo -> dentro a metodo per calcolo faccio ripartire handleFightTurn
    // però prima controllo se uno dei due è morto NB ricordarsi di risettare ad empty gli oprional delle azioni -> a fine turno refreshare anche la view

    public void interpretUserInput(KeyCode buttonPressed){
        //TODO:controlli input
        // Ottieni la scelta del giocatore reale (da console o GUI)
        // Questo potrebbe essere un input dall'utente
        
        
        // Restituisci la mossa corrispondente
        // player 1 [q: attack, w: defend, e: recharge]
        // player 2 [i: attack, o: defend, p: recharge]

        // ho già i risultati non posso più premere input
        if (!this.actionPlayer1.isPresent() || !this.actionPlayer2.isPresent()) {
            
            switch (buttonPressed) {
                case KeyCode.Q:
                    System.out.println("q");
                    if (this.actionPlayer1.isEmpty()) {
                        this.actionPlayer1 = Optional.of(FightActions.ATTACK);
                    }
                    break;
                case KeyCode.W:
                    System.out.println("w");
                    if (this.actionPlayer1.isEmpty()) {
                        this.actionPlayer1 = Optional.of(FightActions.DEFEND);
                    }
                    break;
                case KeyCode.E:
                    System.out.println("e");
                    if (this.actionPlayer1.isEmpty()) {
                        this.actionPlayer1 = Optional.of(FightActions.RECHARGE);
                    } 
                    break;
                case KeyCode.I:
                    System.out.println("i");
                    if (this.actionPlayer2.isEmpty()) {
                        this.actionPlayer2 = Optional.of(FightActions.ATTACK);
                    } 
                    break;
                case KeyCode.O:
                    System.out.println("o");
                    if (this.actionPlayer2.isEmpty()) {
                        this.actionPlayer2 = Optional.of(FightActions.DEFEND);
                    } 
                    break;
                case KeyCode.P:
                    System.out.println("p");
                    if (this.actionPlayer2.isEmpty()) {
                        this.actionPlayer2 = Optional.of(FightActions.RECHARGE);
                    } 
                    break;
                default:
                    throw new IllegalArgumentException("Input errato!");
            }

            if (this.actionPlayer1.isPresent() && this.actionPlayer2.isPresent()) {
                this.computeTurnResult();
            }
        } else {
            System.out.println("Un input e' gia' stato inserito.");
        }
    }

    private void computeTurnResult() {
        //stampa le scelte di entrambi i giocatori
        System.out.println(this.fight.getPlayer1().toString() + " ha scelto " + this.actionPlayer1.get() + ". "
        + this.fight.getPlayer2().toString() + " ha scelto " + this.actionPlayer2.get());
        
        //COMBINAZIONI POSSIBILI PER LO SCONTRO
        //attaccano entrambi
        if (this.actionPlayer1.get() == FightActions.ATTACK && this.actionPlayer2.get() == FightActions.ATTACK){
            if(this.fight.getPlayer1().getCurrentAtks() > 0){ //controlla se ha abbastanza stamina per attaccare
                this.fight.getPlayer1().attackUsed(); //decrememnta la stamina
                this.fight.getPlayer2().takeDmg(); //giocatore 2 prende danno
                System.out.println(this.fight.getPlayer1().toString() + " ha attaccato con successo!");
            }else
                System.out.println(this.fight.getPlayer1().toString() + " non ha abbastanza stamina per eseguire l'attacco");
            if(this.fight.getPlayer2().getCurrentAtks() > 0){
                this.fight.getPlayer2().attackUsed();
                this.fight.getPlayer1().takeDmg();
                System.out.println(this.fight.getPlayer2().getName() + " ha attaccato con successo!");
            }else
                System.out.println(this.fight.getPlayer2().getName() + " non ha abbastanza stamina per eseguire l'attacco");
        }
        //difendono entrambi
        else if(this.actionPlayer1.get() == FightActions.DEFEND && this.actionPlayer2.get() == FightActions.DEFEND){
            if(this.fight.getPlayer1().getcurrentShields() > 0){ //controlla se può difendere
                this.fight.getPlayer1().shieldUsed(); //decrementa di 1 gli scudi a disposizione
                System.out.println(this.fight.getPlayer1().toString() + " si difende");
            }else
                System.out.println(this.fight.getPlayer1().toString() + " ha terminato gli utilizzi dello scudo");
            if(this.fight.getPlayer2().getcurrentShields() > 0){
                System.out.println(this.fight.getPlayer2().getName() + " si difende");
                this.fight.getPlayer2().shieldUsed();
            }else
                System.out.println(this.fight.getPlayer2().getName() + " ha terminato gli utilizzi dello scudo");
        }
        //ricaricano entrambi
        else if(this.actionPlayer1.get() == FightActions.RECHARGE && this.actionPlayer2.get() == FightActions.RECHARGE){
            if(this.fight.getPlayer1().getCurrentAtks() < this.fight.getPlayer1().getMaxAtks()){ //controlla se la stamina è già al massimo
                this.fight.getPlayer1().increaseStamina(); //incrementa di 1 la stamina
                System.out.println(this.fight.getPlayer1().toString() + " recupera un po' di stamina");
            }else //non può ricaricare più stamina di quella massima
                System.out.println(this.fight.getPlayer1().toString() + " ha la stamina al massimo");
            if(this.fight.getPlayer2().getCurrentAtks() < this.fight.getPlayer2().getMaxAtks()){
                System.out.println(this.fight.getPlayer2().getName() + " recupera un po' di stamina");
                this.fight.getPlayer2().increaseStamina();
            }else
                System.out.println(this.fight.getPlayer2().getName() + " ha la stamina al massimo");
        }
        //giocatore 1 attacca e giocatore 2 si difende
        else if (this.actionPlayer1.get() == FightActions.ATTACK && this.actionPlayer2.get() == FightActions.DEFEND){
            if(this.fight.getPlayer1().getCurrentAtks() > 0){
                this.fight.getPlayer1().attackUsed();
                System.out.println(this.fight.getPlayer1().toString() + " attacca");
            }else
                System.out.println(this.fight.getPlayer1().toString() + " non ha abbastanza stamina per eseguire l'attacco");
            if(this.fight.getPlayer2().getcurrentShields() > 0){
                this.fight.getPlayer2().shieldUsed();
                System.out.println(this.fight.getPlayer2().getName() + " si difende con successo!");
            }else
                System.out.println(this.fight.getPlayer2().getName() + " ha terminato gli utilizzi dello scudo");
        }
        //giocatore 1 si difende e giocatore 2 attacca
        else if (this.actionPlayer1.get() == FightActions.DEFEND && this.actionPlayer2.get() == FightActions.ATTACK){
            if(this.fight.getPlayer2().getCurrentAtks() > 0){
                this.fight.getPlayer2().attackUsed();
                System.out.println(this.fight.getPlayer2().getName() + " attacca");
            }else
                System.out.println(this.fight.getPlayer2().getName() + " non ha abbastanza stamina per eseguire l'attacco");
            if(this.fight.getPlayer1().getcurrentShields() > 0){
                this.fight.getPlayer1().shieldUsed();
                System.out.println(this.fight.getPlayer1().toString() + " si difende con successo!");
            }else
                System.out.println(this.fight.getPlayer1().toString() + " ha terminato gli utilizzi dello scudo");
        }
        //giocatore 1 attacca e giocatore 2 recupera stamina
        else if (this.actionPlayer1.get() == FightActions.ATTACK && this.actionPlayer2.get() == FightActions.RECHARGE) {
            if(this.fight.getPlayer1().getCurrentAtks() > 0){
                this.fight.getPlayer1().attackUsed();
                this.fight.getPlayer2().takeDmg();
                System.out.println(this.fight.getPlayer1().toString() + " ha attaccato con successo!");
            }else
                System.out.println(this.fight.getPlayer1().toString() + " non ha abbastanza stamina per eseguire l'attacco");
            if(this.fight.getPlayer2().getCurrentAtks() < this.fight.getPlayer2().getMaxAtks()){
                this.fight.getPlayer2().increaseStamina();
                System.out.println(this.fight.getPlayer2().getName() + " recupera un po' di stamina");
            }else
                System.out.println(this.fight.getPlayer2().getName() + " ha la stamina al massimo");
        }
        //giocatore 1 recupera stamina e giocatore 2 attacca
        else if (this.actionPlayer1.get() == FightActions.RECHARGE && this.actionPlayer2.get() == FightActions.ATTACK) {
            if(this.fight.getPlayer1().getCurrentAtks() < this.fight.getPlayer1().getMaxAtks()){
                this.fight.getPlayer1().increaseStamina();
                System.out.println(this.fight.getPlayer1().toString() + " recupera un po' di stamina");
            }else
                System.out.println(this.fight.getPlayer1().toString() + " ha la stamina al massimo");
            if(this.fight.getPlayer2().getCurrentAtks() > 0){
                this.fight.getPlayer2().attackUsed();
                this.fight.getPlayer1().takeDmg();
                System.out.println(this.fight.getPlayer1().toString() + " ha attaccato con successo!");
            }else
                System.out.println(this.fight.getPlayer1().toString() + " non ha abbastanza stamina per eseguire l'attacco");
        }
        // se nessuno è ancora morto continua la battaglia
        if (this.fight.getPlayer1().getCurrentHp() > 0 && this.fight.getPlayer2().getCurrentHp() > 0) {
            this.actionPlayer1 = Optional.empty();
            this.actionPlayer2 = Optional.empty();
            this.handleFightTurn();
        } else if (this.fight.getPlayer1().getCurrentHp() == 0) {
            // ha perso player 1, lo rimando a matchController

            System.out.println("\n"+this.fight.getPlayer1().toString()+" ha perso lo scontro!!!");
        } else {
            // ha perso player 2
            System.out.println("\n"+this.fight.getPlayer2().toString()+" ha perso lo scontro!!!");
        }
    }

    // // metodo per lo svolgimento della battaglia, va avanti finchè uno esaurisce hp
    // public void resolveFight(boolean  isPlayer1CPU, boolean isPlayer2CPU){
    //     //stampe con informazioni per il combattimento
    //     System.out.println("Associazione tasti per combattimento");
    //     System.out.println("player 1 [q: attacco, w: difesa, e: ricarica]");
    //     System.out.println("player 2 [i: attacco, o: difesa, p: ricarica]");
    //     while (!isPlayerOut(this.fight.getPlayer1()) && !isPlayerOut(this.fight.getPlayer2())) {
    //         //stato dei due giocatori prima di ogni turno
    //         view.displayCombatStatus(fight.getCombatStatus(this.fight.getPlayer1(), this.fight.getPlayer2()));
    //         // Turno del giocatore 1
    //         actionPlayer1 = getAction(isPlayer1CPU, this.fight.getPlayer1());
    //         fight.resolveRound(actionPlayer1, actionPlayer2);

    //         //se uno dei due muore fare break; (non mi garba perchè il 2 potrebbe difendersi dopo il break)

    //         // Turno del giocatore 2
    //         actionPlayer2 = getAction(isPlayer2CPU, this.fight.getPlayer2());
    //         fight.resolveRound(actionPlayer2, actionPlayer1);
    //     }//fine ciclo

    //     //TODO: può finire in pareggio quindi riguardare queste condizioni
    //     // Verifica se il giocatore 1 è stato eliminato
    //     if (isPlayerOut(this.fight.getPlayer1()) && isPlayerOut(this.fight.getPlayer2())) {
    //         System.out.println("PAREGGIO: i giocatori si sono sconfitti a vicenda");
    //     }
    //     else if (isPlayerOut(this.fight.getPlayer1())) {
    //         System.out.println(this.fight.getPlayer2().getName() + " ha VINTO lo scontro!");
    //     }
    //     // Verifica se il giocatore 2 è stato eliminato
    //     else if (isPlayerOut(this.fight.getPlayer2())) {
    //         System.out.println(this.fight.getPlayer1().getName() + " ha VINTO lo scontro!");
    //     }
    //     // Resetto le statistiche dei giocatori che hanno partecipato allo scontro
    //     this.fight.getPlayer1().resetCurrentToMaxStats();
    //     this.fight.getPlayer2().resetCurrentToMaxStats();
    // }

    // // Restituisce la mossa a seconda che il giocatore sia reale o CPU
    // private FightActions getAction(boolean isCpu, Player player) {
    //     if (isCpu) {
    //         return cpuDecision(player); // Logica CPU
    //     } else {
    //         try {
    //             return view.interpretUserInput(); // Logica giocatore reale
    //         } catch (Exception e) {
    //             // TODO: handle exception
    //             return null;
    //         }
    //     }
    // }

    // Metodo per determinare la scelta della CPU
    public FightActions cpuDecision(Player player){
        FightActions choise;
        Random rnd = new Random();
        boolean randomChoise = rnd.nextBoolean();

        //prima azione random tra attacco/difende
        if(randomChoise){
            choise = FightActions.ATTACK;
        } else {
            choise = FightActions.DEFEND;
        }
        if(player.getCurrentAtks() < player.getMaxAtks() -1){ //TODO: numero magico
            choise = FightActions.RECHARGE;
        }
        return choise;
    }

    // metodo per verificare se un giocatore è eliminato
    public boolean isPlayerOut(Player player) {
        return  player.getCurrentHp() <= 0;
    }

    // metodo per ritornare la propria view al match controller
    public FightView getView() {
        return this.view;
    }
}
