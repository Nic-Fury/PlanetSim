package Events;
import Game.IO;

public abstract class Events {

    private final String displayName;
    private final String description;

    public Events(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() { return displayName; }

    public String getDescription() { return description; }

    public abstract boolean isNegativeEvent();

    public abstract void applyEvent();

    public void triggerPossibleEvent() {
        int randomEventProbability = (int) (Math.random() * 100);

//        IO.println("DEVELOPER randomEventProbability" + randomEventProbability);

        /*****************************************************************
         * Event probabilities:
         * 0-29: Storm Event (30% chance)
         * 30-99: Nothing happens (70% chance)
         *****************************************************************/

        if (randomEventProbability < 30) {
            printEventIntro();
            new StormEvent().applyEvent();
            printEventOutro();
        }

    }

    public void printEventIntro() {
        IO.printlnSlow("###############################################\n" +
                   "#~~~~~~~~~~~~~~~Event occurred~~~~~~~~~~~~~~~~#\n" +
                   "###############################################");
        IO.printSlowByChar(">>The Event " + getDisplayName());
        IO.printSlowByChar(">>This is a " + (isNegativeEvent() ? "negative" : "positive") + " event.");
        IO.printSlowByChar(">>Description: " + getDescription());
    }

    public void printEventOutro() {
        IO.println("############# END OF STORM EVENT ##############");
        IO.delay(4000);
        IO.println();
        IO.println();
    }
}
