package Model;

public final class Pair<X, Y> {

    // DICHIARAZIONE VARIABILI
    private X x;
    private Y y;

    // COSTRUTTORE
    public Pair(final X x, final Y y) {
        super();
        this.x = x;
        this.y = y;
    }

    // METODI
    // ritorna il primo valore nella coppia
    public X getFirst(){
        return x;
    }

    // ritorna il secondo valore nella coppia
    public Y getSecond() {
        return y;
    }
    
    
    // imposta il primo valore
    public void setX(X x) {
        this.x = x;
    }

    // imposta il secondo valore
    public void setY(Y y) {
        this.y = y;
    }
    

    @Override
    public int hashCode() {
        return x.hashCode() ^ y.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof Pair ? x.equals(((Pair<?, ?>) obj).x) && y.equals(((Pair<?, ?>) obj).y) : false;
    }

    @Override
    public String toString() {
        return "<" + x + ", " + y + ">";
    }

}
