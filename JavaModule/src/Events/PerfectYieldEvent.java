package Events;

import Buildings.FarmLand;
import Game.ActionHandler;
import Game.GameState;
import Game.IO;

public class PerfectYieldEvent extends PositiveEvents {
    public PerfectYieldEvent() {
        super("Perfect Yield", "The sun is strong and the rain is rich, resulting in a perfect yield for your crops. \n" +
                ">>Results of the Yield \n" +
                ">>You gain _50%_ more _WEED_ resources this round.");
    }

    @Override
    public void applyEvent() {
        long farmlandCount = GameState.getPlacedBuildings().stream()
                .filter(b -> b instanceof FarmLand)
                .count();

        int weedThisRound = (int) farmlandCount * FarmLand.WEED_PRO_RUNDE;
        int bonus = 0;
        if (ActionHandler.isEven(weedThisRound)) {
            bonus = weedThisRound / 2;
        } else {bonus = (weedThisRound + 1) / 2;}

        GameState.getWeedInstance().addResources(bonus);

        IO.println(">>Perfect Yield: " + farmlandCount + " FarmLand(s) produced " + weedThisRound
                + " Weed this round.\n" +
                ">>Bonus: +" + bonus + " Weed!");
    }
}
