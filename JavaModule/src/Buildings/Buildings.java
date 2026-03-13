package Buildings;

import Game.IO;
import Resources.Resources;
import java.util.Set;

/**
 * Base class for all buildings.
 *
 * Following the Information Expert principle, each building knows:
 *  - its display name and map color
 *  - its construction costs
 *  - which resource it produces each round and how much  (getProducedResource / getProductionPerRound)
 *  - which resource it consumes per produced unit and how much  (getConsumedResource / getConsumptionPerUnit)
 *
 * Adding a new building only requires creating a subclass – no other class
 * needs to be changed for production or consumption logic.
 */
public abstract class Buildings {
    public final String displayName;
    public final String color;
    public final int goldKosten;
    public final int holzKosten;

    /** Map coordinates – set by BuildHandler after placement. */
    public int x = -1;
    public int y = -1;

    public Buildings(String displayName, String color, int goldKosten, int holzKosten) {
        this.displayName  = displayName;
        this.color        = color;
        this.goldKosten   = goldKosten;
        this.holzKosten   = holzKosten;
    }

    // ------------------------------------------------------------------
    // Production
    // ------------------------------------------------------------------

    /**
     * Returns the resource instance this building produces each round,
     * or {@code null} if the building produces nothing.
     */
    public abstract Resources getProducedResource();

    /**
     * Returns the amount of resource this building produces per round.
     * Only relevant when {@link #getProducedResource()} is non-null.
     */
    public abstract int getProductionPerRound();

    // ------------------------------------------------------------------
    // Consumption
    // ------------------------------------------------------------------

    /**
     * Returns the resource this building consumes per produced unit,
     * or {@codenull} if the building consumes nothing.
     *
     * Example: a Bakery consumes Weed to produce Bread.
     */
    public Resources getConsumedResource() { return null; }

    /**
     * Returns how many units of {@link #getConsumedResource()} are consumed
     * for every single unit produced.
     * Only relevant when {@link #getConsumedResource()} is non-null.
     */
    public int getConsumptionPerUnit() { return 0; }

    /**
     * Returns the resource consumed per population unit generated.
     * Override in ResidentialBuildings that require food.
     */
    public Resources getPopulationConsumedResource() { return null; }

    /**
     * Returns how many units of getPopulationConsumedResource() are consumed
     * per population unit generated.
     */
    public int getPopulationConsumptionPerUnit() { return 0; }

    /**
     * Returns allowed biome names (e.g. "GREEN", "BLUE", "YELLOW", "GRAY").
     * If this returns null the building is allowed on any non-BLANC tile.
     */
    public Set<String> getAllowedBiomes() { return null; }

    // ------------------------------------------------------------------
    // Round tick
    // ------------------------------------------------------------------

    /**
     * Executes one round of production and consumption.
     *
     * If a consumed resource is defined, actual production is capped by
     * how many units can be afforded:
     *   affordableUnits = floor(availableConsumed / consumptionPerUnit)
     *
     * Returns the number of units actually produced (for logging in Round).
     */
    public int produceResources() {
        Resources produced = getProducedResource();
        if (produced == null || getProductionPerRound() <= 0) return 0;

        int units = getProductionPerRound();

        Resources consumed = getConsumedResource();
        if (consumed != null && getConsumptionPerUnit() > 0) {
            int affordable = consumed.affordableUnits(getConsumptionPerUnit());
            units = Math.min(units, affordable);
            if (units <= 0) return 0;
            consumed.subResources(units * getConsumptionPerUnit());
        }

        produced.addResources(units);
        return units;
    }

    /**
     * Returns the workforce this building requires to operate.
     * Override in subclasses that need workforce (e.g. IndustryBuildings).
     */
    public int getWorkforceRequired() { return 0; }

    /**
     * Returns how much population this building generates per round.
     * Override in ResidentialBuildings.
     */
    public int getPopulationPerRound() { return 0; }


    public void printInfo() {
        IO.println("| " + displayName
                + " | Gold: " + goldKosten
                + " | Holz: " + holzKosten + "  |");
    }


}
