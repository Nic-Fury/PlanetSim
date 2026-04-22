package Skills;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkillsTest {
    private Skills testSkill;

    @BeforeEach
    void setUp() {
        // Verwende BetterToolsSkill als Implementierung zum Testen der abstrakten Klasse
        testSkill = new BetterToolsSkill();
    }

    @Test
    void testInitialLevel() {
        assertEquals(0, testSkill.getLevel());
    }

    @Test
    void testGetMaxLevel() {
        assertEquals(10, testSkill.getMaxLevel());
    }

    @Test
    void testGetUpgradeCostForNextLevel() {
        assertEquals(30, testSkill.getUpgradeCostForNextLevel());
    }

    @Test
    void testIsMaxLevel() {
        assertFalse(testSkill.isMaxLevel());
        // Upgrade bis zum MaxLevel
        for (int i = 0; i < 10; i++) {
            testSkill.upgrade();
        }
        assertTrue(testSkill.isMaxLevel());
    }

    @Test
    void testUpgrade() {
        assertEquals(0, testSkill.getLevel());
        testSkill.upgrade();
        assertEquals(1, testSkill.getLevel());
        testSkill.upgrade();
        assertEquals(2, testSkill.getLevel());
    }

    @Test
    void testUpgradeAtMaxLevel() {
        // Upgrade bis zum MaxLevel
        for (int i = 0; i < 10; i++) {
            testSkill.upgrade();
        }
        assertTrue(testSkill.isMaxLevel());
        assertEquals(10, testSkill.getLevel());

        // Weiteres Upgrade sollte nicht funktionieren
        testSkill.upgrade();
        assertEquals(10, testSkill.getLevel());
    }

    @Test
    void testGetId() {
        assertEquals("better_tools", testSkill.getId());
    }

    @Test
    void testGetDisplayName() {
        assertEquals("Better Tools", testSkill.getDisplayName());
    }
}

