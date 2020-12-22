package com.arczipt.ewolucja.gui;

import com.arczipt.ewolucja.gui.components.SettingsItem;
import com.arczipt.ewolucja.simulation.models.Config;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class SettingsDialog {
    private Config config;

    private Stage stage;
    private GridPane grid;

    private SettingsItem moveEnergyItem;
    private SettingsItem defaultEnergyItem;
    private SettingsItem defaultAnimalNumberItem;
    private SettingsItem defaultPlantEnergyItem;
    private SettingsItem jungleRatioItem;
    private SettingsItem defaultJunglePlantNumberItem;
    private SettingsItem defaultPlantNumberItem;
    private SettingsItem yItem;
    private SettingsItem xItem;
    private SettingsItem mapNumberItem;
    private SettingsItem refreshTimeItem;
    private SettingsItem statisticsSaveItem;

    private Button saveButton;

    private void refresh(){
        moveEnergyItem.getTextField().setText(config.getMoveEnergy().toString());
        defaultEnergyItem.getTextField().setText(config.getDefaultEnergy().toString());
        defaultPlantEnergyItem.getTextField().setText(config.getDefaultPlantEnergy().toString());
        defaultAnimalNumberItem.getTextField().setText(config.getDefaultAnimalNumber().toString());
        jungleRatioItem.getTextField().setText(config.getJungleRatio().toString());
        defaultJunglePlantNumberItem.getTextField().setText(config.getDefaultJunglePlantNumber().toString());
        defaultPlantNumberItem.getTextField().setText(config.getDefaultPlantNumber().toString());
        xItem.getTextField().setText(config.getX().toString());
        yItem.getTextField().setText(config.getY().toString());
        mapNumberItem.getTextField().setText(config.getMapNumber().toString());
        refreshTimeItem.getTextField().setText(config.getRefreshTime().toString());
        statisticsSaveItem.getTextField().setText(config.getSaveStatistics().toString());
    }

    private void save(){
        config.setMoveEnergy(Integer.valueOf(moveEnergyItem.getTextField().getText()));
        config.setDefaultEnergy(Integer.valueOf(defaultEnergyItem.getTextField().getText()));
        config.setDefaultAnimalNumber(Integer.valueOf(defaultAnimalNumberItem.getTextField().getText()));
        config.setDefaultPlantEnergy(Integer.valueOf(defaultPlantEnergyItem.getTextField().getText()));

        config.setJungleRatio(Double.valueOf(jungleRatioItem.getTextField().getText()));

        config.setDefaultJunglePlantNumber(Integer.valueOf(defaultJunglePlantNumberItem.getTextField().getText()));
        config.setDefaultPlantNumber(Integer.valueOf(defaultPlantNumberItem.getTextField().getText()));
        config.setX(Integer.valueOf(xItem.getTextField().getText()));
        config.setY(Integer.valueOf(yItem.getTextField().getText()));

        config.setMapNumber(Integer.parseInt(mapNumberItem.getTextField().getText()) >= 2 ? 2 : 1);

        config.setRefreshTime(Double.valueOf(refreshTimeItem.getTextField().getText()));
        config.setSaveStatistics(Integer.valueOf(statisticsSaveItem.getTextField().getText()));

        stage.close();
    }

    private void discard(){
        stage.close();
    }

    public SettingsDialog(Config config){
        this.config = config;

        stage = new Stage();
        stage.setTitle("Settings");

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(11, 11, 11, 11));

        moveEnergyItem = new SettingsItem("Move energy:", config.getMoveEnergy().toString());
        defaultEnergyItem = new SettingsItem("Starting energy:", config.getDefaultEnergy().toString());
        defaultPlantEnergyItem = new SettingsItem("Plant energy:", config.getDefaultPlantEnergy().toString());
        defaultAnimalNumberItem = new SettingsItem("Starting animal number:", config.getDefaultAnimalNumber().toString());
        jungleRatioItem = new SettingsItem("Jungle ratio:", config.getJungleRatio().toString());
        defaultJunglePlantNumberItem = new SettingsItem("Starting jungle plant number:", config.getDefaultJunglePlantNumber().toString());
        defaultPlantNumberItem = new SettingsItem("Starting plant number:", config.getDefaultPlantNumber().toString());
        xItem = new SettingsItem("X:", config.getX().toString());
        yItem = new SettingsItem("Y:", config.getY().toString());
        mapNumberItem = new SettingsItem("Map Number:", config.getMapNumber().toString());
        refreshTimeItem = new SettingsItem("Refresh time [s]:", config.getRefreshTime().toString());
        statisticsSaveItem = new SettingsItem("Save statistics after:", config.getSaveStatistics().toString());

        grid.add(moveEnergyItem.getLabel(), 0, 0);
        grid.add(defaultEnergyItem.getLabel(), 0, 1);
        grid.add(defaultAnimalNumberItem.getLabel(), 0, 2);
        grid.add(jungleRatioItem.getLabel(), 0, 3);
        grid.add(defaultJunglePlantNumberItem.getLabel(), 0, 4);
        grid.add(defaultPlantNumberItem.getLabel(), 0, 5);
        grid.add(defaultPlantEnergyItem.getLabel(), 0, 6);
        grid.add(xItem.getLabel(), 0, 7);
        grid.add(yItem.getLabel(), 0, 8);
        grid.add(mapNumberItem.getLabel(), 0, 9);
        grid.add(refreshTimeItem.getLabel(), 0,10);
        grid.add(statisticsSaveItem.getLabel(), 0, 11);
        grid.add(moveEnergyItem.getTextField(), 1, 0);
        grid.add(defaultEnergyItem.getTextField(), 1, 1);
        grid.add(defaultAnimalNumberItem.getTextField(), 1, 2);
        grid.add(jungleRatioItem.getTextField(), 1, 3);
        grid.add(defaultJunglePlantNumberItem.getTextField(), 1, 4);
        grid.add(defaultPlantNumberItem.getTextField(), 1, 5);
        grid.add(defaultPlantEnergyItem.getTextField(), 1, 6);
        grid.add(xItem.getTextField(), 1, 7);
        grid.add(yItem.getTextField(), 1, 8);
        grid.add(mapNumberItem.getTextField(), 1, 9);
        grid.add(refreshTimeItem.getTextField(), 1, 10);
        grid.add(statisticsSaveItem.getTextField(), 1, 11);

        saveButton = new Button();
        saveButton.setText("Save");
        saveButton.setOnAction(e -> save());

        grid.add(saveButton, 8, 0);

        Scene scene = new Scene(grid);
        stage.setScene(scene);
    }

    public void openDialog(){
        stage.showAndWait();
    }

    public Config getConfig() {
        return config;
    }
}
