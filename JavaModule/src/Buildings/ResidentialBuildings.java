package Buildings;

import Resources.Resources;

/**
 * Base class for all residential buildings.
 * Residential buildings produce no resource by default.
 */
public abstract class ResidentialBuildings extends Buildings {
    public ResidentialBuildings(String displayName, String buildingSymbolColor, int goldKosten, int holzKosten, int steinKosten) {
        super(displayName, buildingSymbolColor, goldKosten, holzKosten, steinKosten);
    }

    @Override
    public Resources getProducedResource() { return null; }

    @Override
    public int getProductionPerRound() { return 0; }
}
