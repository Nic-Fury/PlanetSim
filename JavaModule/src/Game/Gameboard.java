package Game;

import java.util.ArrayDeque;    // Import ok? (Biom spreading)
import java.util.Deque;         // Import ok? (Biom spreading)
import java.util.Random;        // Import ok? (Biom spreading)

/**********************************************************************
 *           Diese Klasse ist dazu da, um das Spelfeld (Den Planten)
 *           dazustellen und zu verwalten
 *
 ***********************************************************************/

public class Gameboard {

    //  Biome generations Parameter
    private static final double WATER_SEED_FACTOR    = 0.06;  // Anteil der Fläche als Water-Seeds (z.B. 0.01 = 1%)
    private static final double DESERT_SEED_FACTOR   = 0.012; // Anteil für Wüste
    private static final double MOUNTAIN_SEED_FACTOR = 0.008; // Anteil für Berge

    private static final double WATER_SPREAD    = 0.25; // Wie stark Wasser von jedem Seed auswächst
    private static final double DESERT_SPREAD   = 0.33;
    private static final double MOUNTAIN_SPREAD = 0.33;

    public static void printPlanet(int mapSize){

        if (!GameState.hasMap()) {
            GameState.setCurrentMap(chooseMap(mapSize));
        }
        drawMap(GameState.getCurrentMap());

        drawXCoordinates(mapSize);

        IO.println();
    }

    private static String[][] chooseMap(int mapSize){

        String[][] currentMapTemp;

        //default map 10x10
        currentMapTemp = new String[][]
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

        //difficulty 1 map: 10x10 green
        if (mapSize == 1){
            currentMapTemp = new String[][]
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

            //difficulty 2 map: 15x15 green
        } else if (mapSize == 2){
            currentMapTemp = new String[][]
                    {
                            {"BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC"},
                            {"BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC"},
                            {"BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC"},
                            {"BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC"},
                            {"BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC"}
                    };

            //difficulty 3 map: 20x20 green
        } else if (mapSize == 3) {
            currentMapTemp = new String[][]
                    {
                            {"BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC"},
                            {"BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC"},
                            {"BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN"},
                            {"BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC"},
                            {"BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC"},
                            {"BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC"},
                            {"BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "GREEN", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC", "BLANC"}
                    };
        }

        // Apply biomes onto GREEN tiles
        applyBiomes(currentMapTemp);
        return currentMapTemp;
    }

    /**
     * Fügt zufällige Biome hinzu: BLUE (Wasser), YELLOW (Wüste), GRAY (Berge).
     * Nur GREEN-Tiles werden dabei überschrieben. Es werden Seeds gestartet und
     * mit einer Spread-Wahrscheinlichkeit ausgedehnt, damit zusammenhängende
     * Biome entstehen.
     */
    private static void applyBiomes(String[][] map) {
        Random rnd = new Random();
        int rows = map.length;
        int cols = map[0].length;
        int area = rows * cols;

        int waterSeeds    = Math.max(1, (int) Math.round(area * WATER_SEED_FACTOR));
        int desertSeeds   = Math.max(1, (int) Math.round(area * DESERT_SEED_FACTOR));
        int mountainSeeds = Math.max(1, (int) Math.round(area * MOUNTAIN_SEED_FACTOR));

        spreadBiome(map, "BLUE",  waterSeeds,    WATER_SPREAD,    rnd);
        spreadBiome(map, "YELLOW", desertSeeds,   DESERT_SPREAD,   rnd);
        spreadBiome(map, "GRAY",  mountainSeeds,  MOUNTAIN_SPREAD, rnd);
    }

    private static void spreadBiome(String[][] map, String biome, int seeds, double spreadChance, Random rnd) {
        int rows = map.length;
        int cols = map[0].length;

        for (int s = 0; s < seeds; s++) {
            // find a random starting GREEN cell
            int sx, sy;
            int attempts = 0;
            do {
                sy = rnd.nextInt(rows);
                sx = rnd.nextInt(cols);
                attempts++;
                if (attempts > rows * cols) break;
            } while (!"GREEN".equals(map[sy][sx]));

            if (!"GREEN".equals(map[sy][sx])) continue;
            Deque<int[]> q = new ArrayDeque<>();
            map[sy][sx] = biome;
            q.add(new int[]{sx, sy});

            // BFS-like spread
            while (!q.isEmpty()) {
                int[] p = q.poll();
                int x = p[0], y = p[1];

                // try 4-neighbours
                int[][] nbr = {{x+1,y},{x-1,y},{x,y+1},{x,y-1}};
                for (int[] n : nbr) {
                    int nx = n[0], ny = n[1];
                    if (nx < 0 || ny < 0 || ny >= rows || nx >= cols) continue;
                    if (!"GREEN".equals(map[ny][nx])) continue;
                    if (rnd.nextDouble() < spreadChance) {
                        map[ny][nx] = biome;
                        q.add(new int[]{nx, ny});
                    }
                }
            }
        }
    }

    private static void drawMap(String[][] map){
        for (int row = 0; row < map.length; row++) {
            for (String string : map[row]) {
                printSingleColorBlock(string);
            }
            drawYCoordinates(row);
            IO.println();
        }
    }

    private static void printSingleColorBlock(String colorName){
        //  Method uses ANSI escape codes for background colors

        // Gibt es etwas besseres hier als einen switch case? Wahrscheinlich nicht, da es nur 8 Farben gibt.
        String name = colorName == null ? "" : colorName.trim().toUpperCase();

        // Check if the colorName is a building code (enclosed in square brackets)
        if (colorName != null && colorName.startsWith("[") && colorName.endsWith("]")) {
            printBuildingBlock(colorName);
            return;
        }

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
            case "GRAY" -> "\u001b[100m"; // heller schwarzer Hintergrund als "grau"
            default -> "\u001b[0m";
        };

        String ANSI_RESET = "\u001b[0m";
        IO.print(bg + "   " + ANSI_RESET);
    }

    private static void printBuildingBlock(String buildingCode) {
        String ANSI_GREEN_BG = "\u001b[42m";
        String ANSI_RESET    = "\u001b[0m";

        java.lang.IO.print(ANSI_GREEN_BG + buildingCode + ANSI_RESET);
    }

    public static void printSingleColorBlockAtCoordinates(String colorName, int x, int y){
        String[][] currentMap = GameState.getCurrentMap();
        if (currentMap == null || y < 0 || y >= currentMap.length || x < 0 || x >= currentMap[y].length) {
            IO.println("Ungültige Koordinaten oder Map nicht geladen!");
            return;
        }

        currentMap[y][x] = colorName;
        GameState.setCurrentMap(currentMap);
        drawMap(currentMap);
    }

    private static void drawXCoordinates(int mapSizeCode) {

        int realMapSize = switch (mapSizeCode){
            case 1 -> 10;
            case 2 -> 15;
            case 3 -> 20;
            default -> 10;
        };

        IO.print("");
        for (int i = 0; i < realMapSize * 3; i++) {
            if (i % 3 == 1) {
                IO.print(String.format("%02d",(i / 3)) + " ");
            } else {
                IO.print("");
            }
        }
        IO.println();
    }
    private static void drawYCoordinates(int rowIndex) {
        String ANSI_RESET = "\u001b[0m";
        IO.print(ANSI_RESET + " " + String.format("%02d", rowIndex));
    }
}
