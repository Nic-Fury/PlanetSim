package Game;

import Buildings.Buildings;
import Resources.Resources;
import Events.Events;

public class Round {

    public static int startFirstRound(int roundCounterInt){
        //Welcome message
        IO.println();
        IO.println();
        IO.printSlowByChar(String.format("Welcome to Planet Sim!"));
        IO.println();

        //Planet name
        String chosenPlanetName = ActionMenu.readPlanetName();
        int chosenMapSizeInt = ActionMenu.readMapSize();

        startRound(chosenMapSizeInt, roundCounterInt);
        return chosenMapSizeInt;
    }

    public static void startRound(int chosenMapSizeInt, int roundCounterInt){
        updateResources();
        checkForWinningCondition(roundCounterInt);
        ActionMenu.printDayInfo(roundCounterInt);
        Gameboard.printPlanet(chosenMapSizeInt);
        ActionMenu.printActionMenu(roundCounterInt);
        Events.triggerPossibleEvent();
    }

    private static void checkForWinningCondition(int roundCounterInt) {
        // --- Winning Condition: Population reaches 100 ---
        if (GameState.getPopulationInstance().getAmount() >= 100) {
            IO.println();
            IO.printSlowByChar("Congratulations! You've reached a population of 100 and won the game!");
            IO.println("It took you " + roundCounterInt + " days to achieve this milestone.");
            System.exit(0);
//            return true;
        }
//        return false;
    }



    /**
     * Delegates production to each placed building.
     * Following the Information Expert principle, every building knows
     * what it produces and consumes – this method just triggers them all generically.
     * Adding a new building type requires zero changes here.
     */
    private static void updateResources() {

        // --- Arbeitskraft-Bedarf aller platzierten Gebäude summieren ---
        int totalWorkforceRequired = GameState.getPlacedBuildings().stream()
                .mapToInt(Buildings::getWorkforceRequired)
                .sum();

        for (Buildings building : GameState.getPlacedBuildings()) {

            // --- Population durch Wohngebäude generieren ---
            if (building.getPopulationPerRound() > 0) {
                Resources popFood = building.getPopulationConsumedResource();
                int popCost      = building.getPopulationConsumptionPerUnit();

                if (popFood != null && popCost > 0) {
                    // Nur produzieren wenn genug Bread vorhanden
                    if (popFood.affordableUnits(popCost) >= building.getPopulationPerRound()) {
                        popFood.subResources(building.getPopulationPerRound() * popCost);
                        GameState.getPopulationInstance().addResources(building.getPopulationPerRound());
                        java.lang.IO.println(building.displayName.trim() + " generated "
                                + building.getPopulationPerRound() + " "
                                + GameState.getPopulationInstance().getResourceTypeName()
                                + " (consumed " + (building.getPopulationPerRound() * popCost)
                                + " " + popFood.getResourceTypeName() + ")");
                    } else {
                        java.lang.IO.println(building.displayName.trim()
                                + " could not generate Population – not enough "
                                + popFood.getResourceTypeName() + "!");
                    }
                } else {
                    // Kein Verbrauch nötig – direkt produzieren
                    GameState.getPopulationInstance().addResources(building.getPopulationPerRound());
                    java.lang.IO.println(building.displayName.trim() + " generated "
                            + building.getPopulationPerRound() + " "
                            + GameState.getPopulationInstance().getResourceTypeName());
                }
            }

            // --- Ressourcen-Produktion  ---
            if (building.getProducedResource() == null || building.getProductionPerRound() <= 0) continue;

            int produced = building.produceResources();

            if (produced > 0) {
                String msg = building.displayName.trim() + " produced " + produced
                        + " " + building.getProducedResource().getResourceTypeName();
                if (building.getConsumedResource() != null && building.getConsumptionPerUnit() > 0) {
                    msg += " (consumed " + (produced * building.getConsumptionPerUnit())
                            + " " + building.getConsumedResource().getResourceTypeName() + ")";
                }
                java.lang.IO.println(msg);
            } else {
                java.lang.IO.println(building.displayName.trim() + " could not produce "
                        + building.getProducedResource().getResourceTypeName()
                        + " – not enough "
                        + (building.getConsumedResource() != null
                        ? building.getConsumedResource().getResourceTypeName()
                        : "resources") + "!");
            }
        }

        // --- Workforce synchronisieren: Population − benötigte Arbeitskraft ---
        int availableWorkforce = GameState.getPopulationInstance().getAmount() - totalWorkforceRequired;
        int currentWorkforce   = GameState.getWorkforceInstance().getAmount();
        GameState.getWorkforceInstance().addResources(availableWorkforce - currentWorkforce);

        // --- Log: Arbeitskraft-Status --- (optional)
        /*
        java.lang.IO.println("Workforce available: " + GameState.getWorkforceInstance().getAmount()
                + " (Population: " + GameState.getPopulationInstance().getAmount()
                + " | Required: " + totalWorkforceRequired + ")");
        */
    }

}
