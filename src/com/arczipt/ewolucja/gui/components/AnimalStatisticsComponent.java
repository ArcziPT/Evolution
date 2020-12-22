package com.arczipt.ewolucja.gui.components;

import com.arczipt.ewolucja.simulation.stat.AnimalStatistics;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class AnimalStatisticsComponent {
    private AnimalStatistics animalStatistics = null;

    private VBox layout;

    private Text childrenNumberLabel;
    private Text descendantNumberLabel;
    private Text positionLabel;
    private Text energyLabel;
    private Text genomeLabel;
    private Text ageLabel;
    private Text deathLabel;

    private Button unobserveButton;

    public AnimalStatisticsComponent(){
        layout = new VBox();

        layout.prefWidth(300);
        layout.prefHeight(400);
        layout.setMaxWidth(300);
        layout.setMaxHeight(400);
        layout.setAlignment(Pos.TOP_LEFT);

        childrenNumberLabel = new Text("Children number: -");
        descendantNumberLabel = new Text("Descendant number: -");
        positionLabel = new Text("Position: -");

        energyLabel = new Text("Energy: -");
        genomeLabel = new Text("Genome: -");
        ageLabel = new Text("Age: -");
        deathLabel = new Text("Death: -");

        unobserveButton = new Button("Unobserve");
        unobserveButton.setOnAction(e -> {
            if(animalStatistics == null)
                return;

            animalStatistics.unobserve();
            animalStatistics = null;

            childrenNumberLabel.setText("Children number: -");
            descendantNumberLabel.setText("Descendant number: -");
            positionLabel.setText("Position: -");

            energyLabel.setText("Energy: -");
            genomeLabel.setText("Genome: -");
            ageLabel.setText("Age: -");
            deathLabel.setText("Death: -");
        });

        layout.getChildren().addAll(childrenNumberLabel, descendantNumberLabel, positionLabel, energyLabel, genomeLabel, ageLabel, deathLabel, unobserveButton);
    }

    public void refresh(){
        if(animalStatistics == null)
            return;

        childrenNumberLabel.setText("Children number: " + animalStatistics.getChildNumber());
        descendantNumberLabel.setText("Descendant number: " + animalStatistics.getDescendantNumber());
        positionLabel.setText("Position : " + animalStatistics.getAnimal().getPosition());

        energyLabel.setText("Energy: " + animalStatistics.getAnimal().getEnergy());
        genomeLabel.setText("Genome: " + animalStatistics.getAnimal().getGenome());
        ageLabel.setText("Age: " + animalStatistics.getAnimal().getAge());
        deathLabel.setText("Death: " + ((animalStatistics.getAnimal().getDeathEpoch() < 0) ? "-" : animalStatistics.getAnimal().getDeathEpoch()));
    }

    public void setAnimalStatistics(AnimalStatistics animalStatistics) {
        this.animalStatistics = animalStatistics;
    }

    public VBox getLayout(){
        return layout;
    }
}
