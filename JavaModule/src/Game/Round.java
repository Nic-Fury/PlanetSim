package Game;

import Buildings.FarmLand;
import Buildings.Lumberjack;

public class Round {


    public static int startFirstRound(int roundCounterInt){
        //Welcome message
        IO.println();
        IO.println();
        IO.println(String.format("Welcome to Planet Sim!"));
        IO.println();

        //Planet name
        String chosenPlanetName = ActionMenu.readPlanetName();
        int chosenMapSizeInt = ActionMenu.readMapSize();


        startRound(chosenMapSizeInt, roundCounterInt);
        return chosenMapSizeInt;
    }

    public static void startRound(int chosenMapSizeInt, int roundCounterInt){

        ///   ToDO Das Zeug in eine Methode auslagern ------------------------------------------------------------------------------------------------------------------------------------
        int holzBonus = GameState.getAnzahlHolzfaeller() * Lumberjack.HOLZ_PRO_RUNDE;
        if (holzBonus > 0) {
            GameState.addWood(holzBonus);
            // IO.println("Holzfäller produzieren " + holzBonus + " Holz!");
        }
        int weedBonus = GameState.getAnzahlFarmLand() * FarmLand.WEED_PRO_RUNDE;
        if (weedBonus > 0) {
            GameState.addWeed(weedBonus);
            IO.println("FarmLand produzieren " + weedBonus + " Weed!");
        }


        Gameboard.printPlanet(chosenMapSizeInt);
        ActionMenu.printActionMenu(roundCounterInt);

    }

}
