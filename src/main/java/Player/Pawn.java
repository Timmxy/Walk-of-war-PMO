package Player;

public class Pawn {

    private int position;

    public Pawn(int p) {
        this.position = p;
    }

    // METODI
    public int getPosition(){
        return this.position;
    }

    public void newPosition(int p) {
        this.position = p;
    }
}
