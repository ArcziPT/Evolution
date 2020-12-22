package com.arczipt.ewolucja.gui.components;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cell extends StackPane {
    private int col;
    private int row;

    String defaultColor = "";
    String backgroundColor = "";
    String border = "";

    private Circle animalEnergy;

    public Cell(int col, int row, boolean inJungle, int w, int h){
        this.col = col;
        this.row = row;

        this.animalEnergy = new Circle();
        animalEnergy.setCenterX(w/2);
        animalEnergy.setCenterY(h/2);
        animalEnergy.setRadius(w/4);
        animalEnergy.setFill(Color.TRANSPARENT);
        getChildren().add(animalEnergy);

        if(inJungle)
            defaultColor = backgroundColor = "-fx-background-color: black;";
        else
            defaultColor = backgroundColor = "-fx-background-color: gray;";

        border = "-fx-border-width: 1; -fx-border-style: solid; -fx-border-color: white;";

        setStyle(backgroundColor + border);
    }

    public void setHighEnergy(){
        animalEnergy.setFill(Color.BROWN);
    }

    public void setMediumEnergy(){
        animalEnergy.setFill(Color.LIGHTCORAL);
    }

    public void setLowEnergy(){
        animalEnergy.setFill(Color.LIGHTPINK);
    }

    public void setDefault(){
        animalEnergy.setFill(Color.TRANSPARENT);
    }

    public void withTree(){
        backgroundColor = "-fx-background-color: green;";
        setStyle(backgroundColor + border);
    }

    public void withoutTree(){
        backgroundColor = defaultColor;
        setStyle(backgroundColor + border);
    }

    public void dominatingGenome(){
        backgroundColor = "-fx-background-color: blue;";
        setStyle(backgroundColor + border);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }
}
