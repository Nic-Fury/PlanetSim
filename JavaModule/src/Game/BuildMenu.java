package Game;

/**********************************************************************
 *           Diese Klasse ist dazu da, um das Bau Menü
 *           dazustellen und zu verwalten
 *
 *          +-------------------------------------+
 *          |         Baumenü                     |
 *          +-------------------------------------+
 *          +-------------------------------------+
 *          | Gold: 100 |  Holz: 10 |  Stein: x   |
 *          +-------------------------------------+
 *          | [1] Haus | Gold: 5 | Holz: 3  |
 *          | [2] Farmland | Gold: 3 | Holz: 5  |
 *          | [3] Holzfäller | Gold: 4 | Holz: 6  |
 *          | [0] Abbrechen                       |
 *          +-------------------------------------+
 *          Wähle ein Gebäude: 3
 *
 ***********************************************************************/


public class BuildMenu {


    protected static void drawBuildMenu() {
        IO.println();
        IO.println("###############################################");
        IO.println("#~~~~~~~~~~~~~~~~~~BuildMenu~~~~~~~~~~~~~~~~~~#");
        IO.println("###############################################");
        ActionMenu.printResources();


        IO.println("| [1] " + BuildHandler.haus.displayName       + "| Gold: " + BuildHandler.haus.goldKosten       + " | Wood: " + BuildHandler.haus.holzKosten       + "         |");
        IO.println("| [2] " + BuildHandler.farm.displayName       + "| Gold: " + BuildHandler.farm.goldKosten       + " | Wood: " + BuildHandler.farm.holzKosten       + "         |");
        IO.println("| [3] " + BuildHandler.lumberjack.displayName + "| Gold: " + BuildHandler.lumberjack.goldKosten + " | Wood: " + BuildHandler.lumberjack.holzKosten + "         |");
        IO.println("| [0] Cancel                                  |");
        IO.println("+---------------------------------------------+");

        String buildChoice = IO.readln("Choose a building: ");
        int buildChoiceValid = Integer.parseInt(buildChoice);

        BuildHandler.executeBuildingAction(buildChoiceValid);

        }




}
