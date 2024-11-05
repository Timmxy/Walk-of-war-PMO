package Test;

import static org.junit.jupiter.api.Assertions.*;
import Model.RealPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RealPlayerTest {

    private RealPlayer realPlayer;
    
    @BeforeEach
    void setUp() {
        realPlayer = new RealPlayer(2, "Real_Player_Test");
    }
    
    @Test
    void testAddMoney() {
        realPlayer.addMoney(5);
        assertEquals(5, realPlayer.getMoney(), "RealPlayer dovrebbe avere 5 monete dopo aggiunta");
    }
    
    @Test
    void testTakeDmg() {
        int initialHp = realPlayer.getCurrentHp();
        realPlayer.takeDmg();
        
        assertEquals(initialHp - 1, realPlayer.getCurrentHp(), "RealPlayer dovrebbe subire danno riducendo la salute di 1");
    }
}
