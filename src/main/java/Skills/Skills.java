package Skills;

public abstract class Skills {
    protected int level = 0;

    public abstract String getId();
    public abstract String getDisplayName();

    // Default-MaxLevel, falls ein Skill noch keine eigene Logik implementiert.
    public int getMaxLevel() {
        return 10;
    }

    // Default-Kosten, falls ein Skill noch keine eigene Kostenlogik implementiert.
    public int getUpgradeCostForNextLevel() {
        return 30;
    }

    public int getLevel() {
        return level;
    }

    public boolean isMaxLevel() {
        return level >= getMaxLevel();
    }

    public void upgrade() {
        if (!isMaxLevel()) {
            level++;
        }
    }
}
