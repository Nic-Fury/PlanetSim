package Buildings;

import Game.GameState;
import Resources.Resources;

public class Lumberjack extends IndustryBuildings {
    public static final int HOLZ_PRO_RUNDE = 2;

    public Lumberjack() {
        super("Lumberjack  ", "DARK_GREEN", 4, 6);
    }

    @Override
    public Resources getProducedResource() { return GameState.getWoodInstance(); }

    @Override
    public int getProductionPerRound() { return HOLZ_PRO_RUNDE; }
}
