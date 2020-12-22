package com.arczipt.ewolucja.gui.components;

import com.arczipt.ewolucja.simulation.stat.CurrentStatistics;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CurrentStatisticsComponent {
    private CurrentStatistics currentStatistics;
    private SimulationComponent simulationComponent;

    private VBox layout;

    private Text infoLabel;

    private Text animalNumberLabel;
    private Text plantNumberLabel;
    private Text avgDeathAgeLabel;
    private Text deadAnimalNumberLabel;
    private Text avgEnergyLabel;
    private Text avgChildrenNumberLabel;

    private Text genomesLabel;
    private Button showButton;

    public CurrentStatisticsComponent(CurrentStatistics currentStatistics, SimulationComponent simulationComponent){
        this.simulationComponent = simulationComponent;
        this.currentStatistics = currentStatistics;

        layout = new VBox();
        layout.prefHeight(400);
        layout.prefWidth(300);

        infoLabel = new Text("Statistics for CURRENT epoch:");
        animalNumberLabel = new Text("Animal number: " + String.valueOf(currentStatistics.getAnimalsNumber()));
        plantNumberLabel = new Text("Plant number: " + String.valueOf(currentStatistics.getPlantsNumber()));
        avgDeathAgeLabel = new Text("Average death age: " + String.valueOf(currentStatistics.getAvgDeathAge()));
        deadAnimalNumberLabel = new Text("Dead animal number: " + String.valueOf(currentStatistics.getDeadAnimalsNumber()));
        avgEnergyLabel = new Text("Average energy: " + String.valueOf(currentStatistics.getAvgEnergy()));
        avgChildrenNumberLabel = new Text("Average children number: " + String.valueOf(currentStatistics.getAvgChildrenNumber()));
        genomesLabel = new Text("Most common genomes: " + currentStatistics.getMostCommonGenomes());
        genomesLabel.setWrappingWidth(300);
        showButton = new Button("Show");
        showButton.setOnAction(e -> simulationComponent.showAnimalsWithMostCommonGenomes(currentStatistics.getGenomes()));

        layout.getChildren().addAll(infoLabel, animalNumberLabel, plantNumberLabel, avgChildrenNumberLabel, avgDeathAgeLabel, avgEnergyLabel, deadAnimalNumberLabel, genomesLabel, showButton);
    }

    public void refresh(){
        animalNumberLabel.setText("Animal number: " + String.valueOf(currentStatistics.getAnimalsNumber()));
        plantNumberLabel.setText("Plant number: " + String.valueOf(currentStatistics.getPlantsNumber()));
        avgDeathAgeLabel.setText("Average death age: " + String.valueOf(currentStatistics.getAvgDeathAge()));
        deadAnimalNumberLabel.setText("Dead animal number: " + String.valueOf(currentStatistics.getDeadAnimalsNumber()));
        avgEnergyLabel.setText("Average energy: " + String.valueOf(currentStatistics.getAvgEnergy()));
        avgChildrenNumberLabel.setText("Average children number: " + String.valueOf(currentStatistics.getAvgChildrenNumber()));
        genomesLabel.setText("Most common genomes: " + currentStatistics.getMostCommonGenomes());
    }

    public VBox getLayout(){
        return layout;
    }
}
