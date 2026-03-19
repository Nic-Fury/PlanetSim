package Game;

import Buildings.*;
import java.util.List;

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
            case 0 -> IO.println("Build cancelled.");
            default -> IO.println("Ungültige Auswahl im Build-Menu.");
        }
    }

    private static void demolishFlow() {
        List<Buildings> placed = GameState.getPlacedBuildings();
        if (placed.isEmpty()) {
            IO.println("Keine Gebäude zum Abreißen vorhanden.");
            return;
        }

        IO.println("Platzierte Gebäude:");
        for (int i = 0; i < placed.size(); i++) {
            Buildings b = placed.get(i);
            IO.println("[" + i + "] " + b.displayName.trim() + " @ (" + b.x + "," + b.y + ")");
        }

        String sel = IO.readln("Wähle Index des abzureißenden Gebäudes (oder 'c' zum Abbrechen): ").trim();
        if (sel.equalsIgnoreCase("c") || sel.isEmpty()) {
            IO.println("Abbruch.");
            return;
        }
        try {
            int idx = Integer.parseInt(sel);
            if (idx < 0 || idx >= placed.size()) {
                IO.println("Ungültiger Index.");
                return;
            }
            Buildings toDemolish = placed.get(idx);
            GameState.demolishBuilding(toDemolish);
            Gameboard.redraw();
        } catch (NumberFormatException e) {
            IO.println("Ungültige Eingabe.");
        }
    }

    private static void bauenStarten(Buildings template) {
        if (template == null) {
            IO.println("Ungültiges Gebäude-Template.");
            return;
        }

        // Ressourcen & Arbeitskraft prüfen (Template reicht hier)
        if (!GameState.kannBauen(template)) {
            IO.println("Nicht genug Ressourcen für: " + template.displayName.trim());
            return;
        }
        if (!GameState.hatGenugArbeitskraft(template)) {
            IO.println("Nicht genug Arbeitskraft für: " + template.displayName.trim());
            return;
        }

        // Koordinaten einlesen
        int x, y;
        try {
            String sx = IO.readln("Enter X coordinate (0-based): ").trim();
            String sy = IO.readln("Enter Y coordinate (0-based): ").trim();
            x = Integer.parseInt(sx);
            y = Integer.parseInt(sy);
        } catch (NumberFormatException e) {
            IO.println("Ungültige Koordinaten-Eingabe.");
            return;
        }

        if (!checkBuildFieldAllowed(template, x, y, false)) {
            return;
        }

        // Erstelle neue Instanz des Gebäudes (keine Wiederverwendung der Template-Instanz)
        Buildings newBuilding = createInstanceFromTemplate(template);
        if (newBuilding == null) {
            IO.println("Fehler beim Erstellen des Gebäudes.");
            return;
        }

        // Hintergrund-Biome vor dem Platzieren ermitteln
        String bg = getBgBiomeColorAt(x, y);

        // Ressourcen abziehen, Zelle markieren, registrieren und Karte aktualisieren
        GameState.ressourcenAbziehen(newBuilding);
        GameState.markCellAsOccupied(x, y);
        newBuilding.x = x;
        newBuilding.y = y;
        GameState.registerBuilding(newBuilding);

        Gameboard.printSingleColorBlockAtCoordinates(newBuilding.buildingSymbolColor, bg, x, y);
        IO.println("Gebäude gebaut: " + newBuilding.displayName.trim() + " @ (" + x + "," + y + ")");
    }

    protected static boolean checkBuildFieldAllowed(Buildings template, int x, int y, boolean ignoreOccupied) {
        if (!GameState.hasMap()) {
            IO.println("Keine Karte geladen.");
            return false;
        }
        String[][] map = GameState.getCurrentMap();
        if (y < 0 || y >= map.length || x < 0 || x >= map[y].length) {
            IO.println("Koordinaten außerhalb der Karte.");
            return false;
        }
        if (!ignoreOccupied && GameState.isCellOccupied(x, y)) {
            IO.println("Zelle ist bereits belegt.");
            return false;
        }

        String tile = map[y][x];
        String biome = tile == null ? "GREEN" : (tile.contains("|") ? tile.split("\\|", 2)[0] : tile);

        // Template kann null bei allowedBiomes => dann erlauben
        try {
            if (template.getAllowedBiomes() != null && !template.getAllowedBiomes().contains(biome)) {
                IO.println("Gebäude " + template.displayName.trim() + " kann nicht auf Biome " + biome + " gebaut werden.");
                return false;
            }
        } catch (Exception e) {
            // Falls getAllowedBiomes nicht implementiert / null, erlaube standardmäßig.
        }

        return true;
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
        // Fallback: versuche Reflection (optional) - aber nicht zwingend
        try {
            return template.getClass().getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
