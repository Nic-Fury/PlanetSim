package Resources;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class GoldResourcesTest {
    @Test
    void testInitialValues() {
        GoldResources gold = new GoldResources();
        assertEquals("Gold", gold.getResourceTypeName());
        assertEquals(100, gold.getAmount());
    }
}
