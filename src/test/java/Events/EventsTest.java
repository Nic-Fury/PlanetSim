package Events;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventsTest {

    private static class TestEvent extends Events {
        private final boolean negative;

        TestEvent(String displayName, String description, boolean negative) {
            super(displayName, description);
            this.negative = negative;
        }

        @Override
        public boolean isNegativeEvent() {
            return negative;
        }

        @Override
        public void applyEvent() {
            // no-op for base class tests
        }
    }

    @Test
    void getDisplayNameReturnsValueFromConstructor() {
        Events event = new TestEvent("Test Event", "desc", false);
        assertEquals("Test Event", event.getDisplayName());
    }

    @Test
    void getDescriptionReturnsValueFromConstructor() {
        Events event = new TestEvent("Test Event", "A test description", false);
        assertEquals("A test description", event.getDescription());
    }

    @Test
    void printEventMethodsDoNotThrow() {
        Events event = new TestEvent("Test Event", "A test description", true);
        assertDoesNotThrow(event::printEventIntro);
        assertDoesNotThrow(event::printEventOutro);
    }
}

