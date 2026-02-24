package Game;

import Buildings.NormalHouse;

public class ActionHandler {


    public static void executeAction(int userInput){
            switch (userInput) {
                case 1:
                    //System.out.println("You chose to build.");
                    // Call method to gather resources
                    executeBuildMenu();
                    break;
                case 2:
                    System.out.println("You chose to do nothing.");
                    // Call method to build structures
                    break;
                case 3:
                    executeAction_Exit();
                    break;
                case 4:
                    System.out.println("You chose to end your turn.");
                    // Call method to end turn
                    break;
                default:
                    System.out.println("Invalid input. Please choose a valid action.");
            }
    }


    private static void executeBuildMenu() {
        IO.println("+-------------------------------------+");
        IO.println("|         Baumenü                     |");
        IO.println("+-------------------------------------+");
        ActionMenu.printResources();

        NormalHouse haus = new NormalHouse();
        haus.printInfo();
        IO.println("| [0] Abbrechen                       |");
        IO.println("+-------------------------------------+");

        String input = IO.readln("Wähle ein Gebäude: ");

        switch (input.trim()) {
            case "1" -> bauenStarten(haus);
            case "0" -> IO.println("Bauen abgebrochen.");
            default  -> IO.println("Ungültige Eingabe.");
        }
    }

    private static void bauenStarten(Buildings.Buildings building) {
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
        IO.println(building.displayName + " erfolgreich gebaut bei (" + x + ", " + y + ")!");
        ActionMenu.printResources();
    }


    public static void executeAction_Exit(){
        IO.println("Exiting the game. Goodbye!");
        System.exit(0);
    }
}
