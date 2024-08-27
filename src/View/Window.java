package View;

import Controller.MenuController;
import View.Menus.Menu;
import javax.swing.JFrame;

public class Window extends JFrame {

    public Window(MenuController mc) {
        // Configurazione del JFrame
        this.setTitle("WOW");
        this.setSize(500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        // Aggiungere il pannello del menu
        this.add(new Menu(this, mc));

        // Rendere visibile la finestra
        this.setVisible(true);
    }
}
