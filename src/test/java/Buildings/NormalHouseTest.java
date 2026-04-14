package Buildings;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class NormalHouseTest {

    @Test
    void getPopulationPerRound() {
        NormalHouse house = new NormalHouse();
        assertEquals(1, house.getPopulationPerRound(), "NormalHouse should produce 1 population per round");
    }

    @Test
    void getPopulationConsumptionPerUnit() {
        NormalHouse house = new NormalHouse();
        assertEquals(1, house.getPopulationConsumptionPerUnit(), "NormalHouse should consume 1 bread per population unit");
    }

    @Test
    void getWorkforceRequired() {
        NormalHouse house = new NormalHouse();
        assertEquals(0, house.getWorkforceRequired(), "NormalHouse should require 0 workers");
    }

    @Test
    void getAllowedBiomes() {
        NormalHouse house = new NormalHouse();
        Set<String> biomes = house.getAllowedBiomes();

        assertTrue(biomes.contains("GREEN"), "GREEN should be allowed");
        assertTrue(biomes.contains("YELLOW"), "YELLOW should be allowed");
        assertEquals(2, biomes.size(), "Exactly 2 biomes should be allowed");
    }

    @Test
    void getMaxPopulationPerUnit() {
        NormalHouse house = new NormalHouse();
        assertEquals(2, house.getMaxPopulationPerUnit(), "NormalHouse should support max 2 population per unit");
    }
}

