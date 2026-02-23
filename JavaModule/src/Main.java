//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
void main() {

    int roundCounterInt = 1;
    int chosenMapSizeInt = 0;

    while (true) {

        // first round: welcome message, planet name and difficulty selection, round
        if (roundCounterInt == 1) {
            chosenMapSizeInt = Round.startFirstRound(roundCounterInt);
        } else if (roundCounterInt > 1) {
            Round.startRound(chosenMapSizeInt, roundCounterInt);
        }

        roundCounterInt++;
    }

}
