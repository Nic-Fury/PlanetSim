package Events;

import Buildings.FarmLand;
import Game.GameState;
import Game.IO;

public class StormEvent extends NegativeEvents {

    public StormEvent() {
        super("Storm", "A storm has hit your lands, lets see what damage it has caused!");
    }

    @Override
    public void applyEvent() {
        int roll = (int) (Math.random() * 100);

        IO.printlnSlowByChar(">>Results of the storm: ");

        if (roll < 10) {
            // 10% – no damage
            IO.printlnSlowByChar("You are very lucky! The storm passed by without causing any damage.");

        } else if (roll < 50) {
            // 40% – lose 25% of Weed
            int lost = GameState.getWeedInstance().reduceByPercent(25);
            IO.printlnSlowByChar("The storm caused some damage to your crops, but it is not too bad.");
            IO.printlnSlowByChar("You lost 25% of your WEED resources. (-" + lost + " Weed)");

        } else if (roll < 90) {
            // 40% – lose 80% of Weed
            int lost = GameState.getWeedInstance().reduceByPercent(80);
            IO.printlnSlowByChar("The storm was devastating! You lost 80% of your WEED resources. (-" + lost + " Weed)");

        } else {
            // 10% – lose ALL Weed and destroy ALL FarmLands
            int lost = GameState.getWeedInstance().getAmount();
            GameState.getWeedInstance().reduceByPercent(100);

            // Collect FarmLands first to avoid ConcurrentModificationException
            java.util.List<Buildings.Buildings> farmlands = GameState.getPlacedBuildings().stream()
                    .filter(b -> b instanceof FarmLand)
                    .toList();

            for (Buildings.Buildings farm : farmlands) {
                GameState.removeBuilding(farm);
            }

            IO.printlnSlowByChar("It was the storm of the century!");
            IO.printlnSlowByChar("You lost ALL your WEED resources (-" + lost + " Weed)");
            IO.printlnSlowByChar("and ALL " + farmlands.size() + " FarmLand(s) have been destroyed!");
        }
    }
}
