package Resources;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class WorkforceResourcesTest {
    @Test
    void testInitialValues() {
        WorkforceResources workforce = new WorkforceResources();
        assertEquals("Workforce", workforce.getResourceTypeName());
        assertEquals(10, workforce.getAmount());
    }
}
