package Model;

import Board.TileVariant;

public class TileEffectEvent {
    private final Player player;
    private final TileVariant variant;
    private final int effectValue;

    public TileEffectEvent(Player player, TileVariant variant, int effectValue) {
        this.player = player;
        this.variant = variant;
        this.effectValue = effectValue;
    }

    public Player getPlayer() {
        return this.player;
    }

    public TileVariant getVariant() {
        return this.variant;
    }

    public int getEffectValue() {
        return this.effectValue;
    }
}
