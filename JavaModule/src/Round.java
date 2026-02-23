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
        Gameboard.printPlanet(chosenMapSizeInt);
        ActionMenu.printActionMenu(roundCounterInt);
    }

}
