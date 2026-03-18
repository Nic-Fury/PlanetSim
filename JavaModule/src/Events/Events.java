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
     *  0-29  : StormEvent        (30%)
     *  30-59 : PerfectYieldEvent (30%)
     *  60-99 : Nothing           (40%)
     */
    public static void triggerPossibleEvent() {
        int roll = (int) (Math.random() * 100);

        Events event = null;
        if (roll < 30) {
            if (roll < 30) {
                event = new StormEvent();
            } else if (roll < 60) {
                event = new PerfectYieldEvent();
            }
        }

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
