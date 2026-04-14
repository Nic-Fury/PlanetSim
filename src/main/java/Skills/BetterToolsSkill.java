package Skills;

public class BetterToolsSkill extends Skills {

    @Override
    public String getId() {
        return "better_tools";
    }

    @Override
    public String getDisplayName() {
        return "Better Tools";
    }

    @Override
    public int getMaxLevel() {
        return 10;
    }

    @Override
    public int getUpgradeCostForNextLevel() {
        int nextLevel = level + 1;
        if (nextLevel <= 2) return 30;
        int a = 30, b = 30;
        for (int i = 3; i <= nextLevel; i++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }

    public int getProductionBonusPerBuilding() {
        return level; // +1 je Level
    }
}
