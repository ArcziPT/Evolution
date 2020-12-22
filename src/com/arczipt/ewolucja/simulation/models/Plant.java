package com.arczipt.ewolucja.simulation.models;

import com.arczipt.ewolucja.simulation.utils.Vector2D;

public class Plant {
    private final Vector2D position;
    private final int energy;

    public Plant(Vector2D position, int energy){
        this.position = position;
        this.energy = energy;
    }

    public Vector2D getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "position=" + position +
                ", energy=" + energy +
                '}';
    }
}
