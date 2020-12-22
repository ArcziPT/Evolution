package com.arczipt.ewolucja.simulation.stat;

import com.arczipt.ewolucja.simulation.observers.AnimalDeathObserver;
import com.arczipt.ewolucja.simulation.models.Animal;
import com.arczipt.ewolucja.simulation.models.Genome;
import com.arczipt.ewolucja.simulation.models.WorldMap;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurrentStatistics implements AnimalDeathObserver {
    private WorldMap worldMap;

    private int animalsNumber = 0;
    private int plantsNumber = 0;
    private Map<Genome, Integer> genomes;

    //statistics for current epoch
    private double avgDeathAge = 0;
    private int deadAnimalsNumber = 0;
    private double avgEnergy = 0;
    private double avgChildrenNumber = 0;

    //temp
    private double deathAgeSum_t = 0;
    private int deadAnimalsNumber_t = 0;

    public CurrentStatistics(WorldMap worldMap){
        this.worldMap = worldMap;
        this.genomes = new HashMap<>();

        worldMap.register(this);
    }

    public void update(){
        plantsNumber = worldMap.getPlantsNumber();

        if(deadAnimalsNumber_t != 0)
            avgDeathAge = deathAgeSum_t / deadAnimalsNumber_t;
        else
            avgDeathAge = 0;
        deadAnimalsNumber = deadAnimalsNumber_t;

        //reset old
        deadAnimalsNumber_t = 0;
        deathAgeSum_t = 0;

        Stream<Animal> animals = worldMap.getAnimals();
        animalsNumber = (int) animals.count();

        animals = worldMap.getAnimals();
        int totalEnergy = animals.map(Animal::getEnergy).reduce(0, (subtotal, e) -> subtotal += e);
        avgEnergy = (double) totalEnergy/animalsNumber;

        animals = worldMap.getAnimals();
        int totalChildrenNumber = animals.map(Animal::getChildrenNumber).reduce(0, (subtotal, e) -> subtotal += e);
        avgChildrenNumber = (double) totalChildrenNumber/animalsNumber;

        genomes = new HashMap<>();
        worldMap.getAnimals().forEach(a -> genomes.put(a.getGenome(), genomes.getOrDefault(a.getGenome(), 0) + 1));
    }

    @Override
    public void animalDied(Animal a) {
        deathAgeSum_t += a.getAge();
        deadAnimalsNumber_t++;
    }

    public int getDeadAnimalsNumber() {
        return deadAnimalsNumber;
    }

    public int getAnimalsNumber() {
        return animalsNumber;
    }

    public int getPlantsNumber() {
        return plantsNumber;
    }

    public Map<Genome, Integer> getGenomes(){
        return genomes;
    }

    public ArrayList<Genome> getMostCommonGenomes() {
        Optional<Integer> max = genomes.values().stream().max(Comparator.comparingInt(Integer::intValue));
        if(max.isEmpty())
            return new ArrayList<>();

        return genomes.entrySet().stream().filter(e -> e.getValue().equals(max.get())).map(Map.Entry::getKey).limit(10).collect(Collectors.toCollection(ArrayList::new));
    }

    public double getAvgEnergy() {
        return avgEnergy;
    }

    public double getAvgDeathAge() {
        return avgDeathAge;
    }

    public double getAvgChildrenNumber() {
        return avgChildrenNumber;
    }

    @Override
    public String toString() {
        return "CurrentStatistics{" +
                "worldMap=" + worldMap +
                ", animalsNumber=" + animalsNumber +
                ", plantsNumber=" + plantsNumber +
                ", genomes=" + genomes +
                ", avgDeathAge=" + avgDeathAge +
                ", deadAnimalsNumber=" + deadAnimalsNumber +
                ", avgEnergy=" + avgEnergy +
                ", avgChildrenNumber=" + avgChildrenNumber +
                '}';
    }
}
