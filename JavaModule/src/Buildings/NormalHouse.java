package Buildings;

import Game.GameState;
import Game.Gameboard;
import Resources.GoldResources;
import Resources.WoodResources;

public class NormalHouse extends ResidentialBuildings {

    private static final int GOLD_COST = 10;
    private static final int WOOD_COST = 1;

    @Override
    public void build(int x, int y) {
        GameState.getGoldInstance().deductResources(GOLD_COST);
        GameState.getWoodInstance().deductResources(WOOD_COST);
        Gameboard.printSingleColorBlockAtCoordinates("BLUE", x, y);
    }
}
