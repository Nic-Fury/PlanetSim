package Resources;

public class WoodResources extends Resources {
    public WoodResources(){
        super("Wood", 10);
    }

    public void addResources(int menge) { amount += menge; }
}
