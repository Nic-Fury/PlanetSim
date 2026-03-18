package Game;

import Buildings.Buildings;
import Resources.Resources;
import Events.Events;
import java.util.HashMap;
import java.util.Map;

public class Round {

    public static int startFirstRound(int roundCounterInt){
        //Welcome message
        IO.println();
        IO.println();
        IO.printlnSlowByChar(100,">> Welcome to PlanetSim!");
        IO.println();

        //Planet name
        String chosenPlanetName = ActionMenu.readPlanetName();
        int chosenMapSizeInt = ActionMenu.readMapSize();

        startRound(chosenMapSizeInt, roundCounterInt);
        return chosenMapSizeInt;
    }

    public static void startRound(int chosenMapSizeInt, int roundCounterInt){
        checkForWinningCondition(roundCounterInt);
        ActionMenu.printDayInfo(roundCounterInt);
        printResourceUpdate(updateResources()); //updateResources() handles the logic of resource production and workforce synchronization, returns a map of what was produced this round for printing in printResourceUpdate()
        Gameboard.printPlanet(chosenMapSizeInt);
        ActionMenu.printActionMenu(roundCounterInt);
        Events.triggerPossibleEvent();
    }

    private static void checkForWinningCondition(int roundCounterInt) {
        // --- Winning Condition: Population reaches 100 ---
        if (GameState.getPopulationInstance().getAmount() >= 100) {
            IO.println();
            IO.printlnSlowByChar("Congratulations! You've reached a population of 100 and won the game!");
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
    private static Map<String, Integer> updateResources() {

        // --- Arbeitskraft-Bedarf aller platzierten Gebäude summieren ---
        int totalWorkforceRequired = GameState.getPlacedBuildings().stream()
                .mapToInt(Buildings::getWorkforceRequired)
                .sum();

        // Aggregiert produzierte Ressourcen dieser Runde (z. B. wood -> 8)
        Map<String, Integer> producedThisRound = new HashMap<>();

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
                    } else {
                        java.lang.IO.println(building.displayName.trim()
                                + " could not generate Population - not enough "
                                + popFood.getResourceTypeName() + "!");
                    }
                } else {
                    // Kein Verbrauch noetig - direkt produzieren
                    GameState.getPopulationInstance().addResources(building.getPopulationPerRound());
                }
            }

            // --- Ressourcen-Produktion  ---
            if (building.getProducedResource() == null || building.getProductionPerRound() <= 0) continue;

            int produced = building.produceResources();

            if (produced > 0) {
                String resourceName = building.getProducedResource().getResourceTypeName();
                producedThisRound.merge(resourceName, produced, Integer::sum);
            } else {
                java.lang.IO.println(building.displayName.trim() + " could not produce "
                        + building.getProducedResource().getResourceTypeName()
                        + " - not enough "
                        + (building.getConsumedResource() != null
                        ? building.getConsumedResource().getResourceTypeName()
                        : "resources") + "!");
            }
        }

        // --- Workforce synchronisieren: Population - benoetigte Arbeitskraft ---
        int availableWorkforce = GameState.getPopulationInstance().getAmount() - totalWorkforceRequired;
        int currentWorkforce   = GameState.getWorkforceInstance().getAmount();
        GameState.getWorkforceInstance().addResources(availableWorkforce - currentWorkforce);

        return producedThisRound;

        // --- Log: Arbeitskraft-Status --- (optional)
        /*
        java.lang.IO.println("Workforce available: " + GameState.getWorkforceInstance().getAmount()
                + " (Population: " + GameState.getPopulationInstance().getAmount()
                + " | Required: " + totalWorkforceRequired + ")");
        */
    }

    private static void printResourceUpdate(Map<String, Integer> producedThisRound) {
        for (Map.Entry<String, Integer> entry : producedThisRound.entrySet()) {
            IO.println(">> Today " + entry.getValue() + " " + entry.getKey() + " have been produced");
        }
    }

}
