package Menus;

import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;

public class MainMenu extends JFrame {
    
	// barra del menu
	static JMenuBar mb;

	// JMenu
	static JMenu x;

	// scelte menu tendina
	static JMenuItem m1, m2, m3, m4, m5, m6;

	// Frame
	static JFrame f;

	public MainMenu()
	{
		// creazione del Frame
		f = new JFrame("Walk of War");

		// creazione barra menu
		mb = new JMenuBar();

		// creazione menu a tendina
		x = new JMenu("Seleziona una Modalità di Gioco: ");

		// opzioni delle modalita' 
		m1 = new JMenuItem("2 Player Mode : P1 vs P2");
		m2 = new JMenuItem("2 Player Mode : P1 vs CPU");
		m3 = new JMenuItem("4 Player Mode : P1 vs P2 vs P3 vs P4");
        m4 = new JMenuItem("4 Player Mode : P1 vs P2 vs P3 vs CPU");
        m5 = new JMenuItem("4 Player Mode : P1 vs P2 vs 2 CPUs");
        m6 = new JMenuItem("4 Player Mode : P1 vs 3 CPUs");

		// aggiunta opzioni alla tendina
		x.add(m1);
		x.add(m2);
		x.add(m3);
        x.add(m4);
		x.add(m5);
		x.add(m6);

		// aggiungere menu tendina alla barra
		mb.add(x);

		// aggiunta barra menu al Frame
		f.setJMenuBar(mb);

		// dimensione del Frame
		f.setSize(500, 500);
		f.setVisible(true);
	}
}
