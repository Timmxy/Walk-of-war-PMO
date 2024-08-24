package Player;

public class Pawn {

    private int position;

    //metods
    public int getPosition(){
        return this.position;
    }

    public void move(int diceResult){
        this.position += diceResult;
    }
}
