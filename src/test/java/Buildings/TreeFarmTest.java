package Buildings;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TreeFarmTest {

    @Test
    void getProductionPerRound() {
        TreeFarm treeFarm = new TreeFarm();
        assertEquals(5, treeFarm.getProductionPerRound(), "TreeFarm should produce 5 resources per round");
    }

    @Test
    void getConsumptionPerUnit() {
        TreeFarm treeFarm = new TreeFarm();
        assertEquals(0, treeFarm.getConsumptionPerUnit(), "TreeFarm should not consume input resources");
    }

    @Test
    void getWorkforceRequired() {
        TreeFarm treeFarm = new TreeFarm();
        assertEquals(3, treeFarm.getWorkforceRequired(), "TreeFarm should require 3 workers");
    }

    @Test
    void getAllowedBiomes() {
        TreeFarm treeFarm = new TreeFarm();
        Set<String> biomes = treeFarm.getAllowedBiomes();

        assertTrue(biomes.contains("GREEN"), "GREEN should be allowed");
        assertEquals(1, biomes.size(), "Exactly 1 biome should be allowed");
    }
}

