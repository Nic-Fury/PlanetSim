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

    /**
     * Renders a single map cell in the console.
     *
     * Usage:
     * - Pass a plain biome token (for example "GREEN", "BLUE", "YELLOW")
     *   to print a normal terrain tile.
     * - Pass a composed value in the format "BIOME|symbol" to print a
     *   building symbol on top of its original biome background.
     *
     * How it works:
     * - Detects composed building tiles via the "|" separator.
     * - Delegates composed values to {@link #printBuildingBlock(String, String)}.
     * - Otherwise maps the biome token to an ANSI background color and prints
     *   a 3-character wide tile block.
     *
     * @param colorName biome token or composed tile value ("BIOME|symbol")
     */
    private static void printSingleColorBlock(String colorName){
        String name = colorName == null ? "" : colorName.trim().toUpperCase();

        // Format "BIOME|symbol" – building on top of a biome tile
        if (colorName != null && colorName.contains("|")) {
            String[] parts = colorName.split("\\|", 2);
            printBuildingBlock(parts[1], parts[0]);
            return;
        }

        String bg = switch (name) {
            case "BLACK"   -> "\u001b[40m";
            case "RED"     -> "\u001b[41m";
            case "GREEN"   -> "\u001b[42m";
            case "YELLOW"  -> "\u001b[43m";
            case "BLUE"    -> "\u001b[44m";
            case "MAGENTA" -> "\u001b[45m";
            case "CYAN"    -> "\u001b[46m";
            case "WHITE"   -> "\u001b[47m";
            case "BLANC"   -> "\u001b[0m";
            case "GRAY"    -> "\u001b[100m";
            default        -> "\u001b[0m";
        };

        String ANSI_RESET = "\u001b[0m";
        IO.print(bg + "   " + ANSI_RESET);
    }

    /**
     * Prints a building symbol with the provided biome background color.
     *
     * Usage:
     * - buildingCode should be the visual building token (for example "[H]").
     * - bgColor should be the original biome token of the tile
     *   (for example "GREEN", "BLUE", "YELLOW").
     *
     * How it works:
     * - Converts bgColor to the matching ANSI background escape code.
     * - Prints buildingCode on that background.
     * - Resets ANSI formatting afterwards.
     * - Falls back to a green background if bgColor is unknown.
     *
     * @param buildingCode building symbol/token to render
     * @param bgColor original biome color token for the tile background
     */
    private static void printBuildingBlock(String buildingCode, String bgColor) {
        String ANSI_RESET = "\u001b[0m";

        String bg = switch (bgColor == null ? "" : bgColor.trim().toUpperCase()) {
            case "BLACK"   -> "\u001b[40m";
            case "RED"     -> "\u001b[41m";
            case "GREEN"   -> "\u001b[42m";
            case "YELLOW"  -> "\u001b[43m";
            case "BLUE"    -> "\u001b[44m";
            case "MAGENTA" -> "\u001b[45m";
            case "CYAN"    -> "\u001b[46m";
            case "WHITE"   -> "\u001b[47m";
            case "GRAY"    -> "\u001b[100m";
            default        -> "\u001b[42m"; // fallback: grün
        };

        java.lang.IO.print(bg + buildingCode + ANSI_RESET);
    }

    /**
     * Updates one tile with a building overlay and redraws the map.
     *
     * Usage:
     * - Call this after coordinates were validated and a building is placed.
     * - buildingSymbolColor is the symbol/token to display for the building.
     * - bgBiomeColor is the biome token that existed at (x, y) before placement.
     *
     * How it works:
     * - Stores the tile as "BIOME|symbol" so rendering can keep the original
     *   biome background under the building symbol.
     * - Writes the updated map to {@link GameState}.
     * - Triggers a redraw via drawMap(currentMap).
     *
     * @param buildingSymbolColor symbol/token for the building
     * @param bgBiomeColor original biome token at the target coordinate
     * @param x target column index (0-based)
     * @param y target row index (0-based)
     */
    public static void printSingleColorBlockAtCoordinates(String buildingSymbolColor, String bgBiomeColor, int x, int y){
        String[][] currentMap = GameState.getCurrentMap();
        if (currentMap == null || y < 0 || y >= currentMap.length || x < 0 || x >= currentMap[y].length) {
            IO.println("Ungültige Koordinaten oder Map nicht geladen!");
            return;
        }

        // Format: "BIOME|symbol" – so drawMap can restore bg + symbol on redraw
        currentMap[y][x] = bgBiomeColor + "|" + buildingSymbolColor;
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
