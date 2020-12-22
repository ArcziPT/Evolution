package com.arczipt.ewolucja.simulation.models;

import com.arczipt.ewolucja.simulation.stat.AnimalStatistics;

/**
 * Animal which is observed and its descendants use this strategy.
 * I contains info about observed animal, and every time 'birth' is called,
 * we update statistics.
 */
public class ObservedBirthStrategy implements BirthStrategy {
    AnimalStatistics animalStatistics;

    public ObservedBirthStrategy(AnimalStatistics animalStatistics){
        this.animalStatistics = animalStatistics;
    }

    /**
     *
     * @param birthStrategy - one of parents' strategies
     * @return this strategy, because it is 'observed' and the other one is default
     * or it is observing the same animal (because there can be only one).
     */
    @Override
    public BirthStrategy combine(BirthStrategy birthStrategy) {
        return this;
    }

    /**
     * Calls default method and update stats.
     * @param a1 - first oparent
     * @param a2 - second parent
     * @return
     */
    @Override
    public Animal birth(Animal a1, Animal a2) {
        animalStatistics.addDescendant();

        if(animalStatistics.getAnimal() == a1 || animalStatistics.getAnimal() == a2)
            animalStatistics.addChild();

        return BirthStrategy.super.birth(a1, a2);
    }

    @Override
    public String toString() {
        return "ObservedBirthStrategy";
    }
}
