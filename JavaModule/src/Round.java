public class Round {


    public static void startFirstRound(int chosenMapSizeInt,  int roundCounterInt){
        //Welcome message
        IO.println();
        IO.println();
        IO.println(String.format("Welcome to Planet Sim!"));
        IO.println();

        //Planet name
        String chosenPlanetName = ActionMenu.readPlanetName();
        //Difficulty
        chosenMapSizeInt = ActionMenu.readMapSize();


        startRound(chosenMapSizeInt, roundCounterInt);
    }

    public static void startRound(int chosenMapSizeInt, int roundCounterInt){
        Gameboard.printPlanet(chosenMapSizeInt);
        ActionMenu.printActionMenu(roundCounterInt);
    }

}
