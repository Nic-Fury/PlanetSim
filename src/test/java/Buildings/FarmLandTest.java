package Buildings;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FarmLandTest {

    @Test
    void getProductionPerRound() {
        FarmLand farmLand = new FarmLand();
        assertEquals(1, farmLand.getProductionPerRound(), "FarmLand should produce 1 resource per round");
    }

    @Test
    void getConsumptionPerUnit() {
        FarmLand farmLand = new FarmLand();
        assertEquals(0, farmLand.getConsumptionPerUnit(), "FarmLand should not consume input resources");
    }

    @Test
    void getWorkforceRequired() {
        FarmLand farmLand = new FarmLand();
        assertEquals(1, farmLand.getWorkforceRequired(), "FarmLand should require 1 worker");
    }

    @Test
    void getAllowedBiomes() {
        FarmLand farmLand = new FarmLand();
        Set<String> biomes = farmLand.getAllowedBiomes();

        assertTrue(biomes.contains("GREEN"), "GREEN should be allowed");
        assertEquals(1, biomes.size(), "Exactly 1 biome should be allowed");
    }
}

