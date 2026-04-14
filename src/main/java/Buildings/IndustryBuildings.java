package Buildings;

import Game.GameState;
import Skills.BetterToolsSkill;
import Skills.Skills;

public abstract class IndustryBuildings extends Buildings {
    public IndustryBuildings(String displayName, String buildingSymbolColor, int goldKosten, int holzKosten, int steinKosten) {
        super(displayName, buildingSymbolColor, goldKosten, holzKosten, steinKosten);
    }

    protected int applyIndustrySkillBonus(int baseProduction) {
        Skills skill = GameState.getSkillById("better_tools");
        if (skill instanceof BetterToolsSkill betterToolsSkill) {
            return baseProduction + betterToolsSkill.getProductionBonusPerBuilding();
        }
        return baseProduction;
    }
}
