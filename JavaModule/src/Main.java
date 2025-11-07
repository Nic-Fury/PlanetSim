//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {
    //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
    // to see how IntelliJ IDEA suggests fixing it.
    IO.println(String.format("Welcome to Planet Sim!"));

    //game loop
    while (true) {
        //Planet name
        IO.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        String chosenPlantName = IO.readln("Enter the Name of your Planet: ");
        IO.println("Planet Name: " + chosenPlantName);
        //Difficulty
        IO.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        String chosenDifficultyString = IO.readln("Choose your Difficulty: ");
        int chosenDifficultyInt = Integer.parseInt(chosenDifficultyString);
        IO.println("Difficulty: " + chosenDifficultyInt);
        IO.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        ActionMenu.runActionMenu();


    }

}
