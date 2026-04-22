package Resources;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class StoneResourcesTest {
    @Test
    void testInitialValues() {
        StoneResources stone = new StoneResources();
        assertEquals("Stone", stone.getResourceTypeName());
        assertEquals(10, stone.getAmount());
    }
}
