package Resources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class ResourcesTest {
    private Resources testResource;
    @BeforeEach
    void setUp() {
        testResource = new Resources("TestResource", 100) {};
    }
    @Test
    void testConstructorAndGetters() {
        assertEquals(100, testResource.getAmount());
        assertEquals("TestResource", testResource.getResourceTypeName());
    }
    @Test
    void testAddResources() {
        testResource.addResources(50);
        assertEquals(150, testResource.getAmount());
    }
    @Test
    void testSubResources() {
        testResource.subResources(30);
        assertEquals(70, testResource.getAmount());
    }
    @Test
    void testIsEmpty() {
        assertFalse(testResource.isEmpty());
        testResource.subResources(100);
        assertTrue(testResource.isEmpty());
        testResource.subResources(50);
        assertTrue(testResource.isEmpty());
    }
    @Test
    void testAffordableUnits() {
        assertEquals(5, testResource.affordableUnits(20));
        assertEquals(10, testResource.affordableUnits(10));
        assertEquals(Integer.MAX_VALUE, testResource.affordableUnits(0));
        assertEquals(Integer.MAX_VALUE, testResource.affordableUnits(-5));
    }
    @Test
    void testReduceByPercent() {
        int reduced = testResource.reduceByPercent(25);
        assertEquals(25, reduced);
        assertEquals(75, testResource.getAmount());
        reduced = testResource.reduceByPercent(50);
        assertEquals(37, reduced); // 75 * 0.5 = 37.5 -> 37
        assertEquals(38, testResource.getAmount());
        reduced = testResource.reduceByPercent(200);
        assertEquals(76, reduced); // 38 * 2.0 = 76
        assertEquals(0, testResource.getAmount());
    }
}
