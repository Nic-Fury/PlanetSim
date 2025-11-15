//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    //Welcome message
    IO.println();
    IO.println();
    IO.println(String.format("Welcome to Planet Sim!"));
    IO.println();

    //Planet name
    String chosenPlanetName = ActionMenu.readPlanetName();

    //Difficulty
    int chosenDifficultyInt = ActionMenu.readDifficulty();

    //game round loop
    while (true) {

        ActionMenu.printActionMenu();
        Gameboard.printPlanet(chosenDifficultyInt);

        break; // Remove this break to enable infinite loop
    }

}
