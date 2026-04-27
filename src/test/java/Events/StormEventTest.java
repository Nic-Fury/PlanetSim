package Events;

import Buildings.Buildings;
import Buildings.FarmLand;
import Game.GameState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StormEventTest {

    @BeforeEach
    void resetState() {
        GameState.getWeedInstance().subResources(GameState.getWeedInstance().getAmount());

        List<Buildings> snapshot = new ArrayList<>(GameState.getPlacedBuildings());
        for (Buildings building : snapshot) {
            GameState.removeBuilding(building);
        }
    }

    @Test
    void applyEventNeverIncreasesWeed() {
        GameState.getWeedInstance().addResources(100);
        StormEvent stormEvent = new StormEvent();

        stormEvent.applyEvent();

        assertTrue(GameState.getWeedInstance().getAmount() <= 100,
                "Storm must not increase weed resources");
    }

    @Test
    void applyEventNeverCreatesNewBuildings() {
        int beforeBuildings = GameState.getPlacedBuildings().size();
        StormEvent stormEvent = new StormEvent();

        stormEvent.applyEvent();

        assertTrue(GameState.getPlacedBuildings().size() <= beforeBuildings,
                "Storm should not create buildings");
    }

    @Test
    void applyEventCanOnlyReduceFarmlandCountOrLeaveItUnchanged() {
        FarmLand farmLandA = new FarmLand();
        FarmLand farmLandB = new FarmLand();
        GameState.registerBuilding(farmLandA);
        GameState.registerBuilding(farmLandB);
        int before = (int) GameState.getPlacedBuildings().stream().filter(b -> b instanceof FarmLand).count();

        new StormEvent().applyEvent();

        int after = (int) GameState.getPlacedBuildings().stream().filter(b -> b instanceof FarmLand).count();
        assertTrue(after <= before, "Storm should never increase FarmLand count");
    }

    @Test
    void stringASCIIArtReturnsNonNullString() {
        StormEvent stormEvent = new StormEvent();
        assertNotNull(stormEvent.stringASCIIArt());
        assertFalse(stormEvent.stringASCIIArt().isEmpty());
    }
}
