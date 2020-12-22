package com.arczipt.ewolucja.gui.components;

import javafx.scene.layout.Pane;

public class GridMap extends Pane {
    private int rows;
    private int cols;
    private int w;
    private int h;

    Cell cells[][];

    public GridMap(int rows, int cols, int w, int h){
        this.rows = rows;
        this.cols = cols;
        this.h = h;
        this.w = w;

        cells = new Cell[rows][cols];
    }

    public void addCell(Cell cell, int r, int c){
        cells[r][c] = cell;

        double _w = w / cols;
        double _h = h / rows;
        double _x = _w * c;
        double _y = _h * r;

        cell.setLayoutX(_x);
        cell.setLayoutY(_y);
        cell.setPrefWidth(_w);
        cell.setPrefHeight(_h);

        getChildren().add(cell);
    }

    public Cell get(int x, int y){
        return cells[y][x];
    }
}
