package Resources;

public abstract class Resources {

    // Amount of resource in storage; Default needs to be set when subclassed
    protected int amount;

    // Resource type
    protected String resourceType;

    // Constructor
    public Resources(String resourceType, int amount) {
        this.amount = amount;
        this.resourceType = resourceType;
    }

    // Getter for amount
    public int getAmount() {
        return amount;
    }

    // Getter for resource type
    public String getResourceTypeName() {
        return resourceType;
    }

    // Method to add/subtract resources
    public void addResources(int amountToAdd) {
        amount += amountToAdd;
    }

    public void subResources(int amountToSub) {
        amount -= amountToSub;
    }

    /**
     * Returns true if the current amount is zero or less.
     * The resource itself knows whether it is exhausted.
     */
    public boolean isEmpty() {
        return amount <= 0;
    }

    /**
     * Returns how many full units of {@code costPerUnit} can be afforded
     * from the current stock. Example: affordableUnits(2) with 5 → 2.
     * Information Expert: the resource knows its own stock.
     */
    public int affordableUnits(int costPerUnit) {
        if (costPerUnit <= 0) return Integer.MAX_VALUE;
        return amount / costPerUnit;
    }

    /**
     * Reduces the current amount by the given percentage (0-100).
     * The reduction is rounded down. The amount will never go below 0.
     * Example: reduceByPercent(25) with amount=100 → amount becomes 75.
     *
     * @param percent value between 0 and 100
     * @return the amount that was actually subtracted
     */
    public int reduceByPercent(int percent) {
        int reduction = (int) (amount * (percent / 100.0));
        amount = Math.max(0, amount - reduction);
        return reduction;
    }
}
