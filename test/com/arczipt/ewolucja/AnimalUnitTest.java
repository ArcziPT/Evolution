package com.arczipt.ewolucja;

import com.arczipt.ewolucja.simulation.models.*;
import com.arczipt.ewolucja.simulation.utils.Vector2D;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalUnitTest {
    @Test
    public void nextDayTest(){
        for(int i = 1; i<5; i++){
            Config config = new Config();
            config.setMoveEnergy(i);

            WorldMap worldMap = Mockito.mock(WorldMap.class);
            Animal a = new Animal(config, worldMap, new Vector2D(2, 3), new Genome(), 5*i, DefaultBirthStrategy.getInstance());

            a.nextDay(worldMap.getEpoch());
            assertEquals(a.getEnergy(), 4*i);
            assertTrue(a.isAlive());

            a.nextDay(worldMap.getEpoch());
            assertEquals(a.getEnergy(), 3*i);
            assertTrue(a.isAlive());

            a.nextDay(worldMap.getEpoch());
            assertEquals(a.getEnergy(), 2*i);
            assertTrue(a.isAlive());

            a.nextDay(worldMap.getEpoch());
            assertEquals(a.getEnergy(), i);
            assertTrue(a.isAlive());

            a.nextDay(worldMap.getEpoch());
            assertEquals(a.getEnergy(), 0);
            assertFalse(a.isAlive());
        }
    }

    @Test
    public void moveTest(){
        Config config = new Config();
        config.setX(20);
        config.setY(20);

        WorldMap worldMap = Mockito.mock(WorldMap.class);
        Genome g = Mockito.mock(Genome.class);

        Mockito.when(g.chooseRotation()).thenReturn(Rotation.E);

        Animal a = new Animal(config, worldMap, new Vector2D(2, 3), g, 5, DefaultBirthStrategy.getInstance());

        a.setRotation(Rotation.N);

        a.move();
        assertEquals(new Vector2D(3, 3), a.getPosition());
    }

    @Test
    public void moveThroughBorderTest(){
        Config config = new Config();
        config.setX(10);
        config.setY(10);

        WorldMap worldMap = Mockito.mock(WorldMap.class);

        //x
        Genome g = Mockito.mock(Genome.class);
        Mockito.when(g.chooseRotation()).thenReturn(Rotation.E);
        Animal a = new Animal(config, worldMap, new Vector2D(9, 5), g, 5, DefaultBirthStrategy.getInstance());

        a.setRotation(Rotation.N);

        a.move();
        assertEquals(new Vector2D(0, 5), a.getPosition());

        //y
        g = Mockito.mock(Genome.class);
        Mockito.when(g.chooseRotation()).thenReturn(Rotation.S);
        a = new Animal(config, worldMap, new Vector2D(3, 0), g, 5, DefaultBirthStrategy.getInstance());

        a.setRotation(Rotation.N);

        a.move();
        assertEquals(new Vector2D(3, 9), a.getPosition());
    }

    @Test
    public void birthTest(){
        WorldMap worldMap = Mockito.mock(WorldMap.class);
        Mockito.when(worldMap.getFreePosition(Mockito.any())).thenReturn(new Vector2D(1, 1));

        Animal a1 = new Animal(null, worldMap, null, new Genome(), 5, DefaultBirthStrategy.getInstance());
        BirthStrategy birthStrategy = Mockito.mock(ObservedBirthStrategy.class);
        Animal a2 = new Animal(null, worldMap, null, new Genome(), 5, birthStrategy);

        a1.birth(a2);
        Mockito.verify(birthStrategy, Mockito.times(1)).birth(Mockito.any(), Mockito.any());
    }
}
