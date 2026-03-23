package Game;

import Skills.Skills;

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
                    openSkillMenu();
                    break;
                case 4:
                    executeAction_Exit();
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

    // --- Skill Menu Implementation ---

    private static void openSkillMenu() {
        IO.println();
        IO.println("###############################################");
        IO.println("#~~~~~~~~~~~~~~~~~~SkillMenu~~~~~~~~~~~~~~~~~~#");
        IO.println("###############################################");

        int idx = 1;
        java.util.List<Skills> list = new java.util.ArrayList<>(GameState.getRegisteredSkills().values());
        for (Skills s : list) {
            int nextLevel = Math.min(s.getLevel() + 1, s.getMaxLevel());
            String costText = s.isMaxLevel() ? "MAX" : String.valueOf(s.getUpgradeCostForNextLevel());
            IO.println("| [" + idx + "] " + s.getDisplayName()
                    + " | Next Lv: " + nextLevel
                    + " | Cost: " + costText + " G  |");
            idx++;
        }
        IO.println("| [0] Cancel                                  |");
        IO.println("+---------------------------------------------+");

        String input = IO.readln("Choose a skill to upgrade: ").trim();
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            IO.println("Invalid input.");
            return;
        }

        if (choice == 0) return;
        if (choice < 1 || choice > list.size()) {
            IO.println("Invalid selection.");
            return;
        }

        if (GameState.hasUpgradedSkillThisDay()) {
            IO.println("You can only upgrade one skill per round.");
            return;
        }

        Skills selected = list.get(choice - 1);
        if (selected.isMaxLevel()) {
            IO.println("Skill is already at max level.");
            return;
        }

        int cost = selected.getUpgradeCostForNextLevel();
        if (GameState.getGoldInstance().getAmount() < cost) {
            IO.println("Not enough Gold. Upgrade failed.");
            return;
        }

        GameState.getGoldInstance().subResources(cost);
        selected.upgrade();
        GameState.markSkillUpgradeForCurrentDay();
        IO.println("Upgraded " + selected.getDisplayName() + " to level " + selected.getLevel() + ".");
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
