package com.arczipt.ewolucja.gui;

import com.arczipt.ewolucja.gui.components.SimulationComponent;
import com.arczipt.ewolucja.simulation.models.Config;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main extends Application {
    private Config config;

    private ArrayList<SimulationComponent> simulationComponents = new ArrayList<>();

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Path filename = Path.of("parameters.json");

        Config config = new Config();
        try {
            String json = Files.readString(filename);

            JSONObject jsonObject = new JSONObject(json);
            config.setX(jsonObject.getInt("width"));
            config.setY(jsonObject.getInt("height"));
            config.setDefaultEnergy(jsonObject.getInt("startEnergy"));
            config.setMoveEnergy(jsonObject.getInt("moveEnergy"));
            config.setDefaultPlantEnergy(jsonObject.getInt("plantEnergy"));
            config.setJungleRatio(jsonObject.getDouble("jungleRatio"));
        } catch (IOException ignored) {
        }

        SettingsDialog settingsDialog = new SettingsDialog(config);
        settingsDialog.openDialog();

        VBox layout = new VBox();
        for(int i=0; i<config.getMapNumber(); i++){
            SimulationComponent simulationComponent = new SimulationComponent(config);
            simulationComponents.add(simulationComponent);
            layout.getChildren().add(simulationComponent.getLayout());
        }

        Scene scene = new Scene(layout);
        stage.setScene(scene);
        stage.setMinHeight(config.getMapNumber() * 500);
        stage.setMinWidth(1100);
        stage.show();
    }
}
