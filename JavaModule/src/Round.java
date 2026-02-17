public class Round {


    public static void startFirstRound(int chosenDifficultyInt,  int roundCounterInt){
        //Welcome message
        IO.println();
        IO.println();
        IO.println(String.format("Welcome to Planet Sim!"));
        IO.println();

        //Planet name
        String chosenPlanetName = ActionMenu.readPlanetName();
        //Difficulty
        chosenDifficultyInt = ActionMenu.readDifficulty();


        startRound(chosenDifficultyInt, roundCounterInt);
    }

    public static void startRound(int chosenDifficultyInt, int roundCounterInt){
        Gameboard.printPlanet(chosenDifficultyInt, "gege");
        ActionMenu.printActionMenu(roundCounterInt);
    }

}
