package Skills;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BetterToolsSkillTest {
    private BetterToolsSkill skill;

    @BeforeEach
    void setUp() {
        skill = new BetterToolsSkill();
    }

    @Test
    void testGetId() {
        assertEquals("better_tools", skill.getId());
    }

    @Test
    void testGetDisplayName() {
        assertEquals("Better Tools", skill.getDisplayName());
    }

    @Test
    void testGetMaxLevel() {
        assertEquals(10, skill.getMaxLevel());
    }

    @Test
    void testGetProductionBonusPerBuilding() {
        assertEquals(0, skill.getProductionBonusPerBuilding());
        skill.upgrade();
        assertEquals(1, skill.getProductionBonusPerBuilding());
        skill.upgrade();
        assertEquals(2, skill.getProductionBonusPerBuilding());
        skill.upgrade();
        assertEquals(3, skill.getProductionBonusPerBuilding());
    }

    @Test
    void testGetProductionBonusPerBuildingAtMaxLevel() {
        for (int i = 0; i < 10; i++) {
            skill.upgrade();
        }
        assertEquals(10, skill.getProductionBonusPerBuilding());
    }

    @Test
    void testGetUpgradeCostForNextLevel() {
        // Level 0 -> 1: 30
        assertEquals(30, skill.getUpgradeCostForNextLevel());
        skill.upgrade();

        // Level 1 -> 2: 30
        assertEquals(30, skill.getUpgradeCostForNextLevel());
        skill.upgrade();

        // Level 2 -> 3: Fibonacci sequence starts
        // a=30, b=30, c=60
        assertEquals(60, skill.getUpgradeCostForNextLevel());
        skill.upgrade();

        // Level 3 -> 4: a=30, b=60, c=90
        assertEquals(90, skill.getUpgradeCostForNextLevel());
        skill.upgrade();

        // Level 4 -> 5: a=60, b=90, c=150
        assertEquals(150, skill.getUpgradeCostForNextLevel());
        skill.upgrade();

        // Level 5 -> 6: a=90, b=150, c=240
        assertEquals(240, skill.getUpgradeCostForNextLevel());
        skill.upgrade();

        // Level 6 -> 7: a=150, b=240, c=390
        assertEquals(390, skill.getUpgradeCostForNextLevel());
        skill.upgrade();

        // Level 7 -> 8: a=240, b=390, c=630
        assertEquals(630, skill.getUpgradeCostForNextLevel());
        skill.upgrade();

        // Level 8 -> 9: a=390, b=630, c=1020
        assertEquals(1020, skill.getUpgradeCostForNextLevel());
        skill.upgrade();

        // Level 9 -> 10: a=630, b=1020, c=1650
        assertEquals(1650, skill.getUpgradeCostForNextLevel());
        skill.upgrade();

        // Level 10 (MaxLevel): Should still return cost for level 11
        // a=1020, b=1650, c=2670
        assertEquals(2670, skill.getUpgradeCostForNextLevel());
    }

    @Test
    void testUpgradeAndLevel() {
        assertEquals(0, skill.getLevel());
        skill.upgrade();
        assertEquals(1, skill.getLevel());
        skill.upgrade();
        assertEquals(2, skill.getLevel());
    }

    @Test
    void testUpgradeToMaxLevel() {
        for (int i = 0; i < 10; i++) {
            skill.upgrade();
        }
        assertTrue(skill.isMaxLevel());
        assertEquals(10, skill.getLevel());
    }

    @Test
    void testCannotUpgradeBeyondMaxLevel() {
        for (int i = 0; i < 10; i++) {
            skill.upgrade();
        }
        assertTrue(skill.isMaxLevel());
        skill.upgrade();
        assertEquals(10, skill.getLevel());
    }
}

