package Buildings;

import Game.GameState;
import Resources.Resources;

import java.util.Set;

public class Lumberjack extends IndustryBuildings {
    public static final int HOLZ_PRO_RUNDE = 2;

    public Lumberjack() {
        super("Lumberjack  ", "[L]", 4, 6, 0);
    }

    @Override
    public Resources getProducedResource() { return GameState.getWoodInstance(); }

    @Override
    public int getProductionPerRound() { return applyIndustrySkillBonus(HOLZ_PRO_RUNDE); }

    @Override
    public int getWorkforceRequired() { return 2; }

    @Override
    public Set<String> getAllowedBiomes() {
        return Set.of("GREEN");
    }
}
