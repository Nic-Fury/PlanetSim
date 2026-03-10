package Events;

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

        IO.println("DEVELOPER" + randomEventProbability);

        if (randomEventProbability < 100) {
            new StormEvent().applyEvent();
        }

    }
}
