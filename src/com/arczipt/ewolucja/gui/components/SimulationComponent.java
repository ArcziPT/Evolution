package com.arczipt.ewolucja.gui.components;

import com.arczipt.ewolucja.simulation.*;
import com.arczipt.ewolucja.simulation.models.Animal;
import com.arczipt.ewolucja.simulation.models.Config;
import com.arczipt.ewolucja.simulation.models.Genome;
import com.arczipt.ewolucja.simulation.stat.AnimalStatistics;
import com.arczipt.ewolucja.simulation.utils.Vector2D;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.*;

public class SimulationComponent {
    private Simulation simulation;
    private Config config;

    private BorderPane layout;
    private CurrentStatisticsComponent currentStatisticsComponent;
    private AnimalStatisticsComponent animalStatisticsComponent;
    private GridMap gridMap;

    private HBox controlMenu;
    private Button startButton;
    private Button stopButton;

    private MouseHandler mouseHandler;

    private boolean isRunning = false;

    public SimulationComponent(Config config) {
        this.config = config;

        simulation = new Simulation(config);
        currentStatisticsComponent = new CurrentStatisticsComponent(simulation.getCurrentStatistics(), this);
        animalStatisticsComponent = new AnimalStatisticsComponent();
        mouseHandler = new MouseHandler(this);

        layout = new BorderPane();
        layout.setLeft(currentStatisticsComponent.getLayout());

        layout.prefHeight(500);
        layout.prefWidth(1100);

        gridMap = new GridMap(config.getY(), config.getX(), 500, 500);

        int jungleH = (int) (config.getY() * config.getJungleRatio());
        int jungleW = (int) (config.getX() * config.getJungleRatio());
        int jungleX = (config.getX() - jungleW) / 2;
        int jungleY = (config.getY() - jungleH) / 2;
        for (int i = 0; i < config.getX(); i++) {
            for (int j = 0; j < config.getY(); j++) {
                boolean inJungle = (i >= jungleX && i < jungleX + jungleW) && (j >= jungleY && j < jungleY + jungleH);

                Cell cell = new Cell(i, j, inJungle, 500/config.getX(), 500/config.getY());
                mouseHandler.makePaintable(cell);
                gridMap.addCell(cell, j, i);
            }
        }
        layout.setCenter(gridMap);


        //BUTTONS
        startButton = new Button("Start");
        startButton.setOnAction(e -> {
            start();
        });

        stopButton = new Button("Stop");
        stopButton.setOnAction(e -> {
            isRunning = false;
        });

        controlMenu = new HBox();
        controlMenu.getChildren().addAll(startButton, stopButton);
        layout.setBottom(controlMenu);

        layout.setRight(animalStatisticsComponent.getLayout());
    }

    public void start() {
        isRunning = true;

        if(!simulation.hasAnimals()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Simulation has already finished!");
            alert.showAndWait();
            return;
        }

        new Thread(() -> {
            while (isRunning) {
                try {
                    Thread.sleep((long) (config.getRefreshTime() *  1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(!simulation.step()) {
                    Platform.runLater(() -> {
                        this.refresh();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Simulation finished!");
                        alert.showAndWait();
                    });
                    return;
                }
                Platform.runLater(this::refresh);
            }


        }).start();
    }

    public void refresh() {
        currentStatisticsComponent.refresh();
        animalStatisticsComponent.refresh();

        int[][] energies = simulation.getEnergy();
        boolean[][] hasPlant = simulation.hasPlant();

        for (int x = 0; x < config.getX(); x++) {
            for (int y = 0; y < config.getY(); y++) {
                Cell cell = gridMap.get(x, y);

                if (energies[y][x] > config.getDefaultEnergy() * 1.3)
                    cell.setHighEnergy();
                else if (energies[y][x] > config.getDefaultEnergy() * 0.7)
                    cell.setMediumEnergy();
                else if (energies[y][x] > 0)
                    cell.setLowEnergy();
                else
                    cell.setDefault();

                if(hasPlant[y][x])
                    cell.withTree();
                else
                    cell.withoutTree();
            }
        }
    }

    public void showAnimalsWithMostCommonGenomes(Map<Genome, Integer> genomes){
        Optional<Integer> max = genomes.values().stream().max((v1, v2) -> v1 - v2);
        if(max.isEmpty())
            return;

        simulation.getAnimals().filter(a -> genomes.getOrDefault(a.getGenome(), 0).equals(max.get()))
                .map(Animal::getPosition).distinct().forEach(v -> gridMap.get(v.getX(), v.getY()).dominatingGenome());
    }

    public void setObservedAnimal(AnimalStatistics animalStatistics){
        animalStatisticsComponent.setAnimalStatistics(animalStatistics);
        refresh();
    }

    public BorderPane getLayout() {
        return layout;
    }

    private class MouseHandler {
        private SimulationComponent simulationComponent;

        public MouseHandler(SimulationComponent simulationComponent){
            this.simulationComponent = simulationComponent;
        }

        public void makePaintable(Node node) {
            node.setOnMousePressed(onMousePressedHandler);
        }

        private EventHandler<MouseEvent> onMousePressedHandler = e -> {
            Cell cell = (Cell) e.getSource();

            if(e.isPrimaryButtonDown()) {
                try {
                    Vector2D pos = new Vector2D(cell.getCol(), cell.getRow());
                    AnimalStatistics animalStatistics = simulation.observe(pos);

                    if(animalStatistics == null)
                        return;

                    simulationComponent.setObservedAnimal(animalStatistics);
                }catch (IllegalStateException exception){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText(exception.getMessage());
                    alert.showAndWait();
                }
            }
        };
    }
}
