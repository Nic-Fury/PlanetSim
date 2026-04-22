package Resources;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class FoodResourcesTest {
    @Test
    void testFoodResourcesConstructor() {
        FoodResources food = new FoodResources("TestFood", 50) {};
        assertEquals("TestFood", food.getResourceTypeName());
        assertEquals(50, food.getAmount());
    }
}
