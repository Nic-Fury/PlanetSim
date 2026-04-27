package Events;

import Buildings.Buildings;
import Buildings.FarmLand;
import Game.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PerfectYieldEventTest {

    private final List<Buildings> registeredForCleanup = new ArrayList<>();

    @BeforeEach
    void resetState() {
        GameState.getWeedInstance().subResources(GameState.getWeedInstance().getAmount());

        List<Buildings> snapshot = new ArrayList<>(GameState.getPlacedBuildings());
        for (Buildings building : snapshot) {
            GameState.removeBuilding(building);
        }
    }

    @Test
    void applyEventAddsNoBonusWhenNoFarmlandExists() {
        PerfectYieldEvent event = new PerfectYieldEvent();

        event.applyEvent();

        assertEquals(0, GameState.getWeedInstance().getAmount());
    }

    @Test
    void applyEventAddsHalfRoundedUpForOddProduction() {
        registerFarmlands(1);
        PerfectYieldEvent event = new PerfectYieldEvent();

        event.applyEvent();

        assertEquals(1, GameState.getWeedInstance().getAmount(),
                "1 produced weed should grant 1 bonus (rounded up)");
    }

    @Test
    void applyEventAddsHalfForEvenProduction() {
        registerFarmlands(2);
        PerfectYieldEvent event = new PerfectYieldEvent();

        event.applyEvent();

        assertEquals(1, GameState.getWeedInstance().getAmount(),
                "2 produced weed should grant exactly 1 bonus");
    }

    private void registerFarmlands(int amount) {
        for (int i = 0; i < amount; i++) {
            FarmLand farmLand = new FarmLand();
            GameState.registerBuilding(farmLand);
            registeredForCleanup.add(farmLand);
        }
    }

    @Test
    void stringASCIIArtReturnsNonNullString() {
        PerfectYieldEvent event = new PerfectYieldEvent();
        assertNotNull(event.stringASCIIArt());
        assertFalse(event.stringASCIIArt().isEmpty());
    }
}
