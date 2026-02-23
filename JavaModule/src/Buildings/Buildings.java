package Buildings;

public abstract class Buildings {
    public final String displayName;
    public final String color;
    public final int goldKosten;
    public final int holzKosten;

    public Buildings(String displayName, String color, int goldKosten, int holzKosten) {
        this.displayName  = displayName;
        this.color        = color;
        this.goldKosten   = goldKosten;
        this.holzKosten   = holzKosten;
    }

    public void printInfo() {
        IO.println("| " + displayName
                + " | Gold: " + goldKosten
                + " | Holz: " + holzKosten + "  |");
    }
}
