package Game;

import Buildings.Buildings;
import Buildings.NormalHouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BuildHandlerTest {

    // --- MANUELLES MOCK OBJEKT ---
    // Dieses Objekt ist völlig isoliert von Änderungen in "NormalHouse" etc.
    // Wir können dem Mock exakt vorschreiben, was er zurückgeben soll.
    private static class MockBuilding extends NormalHouse {
        private final Set<String> mockAllowedBiomes;

        public MockBuilding(Set<String> allowedBiomes) {
            super(); // Ruft parameterlosen Konstruktor von NormalHouse auf
            this.mockAllowedBiomes = allowedBiomes;
        }

        @Override
        public Set<String> getAllowedBiomes() {
            return mockAllowedBiomes;
        }

        @Override
        public int getWorkforceRequired() {
            return 0; // Für Tests relevant
        }
    }

    @BeforeEach
    void setUp() {
        GameState.setCurrentMap(null);
        // Clear occupied cells that might have been statically preserved between tests
        Buildings dummy = new MockBuilding(Set.of("GREEN"));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                dummy.x = i;
                dummy.y = j;
                GameState.removeBuilding(dummy);
            }
        }
    }

    @Test
    void checkBuildFieldAllowedReturnsFalseWhenNoMapExists() {
        Buildings building = new MockBuilding(Set.of("GREEN"));
        assertFalse(BuildHandler.checkBuildFieldAllowed(building, 0, 0, true));
    }

    @Test
    void checkBuildFieldAllowedRejectsOutOfBoundsAndBlancTile() {
        GameState.setCurrentMap(new String[][]{{"GREEN", "BLANC"}});
        Buildings building = new MockBuilding(Set.of("GREEN"));

        assertFalse(BuildHandler.checkBuildFieldAllowed(building, 2, 0, true));
        assertFalse(BuildHandler.checkBuildFieldAllowed(building, 1, 0, true));
    }

    @Test
    void checkBuildFieldAllowedRejectsBiomeMismatchAndOccupiedCell() {
        GameState.setCurrentMap(new String[][]{{"GREEN", "BLUE"}});
        // Mock darf nur auf GREEN bauen
        Buildings building = new MockBuilding(Set.of("GREEN"));

        assertFalse(BuildHandler.checkBuildFieldAllowed(building, 1, 0, true));

        GameState.markCellAsOccupied(0, 0);
        assertFalse(BuildHandler.checkBuildFieldAllowed(building, 0, 0, true));
    }

    @Test
    void checkBuildFieldAllowedAcceptsValidCell() {
        GameState.setCurrentMap(new String[][]{{"GREEN"}});
        // Mock darf auf GREEN bauen
        Buildings building = new MockBuilding(Set.of("GREEN"));

        assertTrue(BuildHandler.checkBuildFieldAllowed(building, 0, 0, true));
    }

    @Test
    public void testCheckBuildFieldAllowed() {
        // Mock darf auf "GREEN" und "BLUE" bauen
        Buildings mockHouse = new MockBuilding(Set.of("GREEN", "BLUE"));
        assertFalse(BuildHandler.checkBuildFieldAllowed(mockHouse, 0, 0, true), "Should fail if no map");

        String[][] map = new String[][]{{"GREEN", "BLUE"}};
        GameState.setCurrentMap(map);

        assertTrue(BuildHandler.checkBuildFieldAllowed(mockHouse, 0, 0, true)); // Auf GREEN
        assertTrue(BuildHandler.checkBuildFieldAllowed(mockHouse, 1, 0, true)); // Auf BLUE (da im Mock explizit erlaubt)
    }
}
