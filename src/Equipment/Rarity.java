package Equipment;

public enum Rarity {

    COMMON(1), 
    RARE(2),
    EPIC(3),
    LEGENDARY(4);

    private final int value;

    Rarity(int val) {
        this.value = val;
    }

    public int getValue() {
        return this.value;
    }

}
