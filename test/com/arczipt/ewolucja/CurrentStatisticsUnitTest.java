package com.arczipt.ewolucja;

import com.arczipt.ewolucja.simulation.models.Animal;
import com.arczipt.ewolucja.simulation.models.DefaultBirthStrategy;
import com.arczipt.ewolucja.simulation.models.Genome;
import com.arczipt.ewolucja.simulation.models.WorldMap;
import com.arczipt.ewolucja.simulation.stat.CurrentStatistics;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class CurrentStatisticsUnitTest {
    @Test
    public void updateTest(){
        WorldMap worldMap = Mockito.mock(WorldMap.class);
        CurrentStatistics currentStatistics = new CurrentStatistics(worldMap);

        ArrayList<Animal> animals = new ArrayList<>(Arrays.asList(
                new Animal(null, null, null, new Genome(), 10, DefaultBirthStrategy.getInstance()),
                new Animal(null, null, null, new Genome(), 4, DefaultBirthStrategy.getInstance()),
                new Animal(null, null, null, new Genome(), 20, DefaultBirthStrategy.getInstance()),
                new Animal(null, null, null, new Genome(), 16, DefaultBirthStrategy.getInstance()),
                new Animal(null, null, null, new Genome(), 8, DefaultBirthStrategy.getInstance())));

        animals.get(0).setChildrenNumber(1);
        animals.get(1).setChildrenNumber(0);
        animals.get(2).setChildrenNumber(3);
        animals.get(3).setChildrenNumber(2);
        animals.get(4).setChildrenNumber(5);

        animals.get(0).setAge(10);
        animals.get(1).setAge(4);
        animals.get(2).setAge(15);
        animals.get(3).setAge(11);
        animals.get(4).setAge(8);

        Mockito.when(worldMap.getAnimals()).then(invocationOnMock -> animals.stream());
        Mockito.when(worldMap.getPlantsNumber()).thenReturn(3);

        currentStatistics.update();

        assertEquals(currentStatistics.getAnimalsNumber(), 5);
        assertEquals(currentStatistics.getAvgChildrenNumber(), (double) 11/5);
        assertEquals(currentStatistics.getAvgDeathAge(), 0);
        assertEquals(currentStatistics.getAvgEnergy(), (double) 58/5);
        assertEquals(currentStatistics.getPlantsNumber(), 3);
        assertEquals(currentStatistics.getDeadAnimalsNumber(), 0);
    }

    @Test
    public void animalDiedTest(){
        WorldMap worldMap = Mockito.mock(WorldMap.class);
        CurrentStatistics currentStatistics = new CurrentStatistics(worldMap);

        ArrayList<Animal> animals = new ArrayList<>(Arrays.asList(
                new Animal(null, null, null, new Genome(), 10, DefaultBirthStrategy.getInstance()),
                new Animal(null, null, null, new Genome(), 4, DefaultBirthStrategy.getInstance()),
                new Animal(null, null, null, new Genome(), 20, DefaultBirthStrategy.getInstance()),
                new Animal(null, null, null, new Genome(), 16, DefaultBirthStrategy.getInstance()),
                new Animal(null, null, null, new Genome(), 8, DefaultBirthStrategy.getInstance())));

        animals.get(0).setAge(10);
        animals.get(1).setAge(4);
        animals.get(2).setAge(15);
        animals.get(3).setAge(11);
        animals.get(4).setAge(8);

        currentStatistics.animalDied(animals.get(0));
        currentStatistics.animalDied(animals.get(1));

        currentStatistics.update();

        assertEquals(currentStatistics.getDeadAnimalsNumber(), 2);
        assertEquals(currentStatistics.getAvgDeathAge(), 7);
    }
}
