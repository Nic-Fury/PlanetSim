# PlanetSim 🪐🧬🌍
_Projekt im Rahmen der Advanced SWE Vorlesung 2025/26_

Ein konsolenbasiertes Simulationsspiel, in dem der Spieler einen Planeten verwaltet und dessen Bevölkerung aufbaut und versorgt.
Dabei müssen Ressourcen, Gebäude und Arbeitskräfte sinnvoll eingesetzt werden, um das Wachstum des Planeten zu sichern.

## Cheats 🤑
Das Spiel wird über Konsoleneingaben gesteuert.
Es werden die gültigen Optionen angezeigt.
Zusätzlich gibt es einige nicht vorgeschlagene Optionen für Tests und Debugging:
- `ActionMenu`: `100` fügt einer Resource 100 Einheiten hinzu
- `BuildMenu`: `100` platziert mehrere Gebäude auf einmal, ohne Ressourcen zu verbrauchen
- `ActionMenu`: `999` zeigt das versteckte Highscore-Board aus `src/main/resources/highscores.csv`

## Projektstruktur
- Produktionscode liegt unter `src/main/java`
- Tests liegen unter `src/test/java`
- Das Projekt ist als Maven-Projekt konfiguriert

## Setup 🧑‍💻
- Repository klonen: `https://github.com/Nic-Fury/PlanetSim`
- In IntelliJ das Projekt als Maven-Projekt öffnen oder neu importieren
- Java SDK 25 verwenden

## Testen
Die Tests werden über Maven bei jedem Push auf `Main` ausgeführt.
