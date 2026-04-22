package Resources;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class WoodResourcesTest {
    @Test
    void testInitialValues() {
        WoodResources wood = new WoodResources();
        assertEquals("Wood", wood.getResourceTypeName());
        assertEquals(100, wood.getAmount());
    }
}
