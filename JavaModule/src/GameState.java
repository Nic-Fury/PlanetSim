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

    public static GoldResources getGoldInstance() { return myGold; }
    public static WoodResources getWoodInstance() { return myWood; }
}
