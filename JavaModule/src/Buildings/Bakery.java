package Buildings;

import Game.GameState;
import Resources.Resources;

public class Bakery extends IndustryBuildings {
    public static final int BREAD_PRO_RUNDE    = 1;
    public static final int WEED_PRO_BREAD     = 2;  // consumed Weed per produced Bread

    public Bakery() {
        super("Bakery    ", "[B]", 10, 5);
    }

    @Override
    public Resources getProducedResource()  { return GameState.getBreadInstance(); }

    @Override
    public int getProductionPerRound()      { return BREAD_PRO_RUNDE; }

    @Override
    public Resources getConsumedResource()  { return GameState.getWeedInstance(); }

    @Override
    public int getConsumptionPerUnit()      { return WEED_PRO_BREAD; }
}
