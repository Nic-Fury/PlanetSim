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
        this.amount += amountToAdd;
    }

    public void subResources(int amountToSub) {
        this.amount -= amountToSub;
    }

    /**
     * Returns true if the current amount is zero or less.
     * The resource itself knows whether it is exhausted.
     */
    public boolean isEmpty() {
        return this.amount <= 0;
    }

    /**
     * Returns how many full lots of {@code costPerUnit} can be afforded
     * from the current stock.
     *
     * Example: weed.affordableUnits(2) with 5 Weed → 2
     *
     * The resource itself knows its own stock, so this query belongs here
     * (Information Expert principle).
     */
    public int affordableUnits(int costPerUnit) {
        if (costPerUnit <= 0) return Integer.MAX_VALUE;
        return this.amount / costPerUnit;
    }
}
