package com.arczipt.ewolucja.simulation.observers;

public interface AnimalDeathBroadcaster {
    void register(AnimalDeathObserver observer);
}
