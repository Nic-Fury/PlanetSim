package Game;

import Buildings.Buildings;
import Buildings.NormalHouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    private static class MockSkill extends Skills.Skills {
        private final String id;
        private final String displayName;

        public MockSkill(String id, String displayName) {
            this.id = id;
            this.displayName = displayName;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public String getDisplayName() {
            return displayName;
        }
    }

    @BeforeEach
    void resetState() {
        // Aufräumen aller statischen Gebäude, die von anderen Tests platziert wurden
        java.util.List<Buildings> placed = new java.util.ArrayList<>(GameState.getPlacedBuildings());
        for (Buildings b : placed) {
            GameState.removeBuilding(b);
        }

        GameState.setCurrentMap(null);
        GameState.setCurrentPlanetName("Unknown");
        GameState.setCurrentDay(1);
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
    void setCurrentPlanetNameWithBlankSetsUnknown() {
        GameState.setCurrentPlanetName("   ");
        assertEquals("Unknown", GameState.getCurrentPlanetName());
    }

    @Test
    void childhoodDayAdvancementMovesChildrenToWorkforceAge() {
        GameState.addChildrenProducedThisRound(3);
        assertEquals(3, GameState.getChildrenBornToday());

        GameState.advanceChildhoodDay();
        assertEquals(0, GameState.getChildrenBornToday());
        assertEquals(0, GameState.getChildrenReachedWorkingAgeToday());

        GameState.addChildrenProducedThisRound(2);
        GameState.advanceChildhoodDay();
        assertEquals(3, GameState.getChildrenReachedWorkingAgeToday());
    }

    @Test
    void synchronizeWorkforceUsesMaturePopulationMinusRequiredWorkforce() {
        GameState.getPopulationInstance().addResources(10 - GameState.getPopulationInstance().getAmount());
        GameState.resetChildhoodState();

        GameState.addChildrenProducedThisRound(2);
        GameState.advanceChildhoodDay();
        GameState.addChildrenProducedThisRound(1);

        GameState.synchronizeWorkforce();
        assertEquals(7, GameState.getWorkforceInstance().getAmount());
    }

    @Test
    void demolishBuildingRefundsHalfAndClearsCell() {
        NormalHouse house = new NormalHouse();
        house.x = 1;
        house.y = 1;

        GameState.setCurrentMap(new String[][]{
                {"GREEN", "GREEN", "GREEN"},
                {"GREEN", "GREEN|[H]", "GREEN"},
                {"GREEN", "GREEN", "GREEN"}
        });
        GameState.markCellAsOccupied(1, 1);
        GameState.registerBuilding(house);

        int beforeGold = GameState.getGoldInstance().getAmount();
        int beforeWood = GameState.getWoodInstance().getAmount();
        int beforeStone = GameState.getStoneInstance().getAmount();

        GameState.demolishBuilding(house);

        assertFalse(GameState.getPlacedBuildings().contains(house));
        assertFalse(GameState.isCellOccupied(1, 1));
        assertEquals("GREEN", GameState.getCurrentMap()[1][1]);
        assertEquals(beforeGold + (house.goldKosten / 2), GameState.getGoldInstance().getAmount());
        assertEquals(beforeWood + (house.holzKosten / 2), GameState.getWoodInstance().getAmount());
        assertEquals(beforeStone + (house.steinKosten / 2), GameState.getStoneInstance().getAmount());
    }

    @Test
    void customSkillCanBeRegisteredAndRetrieved() {
        MockSkill testSkill = new MockSkill("test_skill_1", "Test Skill");
        GameState.registerSkill(testSkill);

        assertTrue(GameState.getRegisteredSkills().containsKey("test_skill_1"), "Skill should be in the registered skills map");
        assertEquals(testSkill, GameState.getSkillById("test_skill_1"), "Retrieving by ID should return the exact MockSkill instance");
    }

    @Test
    void skillUpgradeLimitPerDayIsEnforcedCorrectly() {
        // Mock a current day manually
        GameState.setCurrentDay(5);

        // Before any upgrade taking place, it should be false
        assertFalse(GameState.hasUpgradedSkillThisDay(), "Should be false initially");

        GameState.markSkillUpgradeForCurrentDay();

        assertTrue(GameState.hasUpgradedSkillThisDay(), "Should be true after marking an upgrade for today");

        // Next Day
        GameState.setCurrentDay(6);
        assertFalse(GameState.hasUpgradedSkillThisDay(), "Should reset to false for a new day");
    }
}
