package Buildings;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BakeryTest {

    @Test
    void getProductionPerRound() {
        Bakery bakery = new Bakery();
        int produktion = bakery.getProductionPerRound();
        assertEquals(1, produktion, "Die Bäckerei sollte 1 Ressource pro Runde produzieren");
    }

    @Test
    void getConsumptionPerUnit() {
        Bakery bakery = new Bakery();
        assertEquals(2, bakery.getConsumptionPerUnit(), "Die Bäckerei sollte 2 Einheiten (Weed) verbrauchen");
    }

    @Test
    void getWorkforceRequired() {
        Bakery bakery = new Bakery();
        assertEquals(2, bakery.getWorkforceRequired(), "Die Bäckerei benötigt 2 Arbeiter");
    }

    @Test
    void getAllowedBiomes() {
        Bakery bakery = new Bakery();
        Set<String> biomes = bakery.getAllowedBiomes();

        assertTrue(biomes.contains("GREEN"), "GREEN sollte erlaubt sein");
        assertTrue(biomes.contains("YELLOW"), "YELLOW sollte erlaubt sein");
        assertEquals(2, biomes.size(), "Es sollten genau 2 Biome erlaubt sein");
    }
}

