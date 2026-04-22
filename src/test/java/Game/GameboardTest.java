package Game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameboardTest {

    @BeforeEach
    public void setup() {
        GameState.setCurrentMap(null);
    }

    @Test
    public void testPrintPlanetInitializesMap() {
        assertFalse(GameState.hasMap());
        // We can't easily capture IO in Phase A without Refactoring, but we can verify map size logic indirectly
        // or just test that chooseMap sets the string array in GameState.
        Gameboard.printPlanet(1);
        assertTrue(GameState.hasMap());
        assertNotNull(GameState.getCurrentMap());
        // Map size 1 is 10x10
        assertEquals(10, GameState.getCurrentMap().length);
    }
}

