package com.arczipt.ewolucja;

import com.arczipt.ewolucja.simulation.models.Animal;
import com.arczipt.ewolucja.simulation.models.Config;
import com.arczipt.ewolucja.simulation.models.DefaultBirthStrategy;
import com.arczipt.ewolucja.simulation.models.WorldMap;
import com.arczipt.ewolucja.simulation.stat.AnimalStatistics;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WorldMapUnitTest {
    @Test
    public void initTest(){
        Config config = new Config();
        config.setDefaultJunglePlantNumber(3);
        config.setDefaultPlantNumber(3);
        config.setDefaultAnimalNumber(10);
        config.setJungleRatio(0.2);
        config.setX(20);
        config.setY(20);
        config.setDefaultEnergy(5);

        WorldMap worldMap = new WorldMap(config);
        assertEquals(config.getDefaultJunglePlantNumber() + config.getDefaultPlantNumber(), worldMap.getPlantsNumber());
    }

    @Test
    public void getAnimalTest(){
        Config config = new Config();
        config.setDefaultJunglePlantNumber(3);
        config.setDefaultPlantNumber(3);
        config.setDefaultAnimalNumber(1);
        config.setJungleRatio(0.2);
        config.setX(20);
        config.setY(20);
        config.setDefaultEnergy(5);

        WorldMap worldMap = new WorldMap(config);
        Animal a = worldMap.getAnimals().findFirst().get();

        assertEquals(a, worldMap.getAnimal(a.getPosition()));
    }

    @Test
    public void observeTest(){
        Config config = new Config();
        config.setDefaultJunglePlantNumber(3);
        config.setDefaultPlantNumber(3);
        config.setDefaultAnimalNumber(10);
        config.setJungleRatio(0.2);
        config.setX(20);
        config.setY(20);
        config.setDefaultEnergy(5);

        WorldMap worldMap = new WorldMap(config);
        Animal a = worldMap.getAnimals().findFirst().get();

        AnimalStatistics st = worldMap.observe(a);
        assertNotEquals(a.getBirthStrategy(), DefaultBirthStrategy.getInstance());

        assertThrows(IllegalStateException.class, () -> worldMap.observe(a));

        worldMap.unobserve();
        st = worldMap.observe(a);
        assertNotEquals(a.getBirthStrategy(), DefaultBirthStrategy.getInstance());
    }
}
