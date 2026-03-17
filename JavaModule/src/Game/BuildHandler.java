package Game;

import Buildings.Bakery;
import Buildings.FarmLand;
import Buildings.Lumberjack;
import Buildings.TreeFarm;
import Buildings.NormalHouse;
import Buildings.Stonemason;
import Buildings.Quarry;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class BuildHandler {

    protected static final NormalHouse haus       = new NormalHouse();
    protected static final FarmLand    farm       = new FarmLand();
    protected static final Lumberjack  lumberjack = new Lumberjack();
    protected static final Bakery      bakery     = new Bakery();
    protected static final Stonemason  stonemason = new Stonemason();
    protected static final Quarry      quarry     = new Quarry();
    protected static final TreeFarm    treeFarm   = new TreeFarm();

    protected static void executeBuildingAction(int buildChoice) {
        switch (buildChoice) {
            case 1 -> bauenStarten(haus);
            case 2 -> bauenStarten(farm);
            case 3 -> bauenStarten(bakery);
            case 4 -> bauenStarten(lumberjack);
            case 5 -> bauenStarten(treeFarm);
            case 6 -> bauenStarten(stonemason);
            case 7 -> bauenStarten(quarry);
            case 100 -> executeBuildingCheat();

            case 0 -> IO.println("Bauen abgebrochen.");
            default -> IO.println("Ungültige Eingabe.");
        }
    }

    protected static void bauenStarten(Buildings.Buildings template) {
        if (!checkForNeededResources(template)) return;
        if (!checkForNeededWorkforce(template)) return;

        int[] coordinates = readCoordinates(template);
        if (coordinates == null) return;

        int x = coordinates[0];
        int y = coordinates[1];

        if (!checkBuildFieldAllowed(template, x, y, false)) return;

        buildAtCoordinates(template, x, y, true);
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

    private static int[] readCoordinates(Buildings.Buildings template) {
        String[][] map = GameState.getCurrentMap();
        int maxX = map[0].length - 1;
        int maxY = map.length - 1;

        IO.println("Koordinaten eingeben (X: 0-" + maxX + ", Y: 0-" + maxY + ")");
        IO.println("Bei X kannst du statt einer Zahl auch [R] für zufällige, valide Koordinaten eingeben.");

        String xInput = IO.readln("X-Koordinate: ").trim();

        if ("r".equalsIgnoreCase(xInput)) {
            int[] randomCoordinates = findRandomValidCoordinates(template, 200);
            if (randomCoordinates == null) {
                IO.println("Keine validen Zufallskoordinaten gefunden. Bauvorgang abgebrochen.");
                return null;
            }

            IO.println("Zufallskoordinaten gewählt: (" + randomCoordinates[0] + ", " + randomCoordinates[1] + ")");
            return randomCoordinates;
        }

        int x;
        try {
            x = Integer.parseInt(xInput);
        } catch (NumberFormatException e) {
            IO.println("Ungültige X-Koordinate. Erlaubt sind nur ganze Zahlen oder [R].");
            return null;
        }

        int y;
        try {
            y = Integer.parseInt(IO.readln("Y-Koordinate: ").trim());
        } catch (NumberFormatException e) {
            IO.println("Ungültige Y-Koordinate. Erlaubt sind nur ganze Zahlen.");
            return null;
        }

        return new int[]{x, y};
    }

    private static int[] findRandomValidCoordinates(Buildings.Buildings template, int maxAttempts) {
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

    private static boolean checkBuildFieldAllowed(Buildings.Buildings template, int x, int y, boolean silent) {
        String[][] map = GameState.getCurrentMap();

        if (!isCoordinateInMap(map, x, y)) {
            if (!silent) IO.println("Koordinaten außerhalb der Karte!");
            return false;
        }

        if (isBlancTile(map, x, y)) {
            if (!silent) IO.println("Auf diesem Feld kannst du nicht bauen!");
            return false;
        }

        if (!isAllowedBiome(template, map[y][x])) {
            Set<String> allowed = template.getAllowedBiomes();
            if (!silent) IO.println("Auf diesem Feld kannst du kein " + template.displayName.trim() + " bauen (Biome: " + map[y][x] + "). Erlaubte Biome: " + allowed);
            return false;
        }

        if (GameState.isCellOccupied(x, y)) {
            if (!silent) IO.println("Hier steht bereits ein Gebäude!");
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

    private static void buildAtCoordinates(Buildings.Buildings template, int x, int y, boolean isNoCheat) {
        Buildings.Buildings building = createNewInstance(template);

        String bgBiomeColor = GameState.getCurrentMap()[y][x];

        if(isNoCheat) GameState.ressourcenAbziehen(building);
        building.x = x;
        building.y = y;
        Gameboard.printSingleColorBlockAtCoordinates(building.buildingSymbolColor, bgBiomeColor, x, y);
        GameState.registerBuilding(building);
        GameState.markCellAsOccupied(x, y);

        IO.println(building.displayName + " erfolgreich gebaut bei (" + x + ", " + y + ")!");
        if(isNoCheat) ActionMenu.printResources();
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

    private static void executeBuildingCheat() {
        IO.printlnSlow("CHEAT ACTIVATED: 5 Buildings will be placed at random valid coordinates without consuming resources BUT workforce.");
        for (int i = 0; i < 5; i++) {
            int randomChoice = ThreadLocalRandom.current().nextInt(1, 8);
            Buildings.Buildings template;
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

            int[] coordinates = findRandomValidCoordinates(template, 100);
            if (coordinates != null) {
                buildAtCoordinates(template, coordinates[0], coordinates[1], false);
            } else {
                IO.println("Keine validen Koordinaten gefunden für " + template.displayName.trim() + ". Gebäude übersprungen.");
            }
        }
    }
}
