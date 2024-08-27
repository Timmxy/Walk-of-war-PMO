/*package View;

public class prova {
    package View;
import Controller.MenuController;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

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

		
        // Imposta la dimensione della finestra
        setSize(300, 400);

        // Imposta l'operazione di chiusura della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crea un pannello principale con layout verticale
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Crea un'etichetta per il titolo
        JLabel titleLabel = new JLabel("WALK OF WAR");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Crea i pulsanti
        JButton playButton = new JButton("Play");
        JButton exitButton = new JButton("Exit");
        JButton leaderboardButton = new JButton("Leaderboard");

        // Allinea i pulsanti al centro
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Aggiungi un listener per il pulsante Exit
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
                });

        // Aggiungi un listener per il pulsante Play
        playButton.addActionListener((ActionEvent e) -> {
            // Qui puoi inserire il codice per avviare il gioco
            System.out.println("Play button clicked");
                });

        // Aggiungi un listener per il pulsante Leaderboard
        leaderboardButton.addActionListener((ActionEvent e) -> {
            // Qui puoi inserire il codice per visualizzare la classifica
            System.out.println("Leaderboard button clicked");
                });

        // Aggiungi i componenti al pannello principale
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Spazio sopra il titolo
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50))); // Spazio tra il titolo e i pulsanti
        mainPanel.add(playButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spazio tra i pulsanti
        mainPanel.add(leaderboardButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spazio tra i pulsanti
        mainPanel.add(exitButton);

        // Aggiungi il pannello principale alla finestra
        add(mainPanel);

        // Rendi la finestra visibile
        setVisible(true);
    }
}
*/