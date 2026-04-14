package Buildings;

import Game.GameState;
import Resources.Resources;

import java.util.Set;

public class NormalHouse extends ResidentialBuildings {
    private static final int MAX_POPULATION_PER_UNIT = 2;

    public NormalHouse() {
        super("House       ", "[H]", 5, 3,2);
    }

    @Override
    public int getPopulationPerRound() {
        return 1; }

    @Override
    public Resources getPopulationConsumedResource() { return GameState.getBreadInstance(); }

    @Override
    public int getPopulationConsumptionPerUnit() { return 1; }

    @Override
    public Set<String> getAllowedBiomes() {
        return Set.of("GREEN", "YELLOW");
    }

    @Override
    public int getMaxPopulationPerUnit() {
        return MAX_POPULATION_PER_UNIT;
    }

}
