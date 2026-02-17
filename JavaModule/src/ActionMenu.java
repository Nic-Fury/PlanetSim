/**********************************************************************
 *           Diese Klasse ist dazu da, um das Runden
 *           AktionsMenü dazustellen und zu verwalten
 *
 *          +----------------------------------+
 *          | Gold: x  |  Holz: x  |  Stein: x |
 *          +----------------------------------+
 *          |	Waehle eine Action       	   |
 *          |       	        			   |
 *          |  1. Runde beenden		           |
 *          |  2. Meine Resourcen              |
 *          |  3. Bauen                        |
 *          |                                  |
 *          |  S. Speichern                    |
 *          |  B. Beenden                      |
 *          |                                  |
 *          +----------------------------------+
 *
 ***********************************************************************/


public class ActionMenu {


    public static String readPlanetName() {
        IO.println("+++++++++++++++++++++++++++++++++++++++");
        String name;
        while (true) {
            name = IO.readln("Enter the Name of your Planet: ").trim();
            if (!name.isEmpty()) {
                IO.println("Planet Name: " + name);
                return name;
            }
            IO.println("Empty Input: Please enter a NAME.");
        }
    }

    public static int readDifficulty() {
        IO.println("+++++++++++++++++++++++++++++++++++++++");
        int chosenDifficultyInt;
        while (true) {
            String input = IO.readln("Choose your Difficulty: (1-3) ");
            try {
                chosenDifficultyInt = Integer.parseInt(input.trim());
                break;
            } catch (NumberFormatException e) {
                IO.println("Invalid Input: Please enter an INTEGER.");
            }
        }
        IO.println("Difficulty: " + chosenDifficultyInt);
        IO.println("+++++++++++++++++++++++++++++++++++++++");
        IO.println();
        return chosenDifficultyInt;
    }

    public static void printActionMenu(){
        printResources();
        printActions();
    }

    public static void printResources(){
        IO.println("+-------------------------------------+");
        // From the class GameState we get the resource instances and execute the getAmount() method
        IO.println("| Gold: " + GameState.getGoldInstance().getAmount() +" |  Holz: " + GameState.getWoodInstance().getAmount() + " |  Stein: x   |");
        IO.println("+-------------------------------------+");
    }

    public static void printActions(){
        IO.println("|         Waehle eine Aktion:         |");
        IO.println("| 1. Build                            |");
        IO.println("| 2. Do nothing                       |");
        IO.println("| 3. Exit                             |");
        IO.println("+-------------------------------------+");
        while (true) {
            String input = IO.readln("Auswahl: (1-3) ");
            int userinput;
            try {
                userinput = Integer.parseInt(input.trim());
                ActionHandler.executeAction(userinput);
                break;
            } catch (NumberFormatException e) {
                IO.println("Invalid Input: Please enter an INTEGER. (1-2)");
            }
        }
        IO.println("");
    }


}
