package Game;

import Buildings.NormalHouse;
import Buildings.FarmLand;
import Buildings.Lumberjack;

public class ActionHandler {


    public static void executeAction(int userInput){
            switch (userInput) {
                case 1:
                    //System.out.println("You chose to build.");
                    // Call method to gather resources
                    BuildMenu.executeBuildMenu();
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





    public static void executeAction_Exit(){
        IO.println("Exiting the game. Goodbye!");
        System.exit(0);
    }

    public static void gameOver(){
        IO.println("Game Over! Thanks for playing.");
        IO.println("Your Score: " + GameState.getGoldInstance().getAmount() + " Gold, " + GameState.getWoodInstance().getAmount() + " Holz");
        System.exit(0);
    }
}
