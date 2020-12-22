package com.arczipt.ewolucja.simulation.models;

public class DefaultBirthStrategy implements BirthStrategy {
    /**
     *
     * @param birthStrategy - one of parents' strategies
     * @return second's parent strategy, because this one is just default, but
     * second one might be 'observed'
     */
    @Override
    public BirthStrategy combine(BirthStrategy birthStrategy) {
        return birthStrategy;
    }

    private static DefaultBirthStrategy instance;
    private DefaultBirthStrategy(){}
    public static DefaultBirthStrategy getInstance(){
        if(instance == null)
            instance = new DefaultBirthStrategy();
        return instance;
    }

    @Override
    public String toString() {
        return "DefaultBirthStrategy";
    }
}
