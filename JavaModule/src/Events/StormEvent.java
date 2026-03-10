package Events;

public class StormEvent extends NegativeEvents {

    public StormEvent() {
        super("Storm", "A storm has hit your lands, lets see what damage it has caused!");
    }

    @Override
    public void applyEvent() {
        int randomeStormChance = (int) (Math.random() * 100); // Random number between 0 and 99
        System.out.println("__DEVELOPER__: Storm Chance is " + randomeStormChance);

        if (randomeStormChance < 10) {
            System.out.println("You are very lucky! The storm passed by without causing any damage");
        } else if (randomeStormChance < 50) {
            System.out.println("The storm caused some damage to your crops, but it is not too bad. You lost _25%_ of your _WEED_ resources.");
            // Code to reduce food resources by 25%
        } else if (randomeStormChance < 90) {
            System.out.println("The storm was devastating! You lost _80%_ of your _WEED_ resources.");
            // Code to reduce food resources by 80%
        } else if (randomeStormChance < 100) {
            System.out.println("It was the storm of the century! You lost _100%_ of your _WEED_ resources and _ALL_ your _FARMLAND_ is destroyed.");
            // Code to reduce food resources by 100%
        } else {
            System.out.println("ERROR: Storm Chance is out of bounds. This should never happen.");
            // Code to reduce food resources by 100% and population by 50%
        }

        System.out.println("############ END OF STORM EVENT ############");
    }
}
