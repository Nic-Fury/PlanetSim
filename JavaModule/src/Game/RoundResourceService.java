package Game;

import Buildings.Buildings;
import Buildings.ResidentialBuildings;
import Resources.Resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Encapsulates all per-day resource and population production logic.
 * The Round class stays focused on turn flow and delegates production here.
 */
public final class RoundResourceService {

    private RoundResourceService() {
    }

    /**
     * Runs one full production pass for all placed buildings.
     * It first handles residential population growth, then generic building output,
     * and finally synchronizes workforce in GameState.
     */
    public static Map<String, Integer> updateResourcesForRound() {
        Map<String, Integer> producedThisRound = new HashMap<>();

        for (Buildings building : GameState.getPlacedBuildings()) {
            if (shouldSkipResourceProduction(building)) {
                continue;
            }
            processResourceProduction(building, producedThisRound);
        }

        GameState.synchronizeWorkforce();
        return producedThisRound;
    }

    /**
     * Decides whether generic resource production should be skipped for this building.
     * Residential buildings are processed by dedicated population logic first.
     */
    private static boolean shouldSkipResourceProduction(Buildings building) {
        if (!(building instanceof ResidentialBuildings residentialBuilding)) {
            return false;
        }
        return !processResidentialPopulation(residentialBuilding);
    }

    /**
     * Produces population for one residential building while respecting
     * housing capacity and optional food cost per produced unit.
     */
    private static boolean processResidentialPopulation(ResidentialBuildings residentialBuilding) {
        if (residentialBuilding.getPopulationPerRound() <= 0) {
            return true;
        }

        int populationToProduce = Math.min(
                residentialBuilding.getPopulationPerRound(),
                residentialBuilding.getRemainingPopulationCapacity()
        );

        if (populationToProduce <= 0) {
            return false;
        }

        Resources popFood = residentialBuilding.getPopulationConsumedResource();
        int popCost = residentialBuilding.getPopulationConsumptionPerUnit();

        if (popFood != null && popCost > 0) {
            int affordablePopulation = popFood.affordableUnits(popCost);
            populationToProduce = Math.min(populationToProduce, affordablePopulation);

            if (populationToProduce <= 0) {
                IO.println(residentialBuilding.displayName.trim()
                        + " could not generate Population - not enough "
                        + popFood.getResourceTypeName() + "!");
                return true;
            }

            popFood.subResources(populationToProduce * popCost);
        }

        GameState.getPopulationInstance().addResources(populationToProduce);
        GameState.addChildrenProducedThisRound(populationToProduce);
        residentialBuilding.registerPopulationProduced(populationToProduce);
        return true;
    }

    /**
     * Triggers generic building production and aggregates successful output
     * by resource type for round-end reporting.
     */
    private static void processResourceProduction(Buildings building, Map<String, Integer> producedThisRound) {
        if (building.getProducedResource() == null || building.getProductionPerRound() <= 0) {
            return;
        }

        int produced = building.produceResources();
        if (produced > 0) {
            String resourceName = building.getProducedResource().getResourceTypeName();
            producedThisRound.merge(resourceName, produced, Integer::sum);
            return;
        }

        IO.println(building.displayName.trim() + " could not produce "
                + building.getProducedResource().getResourceTypeName()
                + " - not enough "
                + (building.getConsumedResource() != null
                ? building.getConsumedResource().getResourceTypeName()
                : "resources") + "!");
    }
}


