//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {



    //game round loop
    int roundCounterInt = 0;
    int chosenDifficultyInt = 0;
    while (true) {

        // first round: welcome message, planet name and difficulty selection, round
        if (roundCounterInt == 0) {
            //Welcome message
            IO.println();
            IO.println();
            IO.println(String.format("Welcome to Planet Sim!"));
            IO.println();

            //Planet name
            String chosenPlanetName = ActionMenu.readPlanetName();
            //Difficulty
            chosenDifficultyInt = ActionMenu.readDifficulty();

            Round.startRound(chosenDifficultyInt);
        } else if (roundCounterInt > 0) {
            Round.startRound(chosenDifficultyInt);
        }

        roundCounterInt++;
    }

}
