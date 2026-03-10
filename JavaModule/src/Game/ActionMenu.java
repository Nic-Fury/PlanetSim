package Game;

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

    public static int readMapSize() {
        IO.println("+++++++++++++++++++++++++++++++++++++++");
        int chosenMapSizeInt;
        while (true) {
            String input = IO.readln("Choose your MapSize: (1-3) ");
            try {
                chosenMapSizeInt = Integer.parseInt(input.trim());
                break;
            } catch (NumberFormatException e) {
                IO.println("Invalid Input: Please enter an INTEGER.");
            }
        }
        IO.println("Difficulty: " + chosenMapSizeInt);
        IO.println("+++++++++++++++++++++++++++++++++++++++");
        IO.println();
        return chosenMapSizeInt;
    }

    public static void printActionMenu(int roundCounterInt){
        printResources();
        printActions(roundCounterInt);
    }

    public static void printResources(){
        IO.printlnSlow(5,"+---------------------------------------------+");
        // From the class GameState we get the resource instances and execute the getAmount() method
        IO.printSlowByChar(5,"| Gold:       " + String.format("%03d",GameState.getGoldInstance().getAmount()) +      " |  Wood: " + String.format("%03d",GameState.getWoodInstance().getAmount()) + " |  Stone: "+ String.format("%03d",GameState.getStoneInstance().getAmount()) +"  |");
        IO.printSlowByChar(5,"| Population: " + String.format("%03d",GameState.getPopulationInstance().getAmount()) +" |  Weed: " + String.format("%03d",GameState.getWeedInstance().getAmount()) + " |  Bread: "+ String.format("%03d",GameState.getBreadInstance().getAmount()) +"  |");
        IO.printSlowByChar(5,"| Workforce:  " + String.format("%03d", GameState.getWorkforceInstance().getAmount()) + " |  ????: " + "..." + "                |");
        IO.printlnSlow(5,"+---------------------------------------------+");
    }

    public static void printActions(int roundCounterInt){
        IO.printSlowByChar(5,"|             Choose one option:              |");
        IO.printSlowByChar(5,"| [1] Build                                   |");
        IO.printSlowByChar(5,"| [2] Do nothing                              |");
        IO.printSlowByChar(5,"| [3] Exit                                    |");
        IO.printlnSlow(5,"+---------------------------------------------+");
        while (true) {
            String input = IO.readln("Enter your choice for round "+roundCounterInt+" : (1-3) ");
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
