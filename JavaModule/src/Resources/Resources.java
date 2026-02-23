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

    // Method to add/substract resources
    public void addResources(int amountToAdd) {
        this.amount += amountToAdd;
    }

    // Methode to deduct resources
    public void deductResources(int amountToDeduct) {
        if (amountToDeduct > this.amount) {
            throw new IllegalArgumentException("Not enough resources to deduct.");
        }
        this.amount -= amountToDeduct;
    }

}
