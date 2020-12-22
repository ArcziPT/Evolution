package com.arczipt.ewolucja.simulation.models;

import com.arczipt.ewolucja.simulation.utils.Vector2D;

import java.util.concurrent.ThreadLocalRandom;

public enum Rotation {
    N(new Vector2D(0, 1), 0),
    NE(new Vector2D(1, 1), 45),
    E(new Vector2D(1, 0), 90),
    SE(new Vector2D(1, -1), 135),
    S(new Vector2D(0, -1), 180),
    SW(new Vector2D(-1, -1), 225),
    W(new Vector2D(-1, 0), 270),
    NW(new Vector2D(-1, 1), 315);

    Rotation(Vector2D direction, int angle){
        this.direction = direction;
        this.angle = angle;
    }

    private Vector2D direction;
    private int angle;

    public Vector2D getDirection() {
        return direction;
    }

    public static Rotation randomDirection(){
        int i = ThreadLocalRandom.current().nextInt(0, 7);
        return Rotation.values()[i];
    }

    @Override
    public String toString() {
        return String.valueOf(this.ordinal());
    }

    public static Rotation rotate(Rotation r1, Rotation r2){
        int angle = (r1.angle + r2.angle) % 360;

        Rotation rotation = null;
        switch (angle){
            case 0 -> rotation = N;
            case 45 -> rotation = NE;
            case 90 -> rotation = E;
            case 135 -> rotation = SE;
            case 180 -> rotation = S;
            case 225 -> rotation = SW;
            case 270 -> rotation = W;
            case 315 -> rotation = NW;
        }

        return rotation;
    }
}
