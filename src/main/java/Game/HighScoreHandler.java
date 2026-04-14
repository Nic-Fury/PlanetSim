package Game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HighScoreHandler {

    private static final String HEADER = "timestamp,planet,day,population,gold,wood,stone,bread,weed,result,score";
    private static final DateTimeFormatter TS_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private static class ScoreEntry {
        private final String timestamp;
        private final String planet;
        private final int day;
        private final int population;
        private final int gold;
        private final int wood;
        private final int stone;
        private final int bread;
        private final int weed;
        private final String result;
        private final int score;

        private ScoreEntry(String timestamp, String planet, int day, int population, int gold, int wood,
                           int stone, int bread, int weed, String result, int score) {
            this.timestamp = timestamp;
            this.planet = planet;
            this.day = day;
            this.population = population;
            this.gold = gold;
            this.wood = wood;
            this.stone = stone;
            this.bread = bread;
            this.weed = weed;
            this.result = result;
            this.score = score;
        }
    }

    public static void saveCurrentGameResult(String result) {
        Path filePath = resolveHighScoreFilePath();
        try {
            ensureFileExistsWithHeader(filePath);

            int day = GameState.getCurrentDay();
            int population = GameState.getPopulationInstance().getAmount();
            int gold = GameState.getGoldInstance().getAmount();
            int wood = GameState.getWoodInstance().getAmount();
            int stone = GameState.getStoneInstance().getAmount();
            int bread = GameState.getBreadInstance().getAmount();
            int weed = GameState.getWeedInstance().getAmount();
            int score = calculateScore(population, gold, wood, stone, bread, weed, result);

            String row = String.join(",",
                    escapeCsv(LocalDateTime.now().format(TS_FORMAT)),
                    escapeCsv(GameState.getCurrentPlanetName()),
                    String.valueOf(day),
                    String.valueOf(population),
                    String.valueOf(gold),
                    String.valueOf(wood),
                    String.valueOf(stone),
                    String.valueOf(bread),
                    String.valueOf(weed),
                    escapeCsv(result),
                    String.valueOf(score)
            );

            Files.writeString(
                    filePath,
                    row + System.lineSeparator(),
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            IO.println("Could not write highscore entry: " + e.getMessage());
        }
    }

    public static void printHighScoreBoard() {
        Path filePath = resolveHighScoreFilePath();
        List<ScoreEntry> entries;
        try {
            entries = loadEntries(filePath);
        } catch (IOException e) {
            IO.println("Could not read highscores: " + e.getMessage());
            return;
        }

        if (entries.isEmpty()) {
            IO.println("Highscore board is empty.");
            return;
        }

        entries.sort(
                Comparator.comparingInt((ScoreEntry entry) -> entry.score).reversed()
                        .thenComparingInt(entry -> entry.day)
                        .thenComparing(entry -> entry.timestamp, Comparator.reverseOrder())
        );

        IO.println();
        IO.println("+--------------------------------------------------------------------------------------------------------------------+");
        IO.println("|                                               HIGHSCORE BOARD                                                      |");
        IO.println("+--------------------------------------------------------------------------------------------------------------------+");
        IO.println(String.format("| %-3s | %-19s | %-16s | %-4s | %-4s | %-4s | %-4s | %-5s | %-5s | %-5s | %-7s | %-5s |",
                "#", "Timestamp", "Planet", "Day", "Pop", "Gold", "Wood", "Stone", "Bread", "Weed", "Result", "Score"));
        IO.println("+--------------------------------------------------------------------------------------------------------------------+");

        int rank = 1;
        for (ScoreEntry entry : entries) {
            if (rank > 20) {
                break;
            }
            IO.println(String.format("| %-3d | %-19s | %-16s | %-4d | %-4d | %-4d | %-4d | %-5d | %-5d | %-5d | %-7s | %-5d |",
                    rank,
                    trimToLength(entry.timestamp, 19),
                    trimToLength(entry.planet, 16),
                    entry.day,
                    entry.population,
                    entry.gold,
                    entry.wood,
                    entry.stone,
                    entry.bread,
                    entry.weed,
                    trimToLength(entry.result, 12),
                    entry.score));
            rank++;
        }

        IO.println("+--------------------------------------------------------------------------------------------------------------------+");
    }

    private static List<ScoreEntry> loadEntries(Path filePath) throws IOException {
        ensureFileExistsWithHeader(filePath);

        List<ScoreEntry> entries = new ArrayList<>();
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) {
                continue;
            }
            if (line.trim().equalsIgnoreCase(HEADER)) {
                continue;
            }

            List<String> columns = parseCsvLine(line);
            if (columns.size() != 11) {
                continue;
            }

            try {
                ScoreEntry entry = new ScoreEntry(
                        columns.get(0),
                        columns.get(1),
                        Integer.parseInt(columns.get(2)),
                        Integer.parseInt(columns.get(3)),
                        Integer.parseInt(columns.get(4)),
                        Integer.parseInt(columns.get(5)),
                        Integer.parseInt(columns.get(6)),
                        Integer.parseInt(columns.get(7)),
                        Integer.parseInt(columns.get(8)),
                        columns.get(9),
                        Integer.parseInt(columns.get(10))
                );
                entries.add(entry);
            } catch (NumberFormatException ignored) {
                // Skip malformed rows to keep the board robust.
            }
        }

        return entries;
    }

    private static int calculateScore(int population, int gold, int wood, int stone, int bread, int weed, String result) {
        int baseScore = (population * 10) + gold + wood + stone + bread + weed;
        if (result != null && result.toUpperCase().startsWith("WIN")) {
            baseScore += 500;
        }
        return Math.max(0, baseScore);
    }

    private static void ensureFileExistsWithHeader(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            Files.writeString(filePath, HEADER + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.CREATE);
            return;
        }

        if (Files.size(filePath) == 0) {
            Files.writeString(filePath, HEADER + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
            return;
        }

        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        boolean hasNonEmptyLine = lines.stream().anyMatch(line -> line != null && !line.trim().isEmpty());
        if (!hasNonEmptyLine) {
            Files.writeString(filePath, HEADER + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }

    private static Path resolveHighScoreFilePath() {
        Path resourcePath = Paths.get("src", "main", "resources", "highscores.csv");
        if (Files.exists(resourcePath)) {
            return resourcePath;
        }

        Path legacyPath = Paths.get("highscores.csv");
        if (Files.exists(legacyPath)) {
            return legacyPath;
        }

        return resourcePath;
    }

    private static String escapeCsv(String value) {
        if (value == null) {
            return "";
        }

        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\"") || escaped.contains("\n") || escaped.contains("\r")) {
            return "\"" + escaped + "\"";
        }
        return escaped;
    }

    private static List<String> parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        if (line == null) {
            return result;
        }

        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }

        result.add(current.toString());
        return result;
    }

    private static String trimToLength(String input, int maxLength) {
        if (input == null) {
            return "";
        }
        if (input.length() <= maxLength) {
            return input;
        }
        if (maxLength <= 3) {
            return input.substring(0, maxLength);
        }
        return input.substring(0, maxLength - 3) + "...";
    }
}


