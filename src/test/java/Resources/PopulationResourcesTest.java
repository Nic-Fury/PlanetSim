package Resources;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class PopulationResourcesTest {
    @Test
    void testInitialValues() {
        PopulationResources population = new PopulationResources();
        assertEquals("Population", population.getResourceTypeName());
        assertEquals(10, population.getAmount());
    }
}
