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
        IO.println("+---------------------------------------------+");
        String name;
        while (true) {
            name = IO.readln("Enter the Name of your Planet: ").trim();
            if (!name.isEmpty()) {
                IO.printlnSlowByChar(30,">> Planet Name: " + name);
                return name;
            }
            IO.println("Empty Input: Please enter a NAME.");
        }
    }

    public static int readMapSize() {
        IO.println("+---------------------------------------------+");
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
        IO.printlnSlowByChar(30,">> Difficulty: " + chosenMapSizeInt);
        IO.println("+---------------------------------------------+");
        IO.printlnSlow(1000,"");
        return chosenMapSizeInt;
    }

    protected static void printDayInfo(int roundCounterInt) {
        IO.println("+---------------------------------------------+");
        IO.println("|================= Day " + String.format("%03d",roundCounterInt) +" ===================|");
        IO.println("+--------------------"+    "---"                              +"----------------------+");
        IO.printlnSlow(1000,"");
    }

    public static void printActionMenu(int roundCounterInt){
        printResources();
        printActions(roundCounterInt);
    }

    public static void printResources(){
        IO.println("+---------------------------------------------+");
        // From the class GameState we get the resource instances and execute the getAmount() method
        IO.println("| Gold:       " + String.format("%03d",GameState.getGoldInstance().getAmount()) +      " |  Wood: " + String.format("%03d",GameState.getWoodInstance().getAmount()) + " |  Stone: "+ String.format("%03d",GameState.getStoneInstance().getAmount()) +"  |");
        IO.println("| Workforce:  " + String.format("%03d", GameState.getWorkforceInstance().getAmount()) + " |  Weed: " + String.format("%03d",GameState.getWeedInstance().getAmount()) + " |  Bread: "+ String.format("%03d",GameState.getBreadInstance().getAmount()) +"  |");
        IO.println("|>>>>>>>>>>>>>> Population: " + String.format("%03d",GameState.getPopulationInstance().getAmount()) +" <<<<<<<<<<<<<<|");
        IO.printlnSlow(1000,"+---------------------------------------------+");
    }

    public static void printActions(int roundCounterInt){
        IO.println("|             Choose one option:              |");
        IO.println("| [1] Build                                   |");
        IO.println("| [2] Do nothing                              |");
        IO.println("| [3] Exit                                    |");
        IO.printlnSlow(1000,"+---------------------------------------------+");
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
