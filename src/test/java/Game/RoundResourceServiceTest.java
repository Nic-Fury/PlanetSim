package Game;

import Buildings.Bakery;
import Buildings.FarmLand;
import Buildings.NormalHouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RoundResourceServiceTest {

    @BeforeEach
    void resetState() {
        GameState.setCurrentMap(null);
        GameState.resetChildhoodState();

        GameState.getGoldInstance().addResources(50 - GameState.getGoldInstance().getAmount());
        GameState.getWoodInstance().addResources(100 - GameState.getWoodInstance().getAmount());
        GameState.getStoneInstance().addResources(100 - GameState.getStoneInstance().getAmount());
        GameState.getBreadInstance().addResources(50 - GameState.getBreadInstance().getAmount());
        GameState.getWeedInstance().addResources(0 - GameState.getWeedInstance().getAmount());
        GameState.getPopulationInstance().addResources(1 - GameState.getPopulationInstance().getAmount());
        GameState.getWorkforceInstance().addResources(0 - GameState.getWorkforceInstance().getAmount());
    }

    @Test
    void updateResourcesForRoundProducesFromIndustrialBuildings() {
        FarmLand farm = new FarmLand();
        GameState.registerBuilding(farm);

        int beforeWeed = GameState.getWeedInstance().getAmount();
        Map<String, Integer> produced = RoundResourceService.updateResourcesForRound();

        assertTrue(produced.containsKey("Weed"));
        assertEquals(farm.getProductionPerRound(), produced.get("Weed"));
        assertEquals(beforeWeed + farm.getProductionPerRound(), GameState.getWeedInstance().getAmount());
    }

    @Test
    void residentialPopulationGrowthConsumesBreadAndProducesChildren() {
        NormalHouse house = new NormalHouse();
        GameState.registerBuilding(house);

        int beforePop = GameState.getPopulationInstance().getAmount();
        int beforeBread = GameState.getBreadInstance().getAmount();

        Map<String, Integer> produced = RoundResourceService.updateResourcesForRound();

        assertTrue(produced.isEmpty());
        assertEquals(beforePop + house.getPopulationPerRound(), GameState.getPopulationInstance().getAmount());
        assertEquals(beforeBread - house.getPopulationPerRound(), GameState.getBreadInstance().getAmount());
        assertEquals(house.getPopulationPerRound(), GameState.getChildrenBornToday());
    }

    @Test
    void buildingWithMissingConsumedResourceDoesNotProduce() {
        Bakery bakery = new Bakery();
        GameState.registerBuilding(bakery);

        GameState.getWeedInstance().addResources(0 - GameState.getWeedInstance().getAmount());
        int beforeBread = GameState.getBreadInstance().getAmount();

        Map<String, Integer> produced = RoundResourceService.updateResourcesForRound();

        assertFalse(produced.containsKey("Bread"));
        assertEquals(beforeBread, GameState.getBreadInstance().getAmount());
    }
}

