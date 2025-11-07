/**********************************************************************
 *           Diese Klasse ist dazu da, um das ründliche
 *           AktionsMenü dazustellen und zu verwalten
 *
 *          +----------------------------------+
 *          | Gold: x  |  Holz: x  |  Stein: x |
 *          +----------------------------------+
 *          |	Waehle eine Action       	   |
 *          |       	        			   |
 *          |  1. Runde beenden		           |
 *          |  2. Meine Resourcen              |
 *          |  3. Bauen                        |
 *          |                                  |
 *          |  S. Speichern                    |
 *          |  B. Beenden                      |
 *          |                                  |
 *          +----------------------------------+
 *
 ***********************************************************************/


public class ActionMenu {


    public static void runActionMenu(){
        printResources();
        printActions();
    }


    public static void printResources(){
        IO.println("+----------------------------------+");
        IO.println("| Gold: x  |  Holz: x  |  Stein: x |");
        IO.println("+----------------------------------+");
    }

    public static void printActions(){

    }


}
