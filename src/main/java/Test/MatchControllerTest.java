package Test;

import Controller.BoardController;
import Controller.FightController;
import Controller.MatchController;
import Controller.PlayerController;
import Controller.ShopController;
import MatchInfo.GameMode;
import Model.*;
import View.MatchView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchControllerTest {
    
    @Mock
    private Match match;
    
    @Mock
    private PlayerController playerController;
    
    @Mock
    private BoardController boardController;
    
    @Mock
    private ShopController shopController;
    
    @Mock
    private FightController fightController;
    
    @Mock
    private MatchView matchView;

    @Mock
    private Stage stage;
    
    private List<Player> players;
    
    @InjectMocks
    private MatchController matchController;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Creazione di una lista di giocatori fittizi per i test
        players = new ArrayList<>();
        players.add(new Player(0, "Player0"));
        players.add(new CPUPlayer(1, "CPUPlayer1"));
        
        match = new Match(GameMode.P1_C1, stage);
        
        // Inizializzazione del MatchController
        matchController = new MatchController(match, players, new Board(), new Shop(), new Fight(), stage);
    }
    
    @Test
    void startGameShouldInitializeGame() {
        matchController.startGame();
        assertFalse(match.isGameOver(), "Il gioco non dovrebbe essere terminato all'inizio.");
        assertEquals(0, match.getCurrentPlayerIndex(), "Il primo turno dovrebbe iniziare con il primo giocatore.");
    }
    
    @Test
    void rollDiceShouldReturnValueBetweenOneAndSix() {
        int diceResult = matchController.rollDice();
        assertTrue(diceResult >= 1 && diceResult <= 6, "Il risultato del dado dovrebbe essere tra 1 e 6.");
    }
    
    @Test
    void endTurnButtonClickedShouldMoveToNextPlayer() {
        matchController.startGame();
        matchController.endTurnButtonClicked();
        assertEquals(1, match.getCurrentPlayerIndex(), "Il turno successivo dovrebbe passare al secondo giocatore.");
    }
    
    @Test
    void handleCPUTurnShouldCallExpectedMethods() {
        Player cpuPlayer = new CPUPlayer(1, "CPUPlayer1");
        when(match.getPlayers()).thenReturn(players);
        when(match.getCurrentPlayerIndex()).thenReturn(1); // CPU Player

        matchController.startGame();  // Imposta il gioco
        matchController.handleCPUTurn();
        
        verify(playerController).getDecisionOnShopVisit((CPUPlayer) cpuPlayer);
        verify(playerController).getDecisionOnShopPurchase((CPUPlayer) cpuPlayer, shopController.getAvailableEquipments());
        verify(playerController).getDecisionOnReroll((CPUPlayer) cpuPlayer, matchController.rollDice());
        verify(playerController).movePlayer(cpuPlayer, anyInt(), anyInt());
    }
    
    @Test
    void visitShopButtonClickedShouldCallShopVisit() {
        Player currentPlayer = players.get(0);
        matchController.visitShopButtonClicked();
        
        verify(shopController).visitShop(currentPlayer);
        verify(matchView).displayShopPanel();
    }
    
    @Test
    void fightEndedShouldPenalizeLosingPlayer() {
        Player losingPlayer = players.get(0);
        
        matchController.fightEnded(losingPlayer);
        
        verify(playerController).movePlayer(losingPlayer, -MatchController.MALUS_FIGHT, boardController.getNumberOfTiles());
        verify(matchView).movePlayerToTile(losingPlayer);
    }
}

