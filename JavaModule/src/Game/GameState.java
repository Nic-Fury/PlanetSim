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

    public static GoldResources       getGoldInstance()       { return myGold; }
    public static WoodResources       getWoodInstance()       { return myWood; }
    public static PopulationResources getPopulationInstance() { return myPopulation; }
    public static BreadResources      getBreadInstance()      { return myBread; }
    public static StoneResources      getStoneInstance()      { return myStone; }
    public static WeedResources       getWeedInstance()       { return myWeed; }

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

    // ---------------------------------------------------------------
    // Construction helpers
    // ---------------------------------------------------------------
    public static boolean kannBauen(Buildings b) {
        return myGold.affordableUnits(b.goldKosten) >= 1
            && myWood.affordableUnits(b.holzKosten) >= 1;
    }

    public static void ressourcenAbziehen(Buildings b) {
        myGold.subResources(b.goldKosten);
        myWood.subResources(b.holzKosten);
    }

    // ---------------------------------------------------------------
    // Cell-occupancy tracking
    // ---------------------------------------------------------------
    public static boolean isCellOccupied(int x, int y) {
        if (currentMap == null || y < 0 || y >= currentMap.length
                || x < 0 || x >= currentMap[y].length) {
            return false;
        }
        return currentMap[y][x].equals("BUILDING");
    }

    public static void markCellAsOccupied(int x, int y) {
        if (currentMap != null && y >= 0 && y < currentMap.length
                && x >= 0 && x < currentMap[y].length) {
            currentMap[y][x] = "BUILDING";
        }
    }

}
