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

    /**
     * Randomly selects and triggers an event.
     * Static so it can be called without an instance: Events.triggerPossibleEvent()
     *
     * Event probabilities:
     *  0-19: StormEvent (20%)
     * 20-39: PerfectYieldEvent (20%)
     * 40-99: No event (60%)
     */
    public static void triggerPossibleEvent() {
        int roll = (int) (Math.random() * 100);

        Events event = null;
        if (roll < 20) {
            event = new StormEvent();
        } else if (roll < 40) {
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
        IO.println("############# END OF STORM EVENT ##############");
        IO.delay(4000);
        IO.println();
        IO.println();
    }
}
