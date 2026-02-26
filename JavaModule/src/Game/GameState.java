package Game;

import Resources.*;
import Buildings.Buildings;

/**********************************************************************
 *           Diese Klasse ist dazu da, um den Spielzustand
 *           zu verwalten und globale Instanzen von Ressourcen
 *           bereitzustellen.
 *
 ***********************************************************************/


public class GameState {
    private static final GoldResources myGold = new GoldResources();
    private static final WoodResources myWood = new WoodResources();
    private static final PopulationResources myPopulation = new PopulationResources();
    private static final BreadResources myBread = new BreadResources();
    private static final StoneResources myStone = new StoneResources();
    private static final WeedResources myWeed = new WeedResources();

    public static GoldResources getGoldInstance() { return myGold; }
    public static WoodResources getWoodInstance() { return myWood; }
    public static PopulationResources getPopulationInstance() { return myPopulation; }
    public static BreadResources getBreadInstance() { return myBread; }
    public static StoneResources getStoneInstance() { return myStone; }
    public static WeedResources getWeedInstance() { return myWeed; }

    private static String[][] currentMap = null;
    public static String[][] getCurrentMap() { return currentMap; }
    public static void setCurrentMap(String[][] map) { currentMap = map; }
    public static boolean hasMap() { return currentMap != null; }

    public static boolean kannBauen(Buildings b) {return myGold.getAmount() >= b.goldKosten && myWood.getAmount() >= b.holzKosten; }
    public static void ressourcenAbziehen(Buildings b) {myGold.subResources(b.goldKosten);myWood.subResources(b.holzKosten); }

    private static int anzahlHolzfaeller = 0;
    public static void holzfaellerHinzufuegen() { anzahlHolzfaeller++; }
    public static int getAnzahlHolzfaeller() { return anzahlHolzfaeller; }
    public static void addWood(int menge) { myWood.addResources(menge); }

    private static int anzahlFarmLand = 0;
    public static void farmLandHinzufuegen() { anzahlFarmLand++; }
    public static int getAnzahlFarmLand() { return anzahlFarmLand; }
    public static void addWeed(int menge) { myWeed.addResources(menge);}
}
