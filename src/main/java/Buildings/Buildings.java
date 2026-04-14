package Buildings;

import Game.IO;
import Resources.Resources;
import java.util.Set;

public abstract class Buildings {
    public final String displayName;
    public final String buildingSymbolColor;
    public final int goldKosten;
    public final int holzKosten;
    public final int steinKosten;

    public int x = -1;
    public int y = -1;

    // 5-Parameter Konstruktor (bestehender)
    public Buildings(String displayName, String buildingSymbolColor, int goldKosten, int holzKosten, int steinKosten) {
        this.displayName  = displayName;
        this.buildingSymbolColor = buildingSymbolColor;
        this.goldKosten   = goldKosten;
        this.holzKosten   = holzKosten;
        this.steinKosten  = steinKosten;
    }

    // Neuer 4-Parameter Überladungs-Konstruktor für Rückwärtskompatibilität
    // Setzt steinKosten standardmäßig auf 0.
    public Buildings(String displayName, String buildingSymbolColor, int goldKosten, int holzKosten) {
        this(displayName, buildingSymbolColor, goldKosten, holzKosten, 0);
    }

    // ... restlicher Code unverändert ...
    public abstract Resources getProducedResource();
    public abstract int getProductionPerRound();

    public Resources getConsumedResource() { return null; }
    public int getConsumptionPerUnit() { return 0; }
    public Set<String> getAllowedBiomes() { return null; }

    public int produceResources() {
        Resources produced = getProducedResource();
        if (produced == null || getProductionPerRound() <= 0) return 0;

        int units = getProductionPerRound();
        Resources consumed = getConsumedResource();
        if (consumed != null && getConsumptionPerUnit() > 0) {
            int affordable = consumed.affordableUnits(getConsumptionPerUnit());
            units = Math.min(units, affordable);
            if (units <= 0) return 0;
            consumed.subResources(units * getConsumptionPerUnit());
        }
        produced.addResources(units);
        return units;
    }

    public int getWorkforceRequired() { return 0; }

    public void printInfo() {
        IO.println("| " + displayName
                + " | Gold: " + goldKosten
                + " | Holz: " + holzKosten
                + " | Stein: " + steinKosten + "  |");
    }
}
