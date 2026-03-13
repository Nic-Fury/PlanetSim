// java
package Buildings;

import Game.GameState;
import Resources.Resources;

import java.util.Set;

public class Quarry extends IndustryBuildings {
    public static final int Stone_PRO_RUNDE = 5;

    public Quarry() {
        super("Quarry  ", "[Q]", 15, 20, 10);
    }

    @Override
    public Resources getProducedResource() { return GameState.getStoneInstance(); }

    @Override
    public int getProductionPerRound() { return Stone_PRO_RUNDE; }

    @Override
    public int getWorkforceRequired() { return 3; }

    @Override
    public Set<String> getAllowedBiomes() {
        return Set.of("GRAY");
    }
}
