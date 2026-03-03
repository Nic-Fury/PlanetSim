package Buildings;

import Game.GameState;
import Resources.Resources;

public class FarmLand extends IndustryBuildings {
    public static final int WEED_PRO_RUNDE = 1;

    public FarmLand() {
        super("FarmLand    ", "YELLOW", 3, 5);
    }

    @Override
    public Resources getProducedResource() { return GameState.getWeedInstance(); }

    @Override
    public int getProductionPerRound() { return WEED_PRO_RUNDE; }
}
