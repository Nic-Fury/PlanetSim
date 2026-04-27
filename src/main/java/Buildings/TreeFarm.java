package Buildings;

import Game.GameState;
import Resources.Resources;

import java.util.Set;

public class TreeFarm extends IndustryBuildings {
    public static final int HOLZ_PRO_RUNDE = 5;

    public TreeFarm() {
        super("TreeFarm  ", "[T]", 15, 10,5);
    }

    @Override
    public Resources getProducedResource() { return GameState.getWoodInstance(); }

    @Override
    public int getProductionPerRound() { return applyIndustrySkillBonus(HOLZ_PRO_RUNDE); }

    @Override
    public int getWorkforceRequired() { return 3; }

    @Override
    public Set<String> getAllowedBiomes() {
        return Set.of("GREEN");
    }
}
