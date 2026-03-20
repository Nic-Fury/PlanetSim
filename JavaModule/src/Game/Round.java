package Game;

import Events.Events;
import java.util.Map;

public class Round {

    public static int startFirstRound(int roundCounterInt){
        //Welcome message
        IO.println();
        IO.println();
//        IO.printlnSlowByChar(100,">> Welcome to PlanetSim!");
        IO.printlnSlowByChar(1,"/======================================================================================\\\n" +
                "||██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗    ████████╗ ██████╗ ||\n" +
                "||██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝    ╚══██╔══╝██╔═══██╗||\n" +
                "||██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗         ██║   ██║   ██║||\n" +
                "||██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝         ██║   ██║   ██║||\n" +
                "||╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗       ██║   ╚██████╔╝||\n" +
                "|| ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝       ╚═╝    ╚═════╝ ||\n" +
                "||██████╗ ██╗      █████╗ ███╗   ██╗████████╗   ███████╗██╗███╗   ███╗    ██╗         ||\n" +
                "||██╔══██╗██║     ██╔══██╗████╗  ██║╚══██╔══╝   ██╔════╝██║████╗ ████║    ██║         ||\n" +
                "||██████╔╝██║     ███████║██╔██╗ ██║   ██║█████╗███████╗██║██╔████╔██║    ██║         ||\n" +
                "||██╔═══╝ ██║     ██╔══██║██║╚██╗██║   ██║╚════╝╚════██║██║██║╚██╔╝██║    ╚═╝         ||\n" +
                "||██║     ███████╗██║  ██║██║ ╚████║   ██║      ███████║██║██║ ╚═╝ ██║    ██╗         ||\n" +
                "||╚═╝     ╚══════╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝      ╚══════╝╚═╝╚═╝     ╚═╝    ╚═╝         ||\n" +
                "\\======================================================================================/");

        //Planet name
        String chosenPlanetName = ActionMenu.readPlanetName();
        int chosenMapSizeInt = ActionMenu.readMapSize();

        //reset Children Counter
        GameState.resetChildhoodState();

        //from here on its a normal round
        startRound(chosenMapSizeInt, roundCounterInt);
        return chosenMapSizeInt;
    }

    public static void startRound(int chosenMapSizeInt, int roundCounterInt){
        // Start of a new day: children age; two-day-olds become workforce-eligible.
        GameState.advanceChildhoodDay();
        checkForWinningCondition(roundCounterInt);
        checkForLosingCondition();
        ActionMenu.printDayInfo(roundCounterInt);
        printResourceUpdate(updateResources()); //updateResources() handles the logic of resource production and workforce synchronization, returns a map of what was produced this round for printing in printResourceUpdate()
        Gameboard.printPlanet(chosenMapSizeInt);
        ActionMenu.printActionMenu(roundCounterInt);
        Events.triggerPossibleEvent();
    }

    private static void checkForWinningCondition(int roundCounterInt) {
        // --- Winning Condition: Population reaches 100 ---
        if (GameState.getPopulationInstance().getAmount() >= 100) {
            IO.println();
            IO.printlnSlowByChar("Congratulations! You've reached a population of 100 and won the game!");
            IO.println("It took you " + roundCounterInt + " days to achieve this milestone.");
            IO.printlnSlowByChar(1,"/================================================================\\\n" +
                    "||██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗██╗███╗   ██╗    ██╗ ||\n" +
                    "||╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██║████╗  ██║    ██║ ||\n" +
                    "|| ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║██╔██╗ ██║    ██║ ||\n" +
                    "||  ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║██║╚██╗██║    ╚═╝ ||\n" +
                    "||   ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝██║██║ ╚████║    ██╗ ||\n" +
                    "||   ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝    ╚═╝ ||\n" +
                    "\\================================================================/");
            System.exit(0);
//            return true;
        }
//        return false;
    }

