package Buildings;

import Game.GameState;
import Resources.Resources;

public class Quarry extends IndustryBuildings {
    public static final int Stone_PRO_RUNDE = 5;

    public Quarry() {
        super("Quarry  ", "[Q]", 15, 20);
    }

    @Override
    public Resources getProducedResource() { return GameState.getStoneInstance(); }

    @Override
    public int getProductionPerRound() { return Stone_PRO_RUNDE; }

    @Override
    public int getWorkforceRequired() { return 3; }
}
