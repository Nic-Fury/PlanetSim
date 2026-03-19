package Game;

import Buildings.Bakery;
import Buildings.Buildings;
import Buildings.FarmLand;
import Buildings.Lumberjack;
import Buildings.NormalHouse;
import Buildings.Quarry;
import Buildings.Stonemason;
import Buildings.TreeFarm;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class BuildHandler {

    // Vorlagen (Templates) für das Menü / Anzeige
    public static final Buildings haus       = new NormalHouse();
    public static final Buildings farm       = new FarmLand();
    public static final Buildings bakery     = new Bakery();
    public static final Buildings lumberjack = new Lumberjack();
    public static final Buildings treeFarm   = new TreeFarm();
    public static final Buildings stonemason = new Stonemason();
    public static final Buildings quarry     = new Quarry();

    public static void executeBuildingAction(int choice) {
        switch (choice) {
            case 1 -> bauenStarten(haus);
            case 2 -> bauenStarten(farm);
            case 3 -> bauenStarten(bakery);
            case 4 -> bauenStarten(lumberjack);
            case 5 -> bauenStarten(treeFarm);
            case 6 -> bauenStarten(stonemason);
            case 7 -> bauenStarten(quarry);
            case 99 -> demolishFlow();
            case 100 -> executeBuildingCheat();
            case 0 -> IO.println("Building cancelled.");
            default -> IO.println("Invalid selection in build menu.");
        }
    }

    private static void demolishFlow() {
        List<Buildings> placed = GameState.getPlacedBuildings();
        if (placed.isEmpty()) {
            IO.println("No buildings available to demolish.");
            return;
        }

        IO.println("Placed buildings:");
        for (int i = 0; i < placed.size(); i++) {
            Buildings b = placed.get(i);
            IO.println("[" + i + "] " + b.displayName.trim() + " @ (" + b.x + "," + b.y + ")");
        }

        String sel = IO.readln("Choose index of building to demolish (or 'c' to cancel): ").trim();
        if (sel.equalsIgnoreCase("c") || sel.isEmpty()) {
            IO.println("Cancelled.");
            return;
        }
        try {
            int idx = Integer.parseInt(sel);
            if (idx < 0 || idx >= placed.size()) {
                IO.println("Invalid index.");
                return;
            }
            Buildings toDemolish = placed.get(idx);
            GameState.demolishBuilding(toDemolish);
            Gameboard.redraw();
        } catch (NumberFormatException e) {
            IO.println("Invalid input.");
        }
    }

    private static void bauenStarten(Buildings template) {
        if (template == null) {
            IO.println("Invalid building template.");
            return;
        }
        if (!checkForNeededResources(template)) return;
        if (!hasEnoughWorkforce(template)) return;

        int[] coordinates = readCoordinates(template);
        if (coordinates == null) return;

        int x = coordinates[0];
        int y = coordinates[1];

        if (!checkBuildFieldAllowed(template, x, y, false)) return;

        buildAtCoordinates(template, x, y, true);
    }

    private static boolean checkForNeededResources(Buildings template) {
        if (!GameState.kannBauen(template)) {
            IO.println("Not enough resources for " + template.displayName + "!");
            return false;
        }
        return true;
    }

    private static boolean hasEnoughWorkforce(Buildings template) {
        if (!GameState.hatGenugArbeitskraft(template)) {
            IO.println("Not enough workforce for " + template.displayName + "!");
            IO.println("Available: " + getAvailableWorkforce() + " | Required: " + template.getWorkforceRequired());
            return false;
        }
        return true;
    }

    private static int getAvailableWorkforce() {
        return GameState.getPopulationInstance().getAmount()
                - GameState.getPlacedBuildings().stream().mapToInt(Buildings::getWorkforceRequired).sum();
    }

    private static int[] readCoordinates(Buildings template) {
        String[][] map = GameState.getCurrentMap();
        int maxX = map[0].length - 1;
        int maxY = map.length - 1;

        IO.println("Enter coordinates (X: 0-" + maxX + ", Y: 0-" + maxY + ")");
        IO.println("For X, you can enter [R] instead of a number for random valid coordinates.");

        String xInput = IO.readln("X coordinate: ").trim();

        if ("r".equalsIgnoreCase(xInput)) {
            int[] randomCoordinates = findRandomValidCoordinates(template, 200);
            if (randomCoordinates == null) {
                IO.println("No valid random coordinates found. Build process cancelled.");
                return null;
            }

            IO.println("Random coordinates selected: (" + randomCoordinates[0] + ", " + randomCoordinates[1] + ")");
            return randomCoordinates;
        }

        int x;
        try {
            x = Integer.parseInt(xInput);
        } catch (NumberFormatException e) {
            IO.println("Invalid X coordinate. Only whole numbers or [R] are allowed.");
            return null;
        }

        int y;
        try {
            y = Integer.parseInt(IO.readln("Y coordinate: ").trim());
        } catch (NumberFormatException e) {
            IO.println("Invalid Y coordinate. Only whole numbers are allowed.");
            return null;
        }

        return new int[]{x, y};
    }

    private static int[] findRandomValidCoordinates(Buildings template, int maxAttempts) {
        String[][] map = GameState.getCurrentMap();
        int maxX = map[0].length - 1;
        int maxY = map.length - 1;

        for (int attempt = 0; attempt < maxAttempts; attempt++) {
            int x = ThreadLocalRandom.current().nextInt(maxX + 1);
            int y = ThreadLocalRandom.current().nextInt(maxY + 1);

            if (checkBuildFieldAllowed(template, x, y, true)) {
                return new int[]{x, y};
            }
        }

        return null;
    }

    protected static boolean checkBuildFieldAllowed(Buildings template, int x, int y, boolean silent) {
        if (!GameState.hasMap()) {
            if (!silent) IO.println("No map loaded.");
            return false;
        }

        String[][] map = GameState.getCurrentMap();

        if (!isCoordinateInMap(map, x, y)) {
            if (!silent) IO.println("Coordinates are outside the map!");
            return false;
        }

        if (isBlancTile(map, x, y)) {
            if (!silent) IO.println("You cannot build on this tile!");
            return false;
        }

        String tile = map[y][x];
        if (!isAllowedBiome(template, tile)) {
            Set<String> allowed = template.getAllowedBiomes();
            if (!silent) {
                IO.println("You cannot build " + template.displayName.trim() + " on this tile (biome: " + tile + "). Allowed biomes: " + allowed);
            }
            return false;
        }

        if (GameState.isCellOccupied(x, y)) {
            if (!silent) IO.println("A building is already placed here!");
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

    private static boolean isAllowedBiome(Buildings template, String tile) {
        Set<String> allowed = template.getAllowedBiomes();
        return allowed == null || allowed.contains(tile);
    }

    private static void buildAtCoordinates(Buildings template, int x, int y, boolean useResources) {
        Buildings newBuilding = createInstanceFromTemplate(template);
        if (newBuilding == null) {
            IO.println("Failed to create building instance.");
            return;
        }

        String bg = getBgBiomeColorAt(x, y);

        if (useResources) GameState.ressourcenAbziehen(newBuilding);
        GameState.markCellAsOccupied(x, y);
        newBuilding.x = x;
        newBuilding.y = y;
        GameState.registerBuilding(newBuilding);

        Gameboard.printSingleColorBlockAtCoordinates(newBuilding.buildingSymbolColor, bg, x, y);
        IO.println("Building placed: " + newBuilding.displayName.trim() + " @ (" + x + "," + y + ")");
        if (useResources) ActionMenu.printResources();
    }

    private static void executeBuildingCheat() {
        IO.printlnSlow("CHEAT ACTIVATED: 5 Buildings will be placed at random valid coordinates without consuming resources BUT workforce.");
        for (int i = 0; i < 5; i++) {
            int randomChoice = ThreadLocalRandom.current().nextInt(1, 8);
            Buildings template;
            switch (randomChoice) {
                case 1 -> template = haus;
                case 2 -> template = farm;
                case 3 -> template = bakery;
                case 4 -> template = lumberjack;
                case 5 -> template = treeFarm;
                case 6 -> template = stonemason;
                case 7 -> template = quarry;
                default -> throw new IllegalStateException("Unexpected value: " + randomChoice);
            }

            if (!hasEnoughWorkforce(template)) {
                IO.println("Not enough workforce for cheat building " + template.displayName.trim() + ". Building skipped.");
                continue;
            }

            int[] coordinates = findRandomValidCoordinates(template, 100);
            if (coordinates != null) {
                buildAtCoordinates(template, coordinates[0], coordinates[1], false);
            } else {
                IO.println("No valid coordinates found for " + template.displayName.trim() + ". Building skipped.");
            }
        }
    }

    private static String getBgBiomeColorAt(int x, int y) {
        String[][] map = GameState.getCurrentMap();
        if (map == null || y < 0 || y >= map.length || x < 0 || x >= map[y].length) return "GREEN";
        String tile = map[y][x];
        if (tile == null) return "GREEN";
        return tile.contains("|") ? tile.split("\\|", 2)[0] : tile;
    }

    private static Buildings createInstanceFromTemplate(Buildings template) {
        if (template instanceof NormalHouse) return new NormalHouse();
        if (template instanceof FarmLand)    return new FarmLand();
        if (template instanceof Bakery)      return new Bakery();
        if (template instanceof Lumberjack)  return new Lumberjack();
        if (template instanceof TreeFarm)    return new TreeFarm();
        if (template instanceof Stonemason)  return new Stonemason();
        if (template instanceof Quarry)      return new Quarry();
        try {
            return template.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
