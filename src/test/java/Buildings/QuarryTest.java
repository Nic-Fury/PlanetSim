package Buildings;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class QuarryTest {

    @Test
    void getProductionPerRound() {
        Quarry quarry = new Quarry();
        assertEquals(5, quarry.getProductionPerRound(), "Quarry should produce 5 resources per round");
    }

    @Test
    void getConsumptionPerUnit() {
        Quarry quarry = new Quarry();
        assertEquals(0, quarry.getConsumptionPerUnit(), "Quarry should not consume input resources");
    }

    @Test
    void getWorkforceRequired() {
        Quarry quarry = new Quarry();
        assertEquals(3, quarry.getWorkforceRequired(), "Quarry should require 3 workers");
    }

    @Test
    void getAllowedBiomes() {
        Quarry quarry = new Quarry();
        Set<String> biomes = quarry.getAllowedBiomes();

        assertTrue(biomes.contains("GRAY"), "GRAY should be allowed");
        assertEquals(1, biomes.size(), "Exactly 1 biome should be allowed");
    }
}

