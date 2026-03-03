package Buildings;

import Resources.Resources;

/**
 * Base class for all residential buildings.
 * Residential buildings produce no resource by default.
 */
public abstract class ResidentialBuildings extends Buildings {
    public ResidentialBuildings(String displayName, String color, int goldKosten, int holzKosten) {
        super(displayName, color, goldKosten, holzKosten);
    }

    @Override
    public Resources getProducedResource() { return null; }

    @Override
    public int getProductionPerRound() { return 0; }
}
