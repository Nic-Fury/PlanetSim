public class Round {


    public static void startFirstRound(int chosenDifficultyInt){
        //Welcome message
        IO.println();
        IO.println();
        IO.println(String.format("Welcome to Planet Sim!"));
        IO.println();

        //Planet name
        String chosenPlanetName = ActionMenu.readPlanetName();
        //Difficulty
        chosenDifficultyInt = ActionMenu.readDifficulty();


        startRound(chosenDifficultyInt);
    }

    public static void startRound(int chosenDifficultyInt){
        Gameboard.printPlanet(chosenDifficultyInt, "green");
        ActionMenu.printActionMenu();
    }

}
