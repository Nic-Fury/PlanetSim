package Game;

import Resources.*;
import Buildings.Buildings;

import java.util.ArrayList;
import java.util.List;

/**********************************************************************
 *           Diese Klasse ist dazu da, um den Spielzustand
 *           zu verwalten und globale Instanzen von Ressourcen
 *           bereitzustellen.
 *
 ***********************************************************************/

public class GameState {
    private static final GoldResources       myGold       = new GoldResources();
    private static final WoodResources       myWood       = new WoodResources();
    private static final PopulationResources myPopulation = new PopulationResources();
    private static final BreadResources      myBread      = new BreadResources();
    private static final StoneResources      myStone      = new StoneResources();
    private static final WeedResources       myWeed       = new WeedResources();
    private static final WorkforceResources myWorkforce = new WorkforceResources();

    // Population produced on the current day and previous day is not workforce yet.
    private static int childrenAge0Days = 0;
    private static int childrenAge1Day = 0;


    public static GoldResources       getGoldInstance()       { return myGold; }
    public static WoodResources       getWoodInstance()       { return myWood; }
    public static PopulationResources getPopulationInstance() { return myPopulation; }
    public static BreadResources      getBreadInstance()      { return myBread; }
    public static StoneResources      getStoneInstance()      { return myStone; }
    public static WeedResources       getWeedInstance()       { return myWeed; }
    public static WorkforceResources getWorkforceInstance() { return myWorkforce; }

    public static void resetChildhoodState() {
        childrenAge0Days = 0;
        childrenAge1Day = 0;
    }

    public static void advanceChildhoodDay() {
        // Children from yesterday age by one day; two-day-olds become workforce-eligible.
        childrenAge1Day = childrenAge0Days;
        childrenAge0Days = 0;
    }

    public static void addChildrenProducedThisRound(int amount) {
        if (amount <= 0) return;
        childrenAge0Days += amount;
    }

    public static int getChildrenNotInWorkforce() {
        return childrenAge0Days + childrenAge1Day;
    }

    public static int getMaturePopulation() {
        return Math.max(0, getPopulationInstance().getAmount() - getChildrenNotInWorkforce());
    }

    public static int getAvailableWorkforce() {
        return getMaturePopulation() - getTotalWorkforceUsed();
    }

    // ---------------------------------------------------------------
    // Map
    // ---------------------------------------------------------------
    private static String[][] currentMap = null;
    public static String[][]  getCurrentMap()              { return currentMap; }
    public static void        setCurrentMap(String[][] map){ currentMap = map; }
    public static boolean     hasMap()                     { return currentMap != null; }

    // ---------------------------------------------------------------
    // Building registry  (replaces all per-type counters)
    // ---------------------------------------------------------------

    /** All buildings that have been placed on the map. */
    private static final List<Buildings> placedBuildings = new ArrayList<>();

    /**
     * Registers a newly placed building. Called once from BuildHandler
     * after the building has been drawn on the map.
     */
    public static void registerBuilding(Buildings building) {
        placedBuildings.add(building);
    }

    /** Returns a read-only view of all placed buildings. */
    public static List<Buildings> getPlacedBuildings() {
        return java.util.Collections.unmodifiableList(placedBuildings);
    }

    /**
     * Removes a building from the registry, frees its cell and resets the
     * map tile back to the original biome (falls vorhanden) oder auf GREEN.
     */
    public static void removeBuilding(Buildings building) {
        placedBuildings.remove(building);
        if (building.x >= 0 && building.y >= 0) {
            occupiedCells.remove(building.x + "," + building.y);
            if (currentMap != null
                    && building.y < currentMap.length
                    && building.x < currentMap[building.y].length) {
                String tile = currentMap[building.y][building.x];
                if (tile != null && tile.contains("|")) {
                    // restore original biome part before the '|'
                    currentMap[building.y][building.x] = tile.split("\\|", 2)[0];
                } else {
                    currentMap[building.y][building.x] = "GREEN";
                }
            }
        }
    }

    /**
     * Demolish a building: refund half (floor) of each cost and remove it.
     */
    public static void demolishBuilding(Buildings building) {
        if (building == null) return;
        int refundGold  = Math.max(0, building.goldKosten / 2);
        int refundWood  = Math.max(0, building.holzKosten / 2);
        int refundStone = Math.max(0, building.steinKosten / 2);

        getGoldInstance().addResources(refundGold);
        getWoodInstance().addResources(refundWood);
        getStoneInstance().addResources(refundStone);

        IO.println("Demolished " + building.displayName.trim()
                + " -> refund G:" + refundGold + " W:" + refundWood + " S:" + refundStone);

        removeBuilding(building);
    }

    // ---------------------------------------------------------------
    // Construction helpers
    // ---------------------------------------------------------------
    public static boolean kannBauen(Buildings b) {
        return myGold.affordableUnits(b.goldKosten) >= 1
                && myWood.affordableUnits(b.holzKosten) >= 1
                && myStone.affordableUnits(b.steinKosten) >= 1;
    }

    public static void ressourcenAbziehen(Buildings b) {
        myGold.subResources(b.goldKosten);
        myWood.subResources(b.holzKosten);
        myStone.subResources(b.steinKosten);
    }

    // ---------------------------------------------------------------
    // Cell-occupancy tracking  (separate from the color stored in the map)
    // ---------------------------------------------------------------

    /** Stores "x,y" strings for every cell that already has a building. */
    private static final java.util.Set<String> occupiedCells = new java.util.HashSet<>();

    public static boolean isCellOccupied(int x, int y) {
        return occupiedCells.contains(x + "," + y);
    }

    public static void markCellAsOccupied(int x, int y) {
        occupiedCells.add(x + "," + y);
    }

    public static boolean hatGenugArbeitskraft(Buildings b) {
        if (b.getWorkforceRequired() <= 0) return true;
        int verfuegbar = getAvailableWorkforce();
        return verfuegbar >= b.getWorkforceRequired();
    }

    private static int getTotalWorkforceUsed() {
        return getPlacedBuildings().stream()
                .mapToInt(Buildings::getWorkforceRequired)
                .sum();
    }

}
