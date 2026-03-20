package Game;

public class BuildMenu {

    protected static void drawBuildMenu() {
        IO.println();
        IO.println("###############################################");
        IO.println("#~~~~~~~~~~~~~~~~~~BuildMenu~~~~~~~~~~~~~~~~~~#");
        IO.println("###############################################");
        ActionMenu.printResources();

        IO.println("| [1] " + BuildHandler.haus.displayName       + " | G: " + BuildHandler.haus.goldKosten       + "  | W: " + BuildHandler.haus.holzKosten       + "  | S: " + BuildHandler.haus.steinKosten + "     |");
        IO.println("| [2] " + BuildHandler.farm.displayName       + " | G: " + BuildHandler.farm.goldKosten       + "  | W: " + BuildHandler.farm.holzKosten       + "  | S: " + BuildHandler.farm.steinKosten + "     |");
        IO.println("| [3] " + BuildHandler.bakery.displayName      + "   | G: " + BuildHandler.bakery.goldKosten     + " | W: " + BuildHandler.bakery.holzKosten     + "  | S: " + BuildHandler.bakery.steinKosten + "     |");
        IO.println("| [4] " + BuildHandler.lumberjack.displayName  + " | G: " + BuildHandler.lumberjack.goldKosten + "  | W: " + BuildHandler.lumberjack.holzKosten + "  | S: " + BuildHandler.lumberjack.steinKosten + "     |");
        IO.println("| [5] " + BuildHandler.treeFarm.displayName    + "   | G: " + BuildHandler.treeFarm.goldKosten   + " | W: " + BuildHandler.treeFarm.holzKosten   + " | S: " + BuildHandler.treeFarm.steinKosten + "     |");
        IO.println("| [6] " + BuildHandler.stonemason.displayName  + " | G: " + BuildHandler.stonemason.goldKosten + "  | W: " + BuildHandler.stonemason.holzKosten + "  | S: " + BuildHandler.stonemason.steinKosten + "     |");
        IO.println("| [7] " + BuildHandler.quarry.displayName      + "     | G: " + BuildHandler.quarry.goldKosten     + " | W: " + BuildHandler.quarry.holzKosten     + " | S: " + BuildHandler.quarry.steinKosten + "    |");
        IO.println("| [99] Demolish building                      |");
        IO.println("| [0] Cancel                                  |");
        IO.println("+---------------------------------------------+");

        String buildChoice = IO.readln("Choose a building: ");
        int buildChoiceValid = Integer.parseInt(buildChoice);

        BuildHandler.executeBuildingAction(buildChoiceValid);
    }

}
