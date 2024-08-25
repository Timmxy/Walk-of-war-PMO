package View;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Controller.MenuController;
import View.Menus.MainMenu;

public class AppWindow extends JFrame {

    //DICHIARAZIONE VARIABILI

    // Frame
	static JFrame f;

    // pannello corrente
    private JPanel currentPanel;

    //COSTRUTTORE
    public AppWindow(MenuController mc) {

        // creazione del Frame
		f = new JFrame("Walk of War");

		// dimensione del Frame
		f.setSize(900, 600);

        this.currentPanel = new MainMenu(mc).getPanel();
        this.currentPanel.setLocation(new Point(50, 50));
        this.currentPanel.setSize(400, 500);

        // aggiungo il MainMenu al frame
        f.getContentPane().add(currentPanel);
		

		f.setVisible(true);
    }
}
