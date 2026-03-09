package Buildings;

import Game.GameState;
import Resources.Resources;

public class NormalHouse extends ResidentialBuildings {
    public NormalHouse() {
        super("House       ", "WHITE", 5, 3);
    }

    @Override
    public int getPopulationPerRound() { return 1; }

    @Override
    public Resources getPopulationConsumedResource() { return GameState.getBreadInstance(); }

    @Override
    public int getPopulationConsumptionPerUnit() { return 1; }
}
