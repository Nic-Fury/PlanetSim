package Events;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NegativeEventsTest {

    private static class TestNegativeEvent extends NegativeEvents {
        TestNegativeEvent() {
            super("Negative Test", "negative description");
        }

        @Override
        public void applyEvent() {
            // no-op for behavior tests
        }

        @Override
        public String stringASCIIArt() {
            return "";
        }
    }

    @Test
    void isNegativeEventReturnsTrue() {
        NegativeEvents event = new TestNegativeEvent();
        assertTrue(event.isNegativeEvent(), "NegativeEvents should always be negative");
    }

    @Test
    void constructorValuesAreExposedViaGetters() {
        NegativeEvents event = new TestNegativeEvent();
        assertEquals("Negative Test", event.getDisplayName());
        assertEquals("negative description", event.getDescription());
    }
}

