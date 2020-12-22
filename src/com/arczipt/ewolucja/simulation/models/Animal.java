package com.arczipt.ewolucja.simulation.models;

import com.arczipt.ewolucja.simulation.utils.Vector2D;

import java.util.concurrent.ThreadLocalRandom;

public class Animal implements Comparable<Animal>{
    private BirthStrategy birthStrategy;

    private Vector2D position;
    private int energy;
    private int childrenNumber = 0;
    private int age = 0;
    private boolean alive = true;
    private int deathEpoch = -1;

    private WorldMap worldMap;
    private Config config;
    private Genome genome;

    private Rotation rotation;

    public Animal(Config config, WorldMap worldMap, Vector2D position, Genome genome, int energy, BirthStrategy birthStrategy){
        this.config = config;
        this.worldMap = worldMap;
        this.position = position;
        this.genome = genome;
        this.energy = energy;
        this.birthStrategy = birthStrategy;

        this.rotation = Rotation.values()[ThreadLocalRandom.current().nextInt(0, 8)];
    }

    public void move() {
        rotation = Rotation.rotate(rotation, genome.chooseRotation());
        Vector2D direction = rotation.getDirection();

        position = position.add(direction);

        //check if animal is out of map's border
        if(position.getX() >= config.getX())
            position.setX(position.getX() - config.getX());

        if(position.getY() >= config.getY())
            position.setY(position.getY() - config.getY());

        if(position.getX() < 0)
            position.setX(position.getX() + config.getX());

        if(position.getY() < 0)
            position.setY(position.getY() + config.getY());

        worldMap.update(this);
    }

    /**
     * Decreases energy and increases age.
     * @param epoch - current epoch
     */
    public void nextDay(int epoch){
        energy -= config.getMoveEnergy();
        if(energy <= 0){
            alive = false;

            //save date of death
            deathEpoch = epoch;
        }

        age++;
    }

    /**
     * Create new animal.
     *
     * @param a - second parent
     * @return new animal
     */
    public Animal birth(Animal a){
        //combine to strategies, which let us implement animal statistics
        BirthStrategy bs = birthStrategy.combine(a.birthStrategy);

        return bs.birth(this, a);
    }

    /**
     * Decreases energy due to giving birth.
     *
     * @return energy
     */
    public int makeParent(){
        childrenNumber++;

        int e = energy/4;
        energy -= e;

        return e;
    }

    public void eat(int energy){
        this.energy += energy;
    }

    @Override
    public int compareTo(Animal o) {
        return energy - o.energy;
    }

    public boolean isAlive() {
        return alive;
    }

    public Vector2D getPosition() {
        return position;
    }

    public int getEnergy() {
        return energy;
    }

    public int getChildrenNumber() {
        return childrenNumber;
    }

    public int getAge() {
        return age;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public Genome getGenome() {
        return genome;
    }

    public Config getConfig() {
        return config;
    }

    public BirthStrategy getBirthStrategy() {
        return birthStrategy;
    }

    public void setBirthStrategy(BirthStrategy birthStrategy) {
        this.birthStrategy = birthStrategy;
    }

    public void setChildrenNumber(int childrenNumber) {
        this.childrenNumber = childrenNumber;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "birthStrategy=" + birthStrategy +
                ", position=" + position +
                ", energy=" + energy +
                ", childrenNumber=" + childrenNumber +
                ", age=" + age +
                ", alive=" + alive +
                ", genome=" + genome +
                '}';
    }

    public int getDeathEpoch() {
        return deathEpoch;
    }

    public void setDeathEpoch(int deathEpoch) {
        this.deathEpoch = deathEpoch;
    }

    public Rotation getRotation() {
        return rotation;
    }

    public void setRotation(Rotation rotation) {
        this.rotation = rotation;
    }
}
