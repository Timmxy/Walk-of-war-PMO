package Model;

public class RealPlayer extends Player {
    // Variabili che il Controller modificherà in base alle scelte prese dalla View
    private boolean wantsToVisitShop;
    private boolean wantsToRerollDice;
    private boolean wantsToMovePosition;
    private boolean hasWon;

    public RealPlayer(int id, String name) {
        super(id, name);
    }

    // Metodi per settare le scelte del giocatore (chiamati dal Controller)
    public void setWantsToVisitShop(boolean wantsToVisitShop) {
        this.wantsToVisitShop = wantsToVisitShop;
    }

    public void setWantsToRerollDice(boolean wantsToRerollDice) {
        this.wantsToRerollDice = wantsToRerollDice;
    }

    public void setWantsToMovePosition(boolean wantsToMovePosition) {
        this.wantsToMovePosition = wantsToMovePosition;
    }

    public void setHasWon(boolean hasWon) {
        this.hasWon = hasWon;
    }

    // Implementazioni dei metodi astratti del Player
    @Override
    public boolean wantsToVisitShop() {
        return this.wantsToVisitShop;
    }

    @Override
    public boolean wantsToRerollDice() {
        return this.wantsToRerollDice;
    }

    @Override
    public boolean wantsToMovePosition() {
        return this.wantsToMovePosition;
    }

    @Override
    public void playTurn() {
        // TODO logica del tunro del player reale 
    }

    @Override
    public boolean hasWon() {
        return this.hasWon;
    }

}

 