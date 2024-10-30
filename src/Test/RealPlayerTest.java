package Test;

import static org.junit.jupiter.api.Assertions.*;
import Model.RealPlayer;
import Equipment.Equipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

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
    void testAddItemToInventory() {
        Equipment equipment = mock(Equipment.class);
        realPlayer.addItemToInventory(equipment);
        
        assertTrue(realPlayer.getInventory().contains(equipment), "L'inventario di RealPlayer dovrebbe contenere l'oggetto aggiunto");
    }

    @Test
    void testComputeStatsWithMultipleEquipments() {
        Equipment helmet = mock(Equipment.class);
        when(helmet.getValue()).thenReturn(2);
        
        realPlayer.computeStats(List.of(helmet));
        
        assertEquals(5, realPlayer.getMaxHp(), "RealPlayer dovrebbe avere HP aggiornati con l'aggiunta di un helmet");
    }
    
    @Test
    void testTakeDmg() {
        int initialHp = realPlayer.getCurrentHp();
        realPlayer.takeDmg();
        
        assertEquals(initialHp - 1, realPlayer.getCurrentHp(), "RealPlayer dovrebbe subire danno riducendo la salute di 1");
    }
}
