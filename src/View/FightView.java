package View;

import Model.FightActions;
import Model.Player;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Scanner;
import java.util.function.Consumer;

import javax.swing.Action;

import Controller.FightController;

public class FightView extends Pane {

    private static final int PANE_WIDTH = 1050; // larghezza pannello
    private static final int PANE_HEIGTH = 450; // altezza pannello

    private Consumer<String> onActionSelected;

    private FightController controller;

    private Text fightHeader;
    private Text statsPlayer1;
    private Text statsPlayer2;

    public FightView(FightController fc) {
        this.controller = fc;
        this.setOnKeyPressed(this::handleKeyInput);
        // imposta la larghezza e altezza del Pane
        this.setPrefSize(FightView.PANE_WIDTH, FightView.PANE_HEIGTH);
        this.setMaxSize(1050, 520);

        this.fightHeader = new Text("FIGHT!");
        this.statsPlayer1 = new Text("");
        this.statsPlayer2 = new Text("");

        this.fightHeader.setTranslateX(FightView.PANE_WIDTH / 2);
        this.fightHeader.setTranslateY(FightView.PANE_HEIGTH / 4);

        this.fightHeader.setFill(Color.WHITE);

        this.getChildren().add(fightHeader);
        this.getChildren().add(statsPlayer1);
        this.getChildren().add(statsPlayer2);
    }

    public void refreshStatsTexts(Player p1, Player p2) {
        this.statsPlayer1.setText(p1.toString()+"'s Stats:"+
                                                "\nHealth Points: "+p1.getCurrentHp()+
                                                "\nStamina: "+p1.getCurrentAtks()+
                                                "\nShields: "+p1.getcurrentShields());

        this.statsPlayer2.setText(p2.toString()+"'s Stats:"+
                                                "\nHealth Points: "+p2.getCurrentHp()+
                                                "\nStamina: "+p2.getCurrentAtks()+
                                                "\nShields: "+p2.getcurrentShields());
    }

    public void displayCombatStatus(String status) {
        // Mostra lo stato attuale del combattimento (in console o GUI)
        System.out.println(status);
    }

    public void setFocusOnThis() {
        this.setFocusTraversable(true); // Rende FightView "focusable"
        this.requestFocus(); // Richiede il focus su FightView per catturare i key events
    }

    private void handleKeyInput(KeyEvent event) {
        this.controller.interpretUserInput(event.getCode());
    }

    // public void receiveInput(Consumer<String> onActionSelected) {
    //     this.onActionSelected = onActionSelected;

    //     // Configura l'ascolto di eventi di tasti
    //     this.setOnKeyPressed(event -> {
    //         switch (event.getCode()) {
    //             case A:
    //                 onActionSelected.accept("ATTACCO");
    //                 break;
    //             case D:
    //                 onActionSelected.accept("DIFESA");
    //                 break;
    //             case R:
    //                 onActionSelected.accept("RICARICA");
    //                 break;
    //             default:
    //                 // Ignora tasti non validi
    //                 break;
    //         }
    //     });
    // }

    // public void ascoltaAzione() {
    //     // Imposta il focus sul FightView per catturare gli eventi della tastiera
    //     this.setFocusTraversable(true);
    //     this.requestFocus();
    // }
}

