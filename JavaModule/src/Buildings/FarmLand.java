package Buildings;

import Game.GameState;
import Resources.Resources;

import java.util.Set;

public class FarmLand extends IndustryBuildings {
    public static final int WEED_PRO_RUNDE = 1;

    public FarmLand() {
        super("FarmLand    ", "[#]", 3, 5,0);
    }

    @Override
    public Resources getProducedResource() { return GameState.getWeedInstance(); }

    @Override
    public int getProductionPerRound() { return WEED_PRO_RUNDE; }

    @Override
    public int getWorkforceRequired() { return 1; }

    @Override
    public Set<String> getAllowedBiomes() {
        return Set.of("GREEN");
    }
}
