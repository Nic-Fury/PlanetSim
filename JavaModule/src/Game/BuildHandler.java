package Game;

import Buildings.Bakery;
import Buildings.FarmLand;
import Buildings.Lumberjack;
import Buildings.TreeFarm;
import Buildings.NormalHouse;
import Buildings.Stonemason;
import Buildings.Quarry;
import java.util.Set;

public class BuildHandler {

    protected static final NormalHouse haus       = new NormalHouse();
    protected static final FarmLand    farm       = new FarmLand();
    protected static final Lumberjack  lumberjack = new Lumberjack();
    protected static final Bakery      bakery     = new Bakery();
    protected static final Stonemason  stonemason = new Stonemason();
    protected static final Quarry      quarry     = new Quarry();
    protected static final TreeFarm    treeFarm   = new TreeFarm();

    protected static void executeBuildingAction(int buildchoice) {
        switch (buildchoice) {
            case 1 -> bauenStarten(haus);
            case 2 -> bauenStarten(farm);
            case 3 -> bauenStarten(bakery);
            case 4 -> bauenStarten(lumberjack);
            case 5 -> bauenStarten(treeFarm);
            case 6 -> bauenStarten(stonemason);
            case 7 -> bauenStarten(quarry);

            case 0 -> IO.println("Bauen abgebrochen.");
            default -> IO.println("Ungültige Eingabe.");
        }
    }

    protected static void bauenStarten(Buildings.Buildings template) {
        if (!checkForNeededResources(template)) return;
        if (!checkForNeededWorkforce(template)) return;

        int[] coordinates = readCoordinates();
        if (coordinates == null) return;

        int x = coordinates[0];
        int y = coordinates[1];

        if (!checkBuildFieldAllowed(template, x, y)) return;

        buildAtCoordinates(template, x, y);
    }

    private static boolean checkForNeededResources(Buildings.Buildings template) {
        if (!GameState.kannBauen(template)) {
            IO.println("Nicht genug Ressourcen für " + template.displayName + "!");
            return false;
        }
        return true;
    }

    private static boolean checkForNeededWorkforce(Buildings.Buildings template) {
        if (!GameState.hatGenugArbeitskraft(template)) {
            IO.println("Nicht genug Arbeitskraft für " + template.displayName + "!");
            IO.println("Verfügbar: " + getAvailableWorkforce() + " | Benötigt: " + template.getWorkforceRequired());
            return false;
        }
        return true;
    }

    private static int getAvailableWorkforce() {
        return GameState.getPopulationInstance().getAmount()
                - GameState.getPlacedBuildings().stream().mapToInt(Buildings.Buildings::getWorkforceRequired).sum();
    }

    private static int[] readCoordinates() {
        String[][] map = GameState.getCurrentMap();
        int maxX = map[0].length - 1;
        int maxY = map.length - 1;

        IO.println("Koordinaten eingeben (X: 0-" + maxX + ", Y: 0-" + maxY + ")");

        try {
            int x = Integer.parseInt(IO.readln("X-Koordinate: ").trim());
            int y = Integer.parseInt(IO.readln("Y-Koordinate: ").trim());
            return new int[]{x, y};
        } catch (NumberFormatException e) {
            IO.println("Ungültige Koordinaten.");
            return null;
        }
    }

    private static boolean checkBuildFieldAllowed(Buildings.Buildings template, int x, int y) {
        String[][] map = GameState.getCurrentMap();

        if (!isCoordinateInMap(map, x, y)) {
            IO.println("Koordinaten außerhalb der Karte!");
            return false;
        }

        if (isBlancTile(map, x, y)) {
            IO.println("Auf diesem Feld kannst du nicht bauen!");
            return false;
        }

        if (!isAllowedBiome(template, map[y][x])) {
            Set<String> allowed = template.getAllowedBiomes();
            IO.println("Auf diesem Feld kannst du kein " + template.displayName.trim() + " bauen (Biome: " + map[y][x] + "). Erlaubte Biome: " + allowed);
            return false;
        }

        if (GameState.isCellOccupied(x, y)) {
            IO.println("Hier steht bereits ein Gebäude!");
            return false;
        }

        return true;
    }

    private static boolean isCoordinateInMap(String[][] map, int x, int y) {
        return y >= 0 && y < map.length && x >= 0 && x < map[y].length;
    }

    private static boolean isBlancTile(String[][] map, int x, int y) {
        return "BLANC".equals(map[y][x]);
    }

    private static boolean isAllowedBiome(Buildings.Buildings template, String tile) {
        Set<String> allowed = template.getAllowedBiomes();
        return allowed == null || allowed.contains(tile);
    }

    private static void buildAtCoordinates(Buildings.Buildings template, int x, int y) {
        Buildings.Buildings building = createNewInstance(template);

        GameState.ressourcenAbziehen(building);
        building.x = x;
        building.y = y;
        Gameboard.printSingleColorBlockAtCoordinates(building.color, x, y);
        GameState.registerBuilding(building);
        GameState.markCellAsOccupied(x, y);

        IO.println(building.displayName + " erfolgreich gebaut bei (" + x + ", " + y + ")!");
        ActionMenu.printResources();
    }

    /**
     * Creates a new instance of the same building type so that each placed
     * building is a unique object with its own x/y coordinates.
     */
    private static Buildings.Buildings createNewInstance(Buildings.Buildings template) {
        if (template instanceof FarmLand)     return new FarmLand();
        if (template instanceof Bakery)       return new Bakery();
        if (template instanceof Lumberjack)   return new Lumberjack();
        if (template instanceof NormalHouse)   return new NormalHouse();
        if (template instanceof Stonemason)    return new Stonemason();
        if (template instanceof Quarry)        return new Quarry();
        if (template instanceof TreeFarm)      return new TreeFarm();
        throw new IllegalArgumentException("Unknown building type: " + template.getClass().getName());
    }
}
