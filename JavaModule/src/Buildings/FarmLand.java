package Buildings;

import Game.GameState;
import Game.Gameboard;

public class FarmLand extends IndustryBuildings{


    public void build(int x, int y) {
        GameState.getGoldInstance().deductResources(0);
        GameState.getWoodInstance().deductResources(0);
        Gameboard.printSingleColorBlockAtCoordinates("BLANC", x, y);
    }
}
