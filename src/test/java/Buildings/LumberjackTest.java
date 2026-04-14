package Buildings;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LumberjackTest {

    @Test
    void getProductionPerRound() {
        Lumberjack lumberjack = new Lumberjack();
        assertEquals(2, lumberjack.getProductionPerRound(), "Lumberjack should produce 2 resources per round");
    }

    @Test
    void getConsumptionPerUnit() {
        Lumberjack lumberjack = new Lumberjack();
        assertEquals(0, lumberjack.getConsumptionPerUnit(), "Lumberjack should not consume input resources");
    }

    @Test
    void getWorkforceRequired() {
        Lumberjack lumberjack = new Lumberjack();
        assertEquals(2, lumberjack.getWorkforceRequired(), "Lumberjack should require 2 workers");
    }

    @Test
    void getAllowedBiomes() {
        Lumberjack lumberjack = new Lumberjack();
        Set<String> biomes = lumberjack.getAllowedBiomes();

        assertTrue(biomes.contains("GREEN"), "GREEN should be allowed");
        assertEquals(1, biomes.size(), "Exactly 1 biome should be allowed");
    }
}

