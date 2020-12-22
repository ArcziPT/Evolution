package com.arczipt.ewolucja.simulation.models;

public interface BirthStrategy {
    /**
     * Before calling 'birth', we call combine on strategies of both parent in
     * order to obtain the one which might update statistics.
     *
     * @param birthStrategy - one of parents' strategies
     * @return
     */
    BirthStrategy combine(BirthStrategy birthStrategy);

    /**
     * Default method of creating new animal.
     *
     * @param a1 - first oparent
     * @param a2 - second parent
     * @return new animal
     */
    default Animal birth(Animal a1, Animal a2){
        int e1 = a1.makeParent();
        int e2 = a2.makeParent();

        return new Animal(a1.getConfig(), a1.getWorldMap(), a1.getWorldMap().getFreePosition(a1.getPosition()),
                new Genome(a1.getGenome(), a2.getGenome()), e1 + e2, a1.getBirthStrategy().combine(a2.getBirthStrategy()));
    }
}
