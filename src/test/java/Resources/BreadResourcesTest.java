package Resources;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class BreadResourcesTest {
    @Test
    void testInitialValues() {
        BreadResources bread = new BreadResources();
        assertEquals("Bread", bread.getResourceTypeName());
        assertEquals(20, bread.getAmount());
    }
}
