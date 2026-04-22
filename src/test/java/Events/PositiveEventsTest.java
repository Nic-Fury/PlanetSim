package Events;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositiveEventsTest {

    private static class TestPositiveEvent extends PositiveEvents {
        TestPositiveEvent() {
            super("Positive Test", "positive description");
        }

        @Override
        public void applyEvent() {
            // no-op for behavior tests
        }
    }

    @Test
    void isNegativeEventReturnsFalse() {
        PositiveEvents event = new TestPositiveEvent();
        assertFalse(event.isNegativeEvent(), "PositiveEvents should never be negative");
    }

    @Test
    void constructorValuesAreExposedViaGetters() {
        PositiveEvents event = new TestPositiveEvent();
        assertEquals("Positive Test", event.getDisplayName());
        assertEquals("positive description", event.getDescription());
    }
}

