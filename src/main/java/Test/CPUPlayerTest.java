package Test;

import Model.CPUPlayer;
import Equipment.Equipment;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CPUPlayerTest {

    private CPUPlayer cpuPlayer;
    private Random mockRandom;

    @Test
    void testWantsToVisitShopHighMoney() {
        cpuPlayer.addMoney(5);
        when(mockRandom.nextInt(101)).thenReturn(69); // sotto 70 -> visita lo shop
        assertTrue(cpuPlayer.wantsToVisitShop());
    }

    @Test
    void testWantsToVisitShopLowMoney() {
        cpuPlayer.addMoney(4);
        when(mockRandom.nextBoolean()).thenReturn(false);
        assertFalse(cpuPlayer.wantsToVisitShop());
    }

    @Test
    void testWantsToBuySomethingWhenAffordable() {
        Equipment equipment = mock(Equipment.class);
        when(equipment.getCost()).thenReturn(3);
        cpuPlayer.addMoney(5);
        when(mockRandom.nextInt(3)).thenReturn(0);
        when(mockRandom.nextInt(1)).thenReturn(0);

        Optional<Equipment> result = cpuPlayer.wantsToBuySomething(List.of(equipment, equipment, equipment));
        assertTrue(result.isPresent());
        assertEquals(equipment, result.get());
    }

    @Test
    void testWantsToRerollDice() {
        cpuPlayer.addRerolls(1);
        assertTrue(cpuPlayer.wantsToRerollDice(3));
    }

    @Test
    void testWantsToMovePosition() {
        cpuPlayer.addPositionModifiers(1);
        when(mockRandom.nextInt(2)).thenReturn(1); // Ritorna 1
        assertEquals(0, cpuPlayer.wantsToMovePosition());
    }
}
