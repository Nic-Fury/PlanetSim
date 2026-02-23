package Game;

import Resources.GoldResources;
import Resources.WoodResources;

/**********************************************************************
 *           Diese Klasse ist dazu da, um den Spielzustand
 *           zu verwalten und globale Instanzen von Ressourcen
 *           bereitzustellen.
 *
 ***********************************************************************/


public class GameState {
    private static final GoldResources myGold = new GoldResources();
    private static final WoodResources myWood = new WoodResources();
    private static String[][] currentMap = null;

    public static GoldResources getGoldInstance() { return myGold; }
    public static WoodResources getWoodInstance() { return myWood; }

    public static String[][] getCurrentMap() { return currentMap; }
    public static void setCurrentMap(String[][] map) { currentMap = map; }
    public static boolean hasMap() { return currentMap != null; }

//    public static void deductGold(int amount) {
//        myGold.setAmount(myGold.getAmount() - amount);
//    }
//
//    public static void deductWood(int amount) {
//        myWood.setAmount(myWood.getAmount() - amount);
//    }
}
