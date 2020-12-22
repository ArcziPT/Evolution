package com.arczipt.ewolucja.simulation;

import com.arczipt.ewolucja.simulation.models.Animal;
import com.arczipt.ewolucja.simulation.models.Config;
import com.arczipt.ewolucja.simulation.models.WorldMap;
import com.arczipt.ewolucja.simulation.stat.AnimalStatistics;
import com.arczipt.ewolucja.simulation.stat.AvgStatistics;
import com.arczipt.ewolucja.simulation.stat.CurrentStatistics;
import com.arczipt.ewolucja.simulation.utils.Vector2D;

import java.util.stream.Stream;

public class Simulation {
    private WorldMap worldMap;
    private CurrentStatistics currentStatistics;
    private AvgStatistics avgStatistics;

    private Config config;

    public Simulation(Config config){
        worldMap = new WorldMap(config);
        currentStatistics = new CurrentStatistics(worldMap);
        avgStatistics = new AvgStatistics(currentStatistics, config.getSaveStatistics());
    }

    /**
     * @return boolean indicating animals' presence on map
     */
    public boolean hasAnimals(){
        return worldMap.hasAnimals();
    }

    /**
     * One step of simulation.
     *
     * @return false if it has finished, true otherwise.
     */
    public boolean step(){
        worldMap.step();
        currentStatistics.update();
        avgStatistics.update();

        return worldMap.hasAnimals();
    }

    /**
     * Observe animal in order to get access to statistics.
     *
     * @param position - position at map, from which animal with highest energy will be observed.
     * @return animalStatistics
     */
    public AnimalStatistics observe(Vector2D position){
        Animal animal = worldMap.getAnimal(position);
        return worldMap.observe(animal);
    }

    /**
     *
     * @return stream of alive animals
     */
    public Stream<Animal> getAnimals(){
        return worldMap.getAnimals();
    }

    /**
     *
     * @return statistics of simulation
     */
    public CurrentStatistics getCurrentStatistics() {
        return currentStatistics;
    }

    public Config getConfig() {
        return config;
    }

    public int getEpoch(){
        return worldMap.getEpoch();
    }

    public int[][] getEnergy(){
        return worldMap.getEnergy();
    }

    public boolean[][] hasPlant(){
        return worldMap.hasPlant();
    }
}
