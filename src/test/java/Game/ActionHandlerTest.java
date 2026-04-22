package Game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ActionHandlerTest {

    @Test
    void executeActionReturnsContinueForDoNothingAndInvalid() {
        assertEquals(ActionHandler.ActionFlow.CONTINUE_ROUND, ActionHandler.executeAction(2));
        assertEquals(ActionHandler.ActionFlow.CONTINUE_ROUND, ActionHandler.executeAction(-1));
    }

    @Test
    void executeActionReturnsEndRoundForZero() {
        assertEquals(ActionHandler.ActionFlow.END_ROUND, ActionHandler.executeAction(0));
    }

    @Test
    void isEvenDetectsParity() {
        assertTrue(ActionHandler.isEven(8));
        assertFalse(ActionHandler.isEven(7));
    }
}

