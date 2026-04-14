package Events;

import Buildings.IndustryBuildings;

public abstract class NegativeEvents extends Events {

    public NegativeEvents(String displayName, String description) {
        super(displayName, description);
    }

    @Override
    public boolean isNegativeEvent() { return true; }

    @Override
    public abstract void applyEvent();
}
