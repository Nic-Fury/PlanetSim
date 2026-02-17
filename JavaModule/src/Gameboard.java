/**********************************************************************
 *           Diese Klasse ist dazu da, um das Spelfeld (Den Planten)
 *           dazustellen und zu verwalten
 *
 *
 *
 *
 *
 *
 *
 *
 ***********************************************************************/


public class Gameboard {

    private static String[][] currentMap;

    public static void printPlanet(int mapSize, String color){
        currentMap = chooseMap(mapSize, color);
        drawMap(currentMap);
    }

    private static String[][] chooseMap(int mapSize, String color){

        String[][] currentMapTemp;

        String[][] map10 = new String[][]
        {
                {"BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC"},
                {"BLANC", "BLUE",  "BLUE",  "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC"},
                {"BLANC", "BLUE",  "BLUE",  "GREEN", "YELLOW","YELLOW","GREEN", "GREEN", "GREEN", "BLANC"},
                {"GREEN", "GREEN", "GREEN", "GREEN", "YELLOW","YELLOW","GREEN", "GREEN", "GREEN", "GREEN"},
                {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                {"GREEN", "GREEN", "MAGENTA","MAGENTA","MAGENTA","MAGENTA","GREEN","GREEN","GREEN","GREEN"},
                {"GREEN", "GREEN", "MAGENTA","MAGENTA","MAGENTA","MAGENTA","GREEN","GREEN","GREEN","GREEN"},
                {"BLANC", "GREEN", "GREEN",  "GREEN",  "GREEN",  "GREEN",  "GREEN","GREEN","GREEN","BLANC"},
                {"BLANC", "GREEN", "GREEN",  "CYAN",   "CYAN",   "CYAN",   "CYAN","GREEN","GREEN","BLANC"},
                {"BLANC", "BLANC", "BLANC",  "CYAN",   "CYAN",   "CYAN",   "CYAN","BLANC","BLANC","BLANC"}
        };
        currentMapTemp = map10;

        if (color.equals("green")){
            String[][] map10green = new String[][]
            {
                    {"BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC"},
                    {"BLANC", "GREEN",  "GREEN",  "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC"},
                    {"BLANC", "GREEN",  "GREEN",  "GREEN", "GREEN","GREEN","GREEN", "GREEN", "GREEN", "BLANC"},
                    {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN","GREEN","GREEN", "GREEN", "GREEN", "GREEN"},
                    {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                    {"GREEN", "GREEN", "GREEN","GREEN","GREEN","GREEN","GREEN","GREEN","GREEN","GREEN"},
                    {"GREEN", "GREEN", "GREEN","GREEN","GREEN","GREEN","GREEN","GREEN","GREEN","GREEN"},
                    {"BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN","GREEN","GREEN","BLANC"},
                    {"BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN","GREEN","GREEN","BLANC"},
                    {"BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN","BLANC","BLANC","BLANC"}
            };
            currentMapTemp = map10green;
        }

        return currentMapTemp;
    }

    private static void drawMap(String[][] map){
        for (String[] strings : map) {
            for (String string : strings) {
                printSingleColorBlock(string);
            }
            IO.println();
        }
        IO.println();
    }

    private static void printSingleColorBlock(String colorName){
        //  Method uses ANSI escape codes for background colors


        // Gibt es etwas besseres hier als einen switch case?
        String name = colorName == null ? "" : colorName.trim().toUpperCase();
        String bg = switch (name) {
            case "BLACK" -> "\u001b[40m";
            case "RED" -> "\u001b[41m";
            case "GREEN" -> "\u001b[42m";
            case "YELLOW" -> "\u001b[43m";
            case "BLUE" -> "\u001b[44m";
            case "MAGENTA" -> "\u001b[45m";
            case "CYAN" -> "\u001b[46m";
            case "WHITE" -> "\u001b[47m";
            case "BLANC" -> "\u001b[0m";
            default -> "\u001b[0m";
        };

        String ANSI_RESET = "\u001b[0m";

        IO.print(bg + "   " + ANSI_RESET);
    }

    public static void printSingleColorBlockAtCoordinates(String colorName, int x, int y){
        // Validierung der Koordinaten
        if (x < 0 || x >= 10 || y < 0 || y >= 10 || currentMap == null) {
            IO.println("Ungültige Koordinaten oder Map nicht geladen!");
            return;
        }

        // Farbe an Koordinaten ändern
        currentMap[y][x] = colorName;

        // Map neu zeichnen
        drawMap(currentMap);
    }

}
