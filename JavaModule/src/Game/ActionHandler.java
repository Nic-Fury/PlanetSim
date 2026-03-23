package Game;

public class ActionHandler {


    public static void executeAction(int userInput){
            switch (userInput) {
                case 1:
                    //System.out.println("You chose to build.");
                    // Call method to gather resources
                    BuildMenu.drawBuildMenu();
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
                case 100:
                    System.out.println("CHEAT ACTIVATED: Developer Mode");
                    executeDeveloperMode();
                    break;
                case 999:
                    HighScoreHandler.printHighScoreBoard();
                    break;
                default:
                    System.out.println("Invalid input. Please choose a valid action.");
            }
    }





    public static void executeAction_Exit(){
        //HighScoreHandler.saveCurrentGameResult("QUIT");    // If you quit your HigheScore will not be safed
        IO.println("Exiting the game. Goodbye!");
        System.exit(0);
    }

    public static void gameOver(){
        IO.println("Game Over! Thanks for playing.");
        IO.println("Your Score: " + GameState.getGoldInstance().getAmount() + " Gold, " + GameState.getWoodInstance().getAmount() + " Holz");
        System.exit(0);
    }

    public static void executeDeveloperMode(){
        String developerChoice = IO.readln("Add 100 resources to ([G]old, [W]ood, [S]tone, [P]opulation, [We]ed, [B]read), [Wo]rkforce \n" +
                                                  "[exit] to leave developer mode \n").trim().toUpperCase();
        switch (developerChoice) {
            case "G"    -> GameState.getGoldInstance().addResources(100);
            case "W"    -> GameState.getWoodInstance().addResources(100);
            case "S"    -> GameState.getStoneInstance().addResources(100);
            case "P"    -> GameState.getPopulationInstance().addResources(100);
            case "WE"   -> GameState.getWeedInstance().addResources(100);
            case "B"    -> GameState.getBreadInstance().addResources(100);
            case "WO" -> GameState.getWorkforceInstance().addResources(100);
            case "EXIT", "E" -> IO.println("Exiting developer mode.");
            default     -> IO.println("Invalid input. Please enter a valid resource or 'exit'.");
        }
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }
}
