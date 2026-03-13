package Buildings;

import Game.GameState;
import Resources.Resources;

import java.util.Set;

public class NormalHouse extends ResidentialBuildings {
    public NormalHouse() {
        super("House       ", "WHITE", 5, 3,2);
    }

    @Override
    public int getPopulationPerRound() { return 1; }

    @Override
    public Resources getPopulationConsumedResource() { return GameState.getBreadInstance(); }

    @Override
    public int getPopulationConsumptionPerUnit() { return 1; }

    @Override
    public Set<String> getAllowedBiomes() {
        return Set.of("GREEN", "YELLOW");
    }
}
