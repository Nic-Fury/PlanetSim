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

    public abstract String stringASCIIArt();

    /**
     * Randomly selects and triggers an event.
     * Static so it can be called without an instance: Events.triggerPossibleEvent()
     *
     * Event probabilities:
     *  StormEvent (10%)
     *  PerfectYieldEvent (10%)
     *  No event (80%)
     */
    public static void triggerPossibleEvent() {
        int roll = (int) (Math.random() * 100);

        Events event = null;
        if (roll < 10) {
            event = new StormEvent();
        } else if (roll < 20) {
            event = new PerfectYieldEvent();
        } // else: no event (60%)

        if (event != null) {
            event.printEventIntro();
            event.applyEvent();
            event.printEventOutro();
        }
    }

    public void printEventIntro() {
        IO.printlnSlow("###############################################\n" +
                   "#~~~~~~~~~~~~~~~Event occurred~~~~~~~~~~~~~~~~#\n" +
                   "###############################################");
        IO.printlnSlowByChar(">>The Event " + getDisplayName());
        IO.printlnSlowByChar(">>This is a " + (isNegativeEvent() ? "negative" : "positive") + " event.");
        IO.printlnSlowByChar(">>Description: " + getDescription());
    }

    public void printEventOutro() {
        IO.println("################ END OF EVENT #################");
        IO.delay(4000);
        IO.println();
        IO.println();
    }
}
