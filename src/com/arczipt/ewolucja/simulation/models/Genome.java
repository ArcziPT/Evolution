package com.arczipt.ewolucja.simulation.models;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Genome  {
    private Rotation[] genome = new Rotation[32];

    public Genome(){
        List<Rotation> g = Arrays.stream(genome).map(i -> ThreadLocalRandom.current().nextInt(0, 8))
                .map(i -> Rotation.values()[i]).sorted(Comparator.comparingInt(Rotation::ordinal)).collect(Collectors.toList());

        repair();
        genome = g.toArray(new Rotation[32]);
    }

    public Genome(Genome g1, Genome g2){
        int i1 = ThreadLocalRandom.current().nextInt(0, 32);
        int i2 = ThreadLocalRandom.current().nextInt(0, 32);

        while(i1 == i2){
            i2 = ThreadLocalRandom.current().nextInt(0, 32);
        }

        if(i1 > i2){
            int t = i1;
            i1 = i2;
            i2 = t;
        }

        int parent = ThreadLocalRandom.current().nextInt(1, 2);

        if(parent == 1){
            for(int i=0; i<i1; i++){
                this.genome[i] = g1.genome[i];
            }
            for(int i=i1; i<i2; i++){
                this.genome[i] = g2.genome[i];
            }
            for(int i=i2; i<32; i++){
                this.genome[i] = g1.genome[i];
            }
        }else{
            for(int i=0; i<i1; i++){
                this.genome[i] = g2.genome[i];
            }
            for(int i=i1; i<i2; i++){
                this.genome[i] = g1.genome[i];
            }
            for(int i=i2; i<32; i++){
                this.genome[i] = g2.genome[i];
            }
        }

        repair();

        Arrays.sort(genome, Comparator.comparingInt(Enum::ordinal));
    }

    private void repair(){
        Map<Rotation, Integer> geneNumber = Arrays.stream(Rotation.values()).collect(Collectors.toMap(Function.identity(), g -> 0));

        for(Rotation g : this.genome){
            geneNumber.put(g, geneNumber.getOrDefault(g, 0) + 1);
        }

        for(Map.Entry<Rotation, Integer> entry : geneNumber.entrySet()){
            if(entry.getValue() > 0)
                continue;

            int pos = ThreadLocalRandom.current().nextInt(0, 32);
            while(geneNumber.get(genome[pos]) <= 1){
                pos = ThreadLocalRandom.current().nextInt(0, 32);
            }

            geneNumber.put(genome[pos], geneNumber.get(genome[pos]) - 1);
            genome[pos] = entry.getKey();
            geneNumber.put(entry.getKey(), geneNumber.getOrDefault(entry.getValue(), 0) + 1);
        }
    }

    public Rotation chooseRotation(){
        int pos = ThreadLocalRandom.current().nextInt(0, 32);
        return genome[pos];
    }

    @Override
    public String toString() {
        String s = "";
        for(Rotation dir : genome){
            s += dir;
        }
        s += "\n";
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genome genome1 = (Genome) o;
        return Arrays.equals(genome, genome1.genome);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genome);
    }
}
