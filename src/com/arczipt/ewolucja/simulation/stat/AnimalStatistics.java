package com.arczipt.ewolucja.simulation.stat;

import com.arczipt.ewolucja.simulation.models.Animal;

public class AnimalStatistics{
    private int childNumber = 0;
    private int descendantNumber = 0;

    private Animal animal;

    public AnimalStatistics(Animal animal){
        this.animal = animal;
    }

    public void unobserve(){
        animal.getWorldMap().unobserve();
    }

    public void addDescendant(){
        descendantNumber++;
    }

    public void addChild(){
        childNumber++;
    }

    public int getChildNumber() {
        return childNumber;
    }

    public int getDescendantNumber() {
        return descendantNumber;
    }

    public Animal getAnimal() {
        return animal;
    }

    @Override
    public String toString() {
        return "AnimalStatistics{" +
                "childNumber=" + childNumber +
                ", descendantNumber=" + descendantNumber +
                ", animal=" + animal +
                '}';
    }
}
