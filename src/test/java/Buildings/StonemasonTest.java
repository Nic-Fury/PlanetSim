package Buildings;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StonemasonTest {

    @Test
    void getProductionPerRound() {
        Stonemason stonemason = new Stonemason();
        assertEquals(2, stonemason.getProductionPerRound(), "Stonemason should produce 2 resources per round");
    }

    @Test
    void getConsumptionPerUnit() {
        Stonemason stonemason = new Stonemason();
        assertEquals(0, stonemason.getConsumptionPerUnit(), "Stonemason should not consume input resources");
    }

    @Test
    void getWorkforceRequired() {
        Stonemason stonemason = new Stonemason();
        assertEquals(2, stonemason.getWorkforceRequired(), "Stonemason should require 2 workers");
    }

    @Test
    void getAllowedBiomes() {
        Stonemason stonemason = new Stonemason();
        Set<String> biomes = stonemason.getAllowedBiomes();

        assertTrue(biomes.contains("GRAY"), "GRAY should be allowed");
        assertEquals(1, biomes.size(), "Exactly 1 biome should be allowed");
    }
}

