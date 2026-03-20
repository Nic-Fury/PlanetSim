package Buildings;

import Resources.Resources;

/**
 * Base class for all residential buildings.
 * Residential buildings produce no resource by default.
 */
public abstract class ResidentialBuildings extends Buildings {
    private int producedPopulationTotal = 0;

    public ResidentialBuildings(String displayName, String buildingSymbolColor, int goldKosten, int holzKosten, int steinKosten) {
        super(displayName, buildingSymbolColor, goldKosten, holzKosten, steinKosten);
    }

    @Override
    public Resources getProducedResource() { return null; }

    @Override
    public int getProductionPerRound() { return 0; }

    public Resources getPopulationConsumedResource() { return null; }
    public int getPopulationConsumptionPerUnit() { return 0; }
    public int getPopulationPerRound() { return 0; }
    public int getMaxPopulationPerUnit() { return Integer.MAX_VALUE; }

    public int getRemainingPopulationCapacity() {
        int maxPopulation = getMaxPopulationPerUnit();
        if (maxPopulation == Integer.MAX_VALUE) return Integer.MAX_VALUE;
        return Math.max(0, maxPopulation - producedPopulationTotal);
    }

    public void registerPopulationProduced(int amount) {
        if (amount <= 0) return;
        if (getMaxPopulationPerUnit() == Integer.MAX_VALUE) return;
        producedPopulationTotal += amount;
    }
}
