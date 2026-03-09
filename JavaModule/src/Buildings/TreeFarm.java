package Buildings;

import Game.GameState;
import Resources.Resources;

public class TreeFarm extends IndustryBuildings {
    public static final int HOLZ_PRO_RUNDE = 5;

    public TreeFarm() {
        super("TreeFarm  ", "[T]", 15, 10);
    }

    @Override
    public Resources getProducedResource() { return GameState.getWoodInstance(); }

    @Override
    public int getProductionPerRound() { return HOLZ_PRO_RUNDE; }

    @Override
    public int getWorkforceRequired() { return 3; }
}
