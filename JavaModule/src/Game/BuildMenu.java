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


/*
    Vielleicht sollten wir das Baumenü anpassen/aufteilen in Kategorien, damit wir nicht eine so lange Liste haben, wenn wir mehr Gebäude hinzufügen.
     - Kategorie 1: Wohngebäude (Haus, Villa, etc.)
     - Kategorie 2: Landwirtschaft (Farmland, Obstgarten, etc.)
     - Kategorie 3: Industrie (Holzfäller, Steinbruch, etc.)
     - Kategorie 4: Spezielle Gebäude (Bäckerei, Marktplatz, etc.)
     ^^ nur eine Idee
 */

public class BuildMenu {


    protected static void drawBuildMenu() {
        IO.println();
        IO.println("###############################################");
        IO.println("#~~~~~~~~~~~~~~~~~~BuildMenu~~~~~~~~~~~~~~~~~~#");
        IO.println("###############################################");
        ActionMenu.printResources();


        IO.println("| [1] " + BuildHandler.haus.displayName       + "| Gold: " + BuildHandler.haus.goldKosten       + "  | Wood: " + BuildHandler.haus.holzKosten       + "        |");
        IO.println("| [2] " + BuildHandler.farm.displayName       + "| Gold: " + BuildHandler.farm.goldKosten       + "  | Wood: " + BuildHandler.farm.holzKosten       + "        |");
        IO.println("| [3] " + BuildHandler.bakery.displayName +   "  | Gold: " + BuildHandler.bakery.goldKosten     + " | Wood: " + BuildHandler.bakery.holzKosten + "        |");
        IO.println("| [4] " + BuildHandler.lumberjack.displayName + "| Gold: " + BuildHandler.lumberjack.goldKosten + "  | Wood: " + BuildHandler.lumberjack.holzKosten + "        |");
        IO.println("| [5] " + BuildHandler.treeFarm.displayName + "  | Gold: " + BuildHandler.treeFarm.goldKosten + " | Wood: " + BuildHandler.treeFarm.holzKosten + "       |");
        IO.println("| [6] " + BuildHandler.stonemason.displayName +   "| Gold: " + BuildHandler.stonemason.goldKosten     + "  | Wood: " + BuildHandler.stonemason.holzKosten + "        |");
        IO.println("| [7] " + BuildHandler.quarry.displayName +   "    | Gold: " + BuildHandler.quarry.goldKosten     + " | Wood: " + BuildHandler.quarry.holzKosten + "       |");
        IO.println("| [0] Cancel                                  |");
        IO.println("+---------------------------------------------+");

        String buildChoice = IO.readln("Choose a building: ");
        int buildChoiceValid = Integer.parseInt(buildChoice);

        BuildHandler.executeBuildingAction(buildChoiceValid);

        }




}
