//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    int roundCounterInt = 0;
    int chosenDifficultyInt = 0;

    while (true) {

        // first round: welcome message, planet name and difficulty selection, round
        if (roundCounterInt == 0) {
            Round.startFirstRound(chosenDifficultyInt);
        } else if (roundCounterInt > 0) {
            Round.startRound(chosenDifficultyInt);
        }

        roundCounterInt++;
    }

}
