package com.arczipt.ewolucja.simulation.models;

import com.arczipt.ewolucja.simulation.observers.AnimalDeathBroadcaster;
import com.arczipt.ewolucja.simulation.observers.AnimalDeathObserver;
import com.arczipt.ewolucja.simulation.stat.AnimalStatistics;
import com.arczipt.ewolucja.simulation.utils.Vector2D;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WorldMap implements AnimalDeathBroadcaster {
    private Map<Vector2D, ArrayList<Animal>> animalMap = new HashMap<>();
    private Map<Vector2D, Plant> plantMap = new HashMap<>();

    private ArrayList<AnimalDeathObserver> observers = new ArrayList<>();

    private boolean isAnimalObserved = false;
    private Config config;
    private int epoch = 0;

    private int jungleWidth;
    private int jungleLength;

    private int jungleX;
    private int jungleY;

    private int[][] energies;
    private boolean[][] plants;

    public WorldMap(Config config){
        this.config = config;
        energies = new int[config.getY()][config.getX()];
        plants = new boolean[config.getY()][config.getX()];

        jungleWidth = (int) (config.getX() * config.getJungleRatio());
        jungleLength = (int) (config.getY() * config.getJungleRatio());

        jungleX = (config.getX() - jungleWidth)/2;
        jungleY = (config.getY() - jungleLength)/2;

        IntStream.range(0, config.getDefaultAnimalNumber())
                .mapToObj(i -> {
                    int x = ThreadLocalRandom.current().nextInt(0, config.getX());
                    int y = ThreadLocalRandom.current().nextInt(0, config.getY());

                    return new Vector2D(x, y);
                })
                .map(v -> new Animal(config, this, v, new Genome(), config.getDefaultEnergy(), DefaultBirthStrategy.getInstance()))
                .forEach(this::put);

        addPlant(config.getDefaultJunglePlantNumber(),true);
        addPlant(config.getDefaultPlantNumber(), false);
    }

    private void put(Animal animal){
        Vector2D vec = animal.getPosition();
        if(energies[vec.getY()][vec.getX()] < animal.getEnergy())
            energies[vec.getY()][vec.getX()] = animal.getEnergy();

        if(animalMap.containsKey(animal.getPosition()))
            animalMap.get(animal.getPosition()).add(animal);
        else
            animalMap.put(animal.getPosition(), new ArrayList<>(Collections.singletonList(animal)));
    }

    public void update(Animal animal){
        put(animal);
    }

    void addPlant(int n, boolean inJungle){
        ArrayList<Vector2D> availablePositions = new ArrayList<>();
        if(inJungle){
            for(int i = jungleX; i<jungleX + jungleWidth; i++){
                for(int j = jungleY; j<jungleY + jungleLength; j++){
                    Vector2D vec = new Vector2D(i, j);
                    if(animalMap.containsKey(vec))
                        continue;

                    availablePositions.add(vec);
                }
            }
        }else{
            for(int i = 0; i<config.getX(); i++){
                for(int j = 0; j<config.getY(); j++){
                    if(i >= jungleX && i < jungleX + jungleWidth && j >= jungleY && j < jungleY + jungleLength)
                        continue;

                    Vector2D vec = new Vector2D(i, j);
                    if(animalMap.containsKey(vec))
                        continue;

                    availablePositions.add(vec);
                }
            }
        }

        if(availablePositions.isEmpty())
            return;

        for(int a = 0; a<n; a++){
            if(availablePositions.isEmpty())
                return;

            int i = ThreadLocalRandom.current().nextInt(0, availablePositions.size());

            Vector2D position = availablePositions.get(i);
            availablePositions.remove(i);

            plants[position.getY()][position.getX()] = true;
            Plant plant = new Plant(position, config.getDefaultPlantEnergy());
            plantMap.put(position, plant);
        }
    }

    public void step(){
        addPlant(1, true);
        addPlant(1, false);

        getAnimals().forEach(a -> a.nextDay(epoch));

        //copy and reset map
        Stream<Animal> animals = getAnimals();
        animalMap = new HashMap<>();
        energies = new int[config.getY()][config.getX()];

        //move
        animals.forEach(a -> {
            if(a.isAlive())
                a.move();
            else
                observers.forEach(o -> o.animalDied(a));
        });

        eat();
        copulate();

        epoch++;
    }

    private void eat(){
        animalMap.forEach((key, value) -> {
            if (plantMap.containsKey(key)) {
                Stream<Animal> st = value.stream();
                int maxEnergy = st.max(Animal::compareTo).get().getEnergy();

                st = value.stream();
                int num = (int) st.filter(a -> a.getEnergy() == maxEnergy).count();
                int energy = (plantMap.get(key).getEnergy() / num);

                st = value.stream();
                st.forEach(a -> a.eat(energy));

                plants[key.getY()][key.getX()] = false;
                plantMap.remove(key);
            }
        });
    }

    private void copulate(){
        ArrayList<Animal> newAnimals = new ArrayList<>();

        animalMap.values().forEach(v -> {
            if(v.size() >= 2){
                v.sort(Animal::compareTo);

                Animal a1 = v.get(0);
                Animal a2 = v.get(1);

                if(a1.getEnergy() > config.getDefaultEnergy()/2 && a2.getEnergy() > config.getDefaultEnergy()/2)
                    newAnimals.add(a1.birth(a2));
            }
        });

        newAnimals.forEach(this::put);
    }

    public Vector2D getFreePosition(Vector2D position){
        for(Rotation dir : Rotation.values()){
            Vector2D pos = position.add(dir.getDirection());

            if(pos.getX() >= config.getX())
                pos.setX(pos.getX() - config.getX());

            if(pos.getY() >= config.getY())
                pos.setY(pos.getY() - config.getY());

            if(pos.getX() < 0)
                pos.setX(pos.getX() + config.getX());

            if(pos.getY() < 0)
                pos.setY(pos.getY() + config.getY());

            if(animalMap.getOrDefault(pos, new ArrayList<>()).size() == 0)
                return pos;
        }

        return position.add(Rotation.randomDirection().getDirection());
    }

    public AnimalStatistics observe(Animal animal){
        if(!isAnimalObserved){
            isAnimalObserved = true;
            AnimalStatistics animalStatistics = new AnimalStatistics(animal);
            animal.setBirthStrategy(new ObservedBirthStrategy(animalStatistics));
            return animalStatistics;
        }
        else
            throw new IllegalStateException("Animal is already observed!");
    }

    public int[][] getEnergy(){
        return energies;
    }

    public boolean[][] hasPlant(){
        return plants;
    }

    public boolean hasAnimals(){
        return animalMap.size() > 0;
    }

    @Override
    public void register(AnimalDeathObserver observer) {
        observers.add(observer);
    }

    public void unobserve(){
        if(isAnimalObserved){
            isAnimalObserved = false;
            animalMap.values().stream().flatMap(ArrayList::stream).forEach(a -> a.setBirthStrategy(DefaultBirthStrategy.getInstance()));
        }
    }

    public Animal getAnimal(Vector2D position){
        return animalMap.getOrDefault(position, new ArrayList<>()).stream().max(Comparator.comparingInt(Animal::getEnergy)).orElseThrow(() -> new IllegalArgumentException("No animals at this position."));
    }

    public Stream<Animal> getAnimals(){
        return animalMap.values().stream().flatMap(ArrayList::stream);
    }

    Stream<Plant> getPlants(){
        return plantMap.values().stream();
    }

    public int getPlantsNumber(){
        return plantMap.size();
    }

    public int getEpoch() {
        return epoch;
    }
}
