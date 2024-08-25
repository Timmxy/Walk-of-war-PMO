package View.Menus;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Controller.MenuController;
import MatchInfo.GameMode;

import java.awt.*;
import java.awt.event.*;

public class MainMenu {

	//DICHIARAZIONE VARIABILI

	private static final int BUTTON_WIDTH = 300;
	private static final int BUTTON_HEIGHT = 25;

	MenuController mc;

	// textbox scelta
	static JLabel txt;

	// scelte GameMode
	static JButton b1, b2, b3, b4, b5, b6;

	// pannello
	JPanel menuPanel;
	
	//COSTRUTTORE
	public MainMenu(MenuController menuController)
	{
		this.mc = menuController;
		

		this.menuPanel = new JPanel();

		this.menuPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.menuPanel.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// impostazione del pannello
		this.menuPanel.setBackground(Color.GRAY);
		
		// creazione e impostazione scritta 
		txt = new JLabel("Seleziona una Modalità di Gioco: ");
		txt.setLocation(new Point(50, 25));
		this.menuPanel.add(txt);
		
		// creazione bottoni per scelta GameMode
		b1 = new JButton("2 Player Mode : P1 vs P2");
		b2 = new JButton("2 Player Mode : P1 vs CPU");
		b3 = new JButton("4 Player Mode : P1 vs P2 vs P3 vs P4");
        b4 = new JButton("4 Player Mode : P1 vs P2 vs P3 vs CPU");
        b5 = new JButton("4 Player Mode : P1 vs P2 vs 2 CPUs");
        b6 = new JButton("4 Player Mode : P1 vs 3 CPUs");
		
		// posizione dei bottoni all'interno del pannello
		b1.setLocation(new Point(50, 50));
		b2.setLocation(new Point(50, 100));
		b3.setLocation(new Point(50, 150));
		b4.setLocation(new Point(50, 200));
		b5.setLocation(new Point(50, 250));
		b6.setLocation(new Point(50, 300));

		
		// dimensione dei bottoni
		b1.setSize(MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		b2.setSize(MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		b3.setSize(MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		b4.setSize(MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		b5.setSize(MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		b6.setSize(MainMenu.BUTTON_WIDTH, MainMenu.BUTTON_HEIGHT);
		
		// aggiunta logica ai bottoni
		b1.addActionListener(e -> mc.initializeMatch(GameMode.P2));
		b2.addActionListener(e -> mc.initializeMatch(GameMode.P1_C1));
		b3.addActionListener(e -> mc.initializeMatch(GameMode.P4));
		b4.addActionListener(e -> mc.initializeMatch(GameMode.P3_C1));
		b5.addActionListener(e -> mc.initializeMatch(GameMode.P2_C2));
		b6.addActionListener(e -> mc.initializeMatch(GameMode.P1_C3));
		
		// aggiunta bottoni al pannello
		this.menuPanel.add(b1);
		this.menuPanel.add(b2);
		this.menuPanel.add(b3);
		this.menuPanel.add(b4);
		this.menuPanel.add(b5);
		this.menuPanel.add(b6);
	}

	public JPanel getPanel(){
		return this.menuPanel;
	}

}
