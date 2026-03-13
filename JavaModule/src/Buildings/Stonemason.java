package Buildings;

import Game.GameState;
import Resources.Resources;

import java.util.Set;

public class Stonemason extends IndustryBuildings {
    public static final int Stone_PRO_RUNDE = 2;

    public Stonemason() {
        super("Stonemason  ", "[S]", 4, 6);
    }

    @Override
    public Resources getProducedResource() { return GameState.getStoneInstance(); }

    @Override
    public int getProductionPerRound() { return Stone_PRO_RUNDE; }

    @Override
    public int getWorkforceRequired() { return 2; }

    @Override
    public Set<String> getAllowedBiomes() {
        return Set.of("GRAY");
    }
}

