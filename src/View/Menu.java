package View;
import Controller.MenuController;
import MatchInfo.GameMode;
import View.Menu;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class Menu extends JPanel {

    private JFrame parentFrame;
    private MenuController mc;

    public Menu(JFrame parentFrame, MenuController mc) {
        this.parentFrame = parentFrame;
        this.setLayout(new BorderLayout());
        this.mc = mc;

        // Titolo del gioco
        JLabel titleLabel = new JLabel("WALK OF WAR", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        this.add(titleLabel, BorderLayout.NORTH);

        // Pannello centrale con i pulsanti
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Pulsante Play
        JButton playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.PLAIN, 20));
        playButton.addActionListener((ActionEvent e) -> {
            openPlayMenu();
        });

        // Pulsante Leaderboard
        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setFont(new Font("Arial", Font.PLAIN, 20));
        leaderboardButton.addActionListener((ActionEvent e) -> {
            openLeaderboard();
        });

        // Pulsante Exit
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        // Aggiungi pulsanti al pannello centrale
        buttonPanel.add(playButton);
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(exitButton);

        // Aggiungi il pannello centrale al centro del BorderLayout
        this.add(buttonPanel, BorderLayout.CENTER);
    }

    private void openPlayMenu() {
        // Crea un nuovo pannello per la selezione della modalità di gioco
        JPanel playMenu = new JPanel();
        playMenu.setLayout(new BorderLayout());

        JLabel modeLabel = new JLabel("Scegli la modalità di gioco", SwingConstants.CENTER);
        modeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        playMenu.add(modeLabel, BorderLayout.NORTH);

        // Pannello centrale con le opzioni
        JPanel optionsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Pulsante per la modalità 2 Players
        JButton twoPlayersButton = new JButton("2 Players");
        twoPlayersButton.setFont(new Font("Arial", Font.PLAIN, 20));
        twoPlayersButton.addActionListener((ActionEvent e) -> {
            openTwoPlayersMenu();
        });

        // Pulsante per la modalità 4 Players
        JButton fourPlayersButton = new JButton("4 Players");
        fourPlayersButton.setFont(new Font("Arial", Font.PLAIN, 20));
        fourPlayersButton.addActionListener((ActionEvent e) -> {
            openFourPlayersMenu();
        });

        optionsPanel.add(twoPlayersButton);
        optionsPanel.add(fourPlayersButton);

        playMenu.add(optionsPanel, BorderLayout.CENTER);

        // Aggiungi il pulsante Indietro
        JButton backButton = new JButton("Indietro");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.addActionListener((ActionEvent e) -> {
            goBackToMainMenu();
        });

        playMenu.add(backButton, BorderLayout.SOUTH);

        // Sostituisce il pannello attuale con il nuovo
        parentFrame.getContentPane().removeAll();
        parentFrame.add(playMenu);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void openTwoPlayersMenu() {
        // Crea un nuovo pannello per la modalità 2 Players
        JPanel twoPlayersMenu = new JPanel(new BorderLayout());

        JLabel twoPlayersLabel = new JLabel("Scegli una modalità 2 Players", SwingConstants.CENTER);
        twoPlayersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        twoPlayersMenu.add(twoPlayersLabel, BorderLayout.NORTH);

        // Pannello centrale con le opzioni
        JPanel optionsPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Pulsante per Player 1 vs Player 2
        JButton p1VsP2Button = new JButton("Player 1 vs Player 2");
        p1VsP2Button.setFont(new Font("Arial", Font.PLAIN, 20));
        p1VsP2Button.addActionListener((ActionEvent e) -> {
            startGame("Player 1 vs Player 2");
            mc.initializeMatch(GameMode.P2);
        });

        // Pulsante per Player 1 vs CPU
        JButton p1VsCpuButton = new JButton("Player 1 vs CPU");
        p1VsCpuButton.setFont(new Font("Arial", Font.PLAIN, 20));
        p1VsCpuButton.addActionListener((ActionEvent e) -> {
            startGame("Player 1 vs CPU");
            mc.initializeMatch(GameMode.P1_C1);
        });

        optionsPanel.add(p1VsP2Button);
        optionsPanel.add(p1VsCpuButton);

        twoPlayersMenu.add(optionsPanel, BorderLayout.CENTER);

        // Aggiungi il pulsante Indietro
        JButton backButton = new JButton("Indietro");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.addActionListener((ActionEvent e) -> {
            openPlayMenu();
        });

        twoPlayersMenu.add(backButton, BorderLayout.SOUTH);

        // Sostituisce il pannello attuale con il nuovo
        parentFrame.getContentPane().removeAll();
        parentFrame.add(twoPlayersMenu);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void openFourPlayersMenu() {
        // Crea un nuovo pannello per la modalità 4 Players
        JPanel fourPlayersMenu = new JPanel(new BorderLayout());

        JLabel fourPlayersLabel = new JLabel("Scegli una modalità 4 Players", SwingConstants.CENTER);
        fourPlayersLabel.setFont(new Font("Arial", Font.BOLD, 24));
        fourPlayersMenu.add(fourPlayersLabel, BorderLayout.NORTH);

        // Pannello centrale con le opzioni
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Pulsante per Player 1 vs Player 2 vs Player 3 vs Player 4
        JButton p1VsP2VsP3VsP4Button = new JButton("Player 1 vs Player 2 vs Player 3 vs Player 4");
        p1VsP2VsP3VsP4Button.setFont(new Font("Arial", Font.PLAIN, 18));
        p1VsP2VsP3VsP4Button.addActionListener((ActionEvent e) -> {
            startGame("Player 1 vs Player 2 vs Player 3 vs Player 4");
            mc.initializeMatch(GameMode.P4);
        });

        // Pulsante per Player 1 vs Player 2 vs Player 3 vs CPU
        JButton p1VsP2VsP3VsCpuButton = new JButton("Player 1 vs Player 2 vs Player 3 vs CPU");
        p1VsP2VsP3VsCpuButton.setFont(new Font("Arial", Font.PLAIN, 18));
        p1VsP2VsP3VsCpuButton.addActionListener((ActionEvent e) -> {
            startGame("Player 1 vs Player 2 vs Player 3 vs CPU");
            mc.initializeMatch(GameMode.P3_C1);
        });

        // Pulsante per Player 1 vs Player 2 vs 2 CPU
        JButton p1VsP2Vs2CpuButton = new JButton("Player 1 vs Player 2 vs 2 CPU");
        p1VsP2Vs2CpuButton.setFont(new Font("Arial", Font.PLAIN, 18));
        p1VsP2Vs2CpuButton.addActionListener((ActionEvent e) -> {
            startGame("Player 1 vs Player 2 vs 2 CPU");
            mc.initializeMatch(GameMode.P2_C2);
        });

        // Pulsante per Player 1 vs 3 CPU
        JButton p1Vs3CpuButton = new JButton("Player 1 vs 3 CPU");
        p1Vs3CpuButton.setFont(new Font("Arial", Font.PLAIN, 18));
        p1Vs3CpuButton.addActionListener((ActionEvent e) -> {
            startGame("Player 1 vs 3 CPU");
            mc.initializeMatch(GameMode.P1_C3);
        });

        optionsPanel.add(p1VsP2VsP3VsP4Button);
        optionsPanel.add(p1VsP2VsP3VsCpuButton);
        optionsPanel.add(p1VsP2Vs2CpuButton);
        optionsPanel.add(p1Vs3CpuButton);

        fourPlayersMenu.add(optionsPanel, BorderLayout.CENTER);

        // Aggiungi il pulsante Indietro
        JButton backButton = new JButton("Indietro");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.addActionListener((ActionEvent e) -> {
            openPlayMenu();
        });

        fourPlayersMenu.add(backButton, BorderLayout.SOUTH);

        // Sostituisce il pannello attuale con il nuovo
        parentFrame.getContentPane().removeAll();
        parentFrame.add(fourPlayersMenu);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void openLeaderboard() {
        // Crea un nuovo pannello per mostrare la classifica locale
        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        leaderboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel leaderboardLabel = new JLabel("Classifica Locale", SwingConstants.CENTER);
        leaderboardLabel.setFont(new Font("Arial", Font.BOLD, 24));
        leaderboardPanel.add(leaderboardLabel, BorderLayout.NORTH);

        JTextArea leaderboardArea = new JTextArea("Classifica:\n1. Player 1 - 1000 punti\n2. Player 2 - 800 punti\n...");
        leaderboardArea.setFont(new Font("Arial", Font.PLAIN, 18));
        leaderboardArea.setEditable(false);

        leaderboardPanel.add(new JScrollPane(leaderboardArea), BorderLayout.CENTER);

        // Aggiungi il pulsante Indietro
        JButton backButton = new JButton("Indietro");
        backButton.setFont(new Font("Arial", Font.PLAIN, 20));
        backButton.addActionListener((ActionEvent e) -> {
            goBackToMainMenu();
        });

        leaderboardPanel.add(backButton, BorderLayout.SOUTH);

        // Sostituisce il pannello attuale con il nuovo
        parentFrame.getContentPane().removeAll();
        parentFrame.add(leaderboardPanel);
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void goBackToMainMenu() {
        // Torna al menu principale
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new Menu(parentFrame, this.mc));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
    
    private void startGame(String mode) {
        // Logica per avviare il gioco con la modalità selezionata
        JOptionPane.showMessageDialog(parentFrame, "Inizio del gioco in modalità: " + mode);
        // Qui puoi implementare la logica per avviare il gioco vero e proprio
    }
}