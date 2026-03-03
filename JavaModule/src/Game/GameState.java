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

}
