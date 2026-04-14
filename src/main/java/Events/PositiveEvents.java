package Events;

public abstract class PositiveEvents extends Events{

    public PositiveEvents(String displayName, String description) {
        super(displayName, description);
    }

    @Override
    public boolean isNegativeEvent() { return false; }

    @Override
    public abstract void applyEvent();
}