    private static boolean checkForLosingCondition() {
        // --- Losing Condition: Population drops to 0 ---
        if (GameState.getPopulationInstance().getAmount() <= 0) {
            IO.println();
            IO.printlnSlowByChar("Unfortunately, your population has dropped to zero. Game Over!");
            IO.printlnSlowByChar(1,"/============================================================================\\\n" +
                    "|| ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗ ||\n" +
                    "||██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗||\n" +
                    "||██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝||\n" +
                    "||██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗||\n" +
                    "||╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║||\n" +
                    "|| ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝||\n" +
                    "||                             ██               ██                          ||\n" +
                    "||                           ████▄   ▄▄▄▄▄▄▄   ▄████                        ||\n" +
                    "||                              ▀▀█▄█████████▄█▀▀                           ||\n" +
                    "||                                █████████████                             ||\n" +
                    "||                                ██▀▀▀███▀▀▀██                             ||\n" +
                    "||                                ██   ███   ██                             ||\n" +
                    "||                                █████▀▄▀█████                             ||\n" +
                    "||                                 ███████████                              ||\n" +
                    "||                             ▄▄▄██  █▀█▀█  ██▄▄▄                          ||\n" +
                    "||                             ▀▀██           ██▀▀                          ||\n" +
                    "||                               ▀▀           ▀▀                            ||\n" +
                    "\\============================================================================/");
            System.exit(0);
        }
        if (GameState.getBreadInstance().getAmount() <= 0){
            IO.println();
            IO.printlnSlowByChar("Your population has starved due to lack of bread. Game Over!");
            IO.printlnSlowByChar(1,"/============================================================================\\\n" +
                    "|| ██████╗  █████╗ ███╗   ███╗███████╗     ██████╗ ██╗   ██╗███████╗██████╗ ||\n" +
                    "||██╔════╝ ██╔══██╗████╗ ████║██╔════╝    ██╔═══██╗██║   ██║██╔════╝██╔══██╗||\n" +
                    "||██║  ███╗███████║██╔████╔██║█████╗      ██║   ██║██║   ██║█████╗  ██████╔╝||\n" +
                    "||██║   ██║██╔══██║██║╚██╔╝██║██╔══╝      ██║   ██║╚██╗ ██╔╝██╔══╝  ██╔══██╗||\n" +
                    "||╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗    ╚██████╔╝ ╚████╔╝ ███████╗██║  ██║||\n" +
                    "|| ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝     ╚═════╝   ╚═══╝  ╚══════╝╚═╝  ╚═╝||\n" +
                    "||                             ██               ██                          ||\n" +
                    "||                           ████▄   ▄▄▄▄▄▄▄   ▄████                        ||\n" +
                    "||                              ▀▀█▄█████████▄█▀▀                           ||\n" +
                    "||                                █████████████                             ||\n" +
                    "||                                ██▀▀▀███▀▀▀██                             ||\n" +
                    "||                                ██   ███   ██                             ||\n" +
                    "||                                █████▀▄▀█████                             ||\n" +
                    "||                                 ███████████                              ||\n" +
                    "||                             ▄▄▄██  █▀█▀█  ██▄▄▄                          ||\n" +
                    "||                             ▀▀██           ██▀▀                          ||\n" +
                    "||                               ▀▀           ▀▀                            ||\n" +
                    "\\============================================================================/");
            System.exit(0);
        }
        return false;
    }



    /**
     * Delegiert die komplette Tagesproduktion an den Resource-Service.
     * Round bleibt dadurch auf Ablaufsteuerung fokussiert.
     */
    private static Map<String, Integer> updateResources() {
        return RoundResourceService.updateResourcesForRound();
    }

    private static void printResourceUpdate(Map<String, Integer> producedThisRound) {
        for (Map.Entry<String, Integer> entry : producedThisRound.entrySet()) {
            IO.println(">> Today " + entry.getValue() + " " + entry.getKey() + " have been produced");
        }
    }

}
