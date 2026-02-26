package Game;

import Buildings.FarmLand;
import Buildings.Lumberjack;
import Buildings.NormalHouse;

public class BuildHandler {

    protected static final NormalHouse haus       = new NormalHouse();
    protected static final FarmLand    farm        = new FarmLand();
    protected static final Lumberjack  lumberjack  = new Lumberjack();

    protected static void executeBuildingAction(int buildchoice) {
        switch (buildchoice) {
            case 1 -> bauenStarten(haus);
            case 2 -> bauenStarten(farm);
            case 3 -> bauenStarten(lumberjack);
            case 0 -> IO.println("Bauen abgebrochen.");
            default -> IO.println("Ungültige Eingabe.");
        }
    }


    protected static void bauenStarten(Buildings.Buildings building) {
        if (!GameState.kannBauen(building)) {
            IO.println("Nicht genug Ressourcen für " + building.displayName + "!");
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

        GameState.ressourcenAbziehen(building);
        Gameboard.printSingleColorBlockAtCoordinates(building.color, x, y);
        Gameboard.printSingleColorBlockAtCoordinates(building.color, x, y);

        if (building instanceof Lumberjack) {
            GameState.holzfaellerHinzufuegen();
        }

        if (building instanceof FarmLand) {
            GameState.farmLandHinzufuegen();
        }

        IO.println(building.displayName + " erfolgreich gebaut bei (" + x + ", " + y + ")!");
        ActionMenu.printResources();
    }
}
