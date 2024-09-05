package View;

import javax.swing.*;

import Board.TileVariant;

import java.awt.*;
import Model.Player;
import Model.Tile;

class TilePanel extends JPanel {
    private Tile tile;
    private JLabel label;

    public TilePanel(Tile tile) {
        this.tile = tile;
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setPreferredSize(new Dimension(50, 50)); // Dimensione della casella

        label = new JLabel();
        add(label);

        updateTileView(); // Aggiorna la vista in base allo stato iniziale della casella
    }

    // Metodo per aggiornare la view in base alla variante della casella
    public void updateTileView() {
        TileVariant variant = tile.getVariant();
        switch (variant) {
            case BONUS_MONEY:
                setBackground(Color.GREEN);
                label.setText("$");
                break;
            case MALUS_MONEY:
                setBackground(Color.RED);
                label.setText("-");
                break;
            case BONUS_POSITION:
                setBackground(Color.BLUE);
                label.setText(">>");
                break;
            case MALUS_POSITION:
                setBackground(Color.ORANGE);
                label.setText("<<");
                break;
            case STEAL:
                setBackground(Color.YELLOW);
                label.setText("S");
                break;
            default:
                setBackground(Color.WHITE);
                label.setText("");
                break;
        }
    }

    // Metodo per impostare un giocatore sulla casella
    public void setPlayer(Player player) {
        label.setText(player.getName().substring(0, 1)); // Mostra l'iniziale del nome del giocatore
    }

}

