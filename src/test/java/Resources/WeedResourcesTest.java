package Resources;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class WeedResourcesTest {
    @Test
    void testInitialValues() {
        WeedResources weed = new WeedResources();
        assertEquals("Weed", weed.getResourceTypeName());
        assertEquals(0, weed.getAmount());
    }
}
