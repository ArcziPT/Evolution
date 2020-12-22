package com.arczipt.ewolucja.simulation.stat;

import com.arczipt.ewolucja.simulation.models.Genome;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Computes average of statistics from epoch 0 to n, and saves it to file 'avg_stat'.
 */
public class AvgStatistics{
    private CurrentStatistics currentStatistics;

    private int n;
    private int epoch;

    private double avgDeathAge;
    private double avgDeadAnimals;
    private double avgEnergy;
    private double avgChildren;

    private double avgAnimals;
    private double avgPlants;

    private Map<Genome, Integer> genomes;


    public AvgStatistics(CurrentStatistics currentStatistics, int n){
        this.n = n;
        this.currentStatistics = currentStatistics;
        this.genomes = new HashMap<>();
    }

    public void update(){
        if(epoch > n)
            return;

        if(epoch == n) {
            avgPlants /= epoch;
            avgAnimals /= epoch;

            avgChildren /= epoch;
            avgEnergy /= epoch;
            avgDeadAnimals /= epoch;
            avgDeathAge /= epoch;

            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("avg_stat"))) {
                for(Field field : AvgStatistics.class.getDeclaredFields()){
                    if(field.getType() == CurrentStatistics.class)
                        continue;

                    outputStream.writeChars(field.getName() + "=" + field.get(this) + "\n");
                }
            } catch (IOException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        epoch++;

        avgDeathAge += currentStatistics.getAvgDeathAge();
        avgDeadAnimals += currentStatistics.getDeadAnimalsNumber();
        avgEnergy += currentStatistics.getAvgEnergy();
        avgChildren += currentStatistics.getAvgChildrenNumber();

        avgAnimals += currentStatistics.getAnimalsNumber();
        avgPlants += currentStatistics.getPlantsNumber();

        currentStatistics.getGenomes().forEach((key, value) -> genomes.put(key, genomes.getOrDefault(key, 0) + value));
    }
}
