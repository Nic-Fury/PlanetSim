package Events;

public class PerfectYieldEvent extends PositiveEvents {
    public PerfectYieldEvent() {
        super("Perfect Yield", "Your crops have yielded a perfect harvest, providing an abundance of food for your population.");
    }

    @Override
    public void applyEvent() {
        // Implement the logic to increase food resources for the player
        // For example, you could add a certain amount of food to the player's resources
        // GameState.getFoodInstance().add(100); // Example: Add 100 units of food
    }
}
