package Game;

import Buildings.Bakery;
import Buildings.FarmLand;
import Buildings.Lumberjack;
import Buildings.TreeFarm;
import Buildings.NormalHouse;
import Buildings.Stonemason;
import Buildings.Quarry;

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
        if (!GameState.kannBauen(template)) {
            IO.println("Nicht genug Ressourcen für " + template.displayName + "!");
            return;
        }

        if (!GameState.hatGenugArbeitskraft(template)) {
            IO.println("Nicht genug Arbeitskraft für " + template.displayName + "!");
            IO.println("Verfügbar: " + (GameState.getPopulationInstance().getAmount() - GameState.getPlacedBuildings().stream().mapToInt(Buildings.Buildings::getWorkforceRequired).sum()) + " | Benötigt: " + template.getWorkforceRequired());
            return;
        }

        String[][] map = GameState.getCurrentMap();
        int maxX = map[0].length - 1;
        int maxY = map.length - 1;

        IO.println("Koordinaten eingeben (X: 0-" + maxX + ", Y: 0-" + maxY + ")");

        int x, y;
        try {
            x = Integer.parseInt(IO.readln("X-Koordinate: ").trim());
            y = Integer.parseInt(IO.readln("Y-Koordinate: ").trim());
        } catch (NumberFormatException e) {
            IO.println("Ungültige Koordinaten.");
            return;
        }

        if (y < 0 || y >= map.length || x < 0 || x >= map[y].length) {
            IO.println("Koordinaten außerhalb der Karte!");
            return;
        }

        if (map[y][x].equals("BLANC")) {
            IO.println("Auf diesem Feld kannst du nicht bauen!");
            return;
        }

        if (GameState.isCellOccupied(x, y)) {
            IO.println("Hier steht bereits ein Gebäude!");
            return;
        }

        // Create a fresh instance so each placed building has its own coordinates
        Buildings.Buildings building = createNewInstance(template);

        GameState.ressourcenAbziehen(building);
        building.x = x;
        building.y = y;
        Gameboard.printSingleColorBlockAtCoordinates(building.color, x, y);
        GameState.registerBuilding(building);   // generic – works for any building
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
