/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evolutionary.Population;

import Evolutionary.Problems.Individual;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author ZULU
 */
public class Population {

    Random rnd = new Random();
    /**
     * data structure to support population
     */
    ArrayList<Individual> pop = new ArrayList<>();

    /**
     * initialize the population
     *
     * @param template template of the individuals
     * @param size number of individuals
     */
    public void initialize(Individual template, int size) {
        pop = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Individual ind = (Individual) template.clone();
            ind.initialize();
            pop.add(ind);
        }
    }

    /**
     * Sort population
     */
    public void sort() {
        Collections.sort(pop);
        Collections.reverse(pop);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Individual ind : pop) {
            str.append(ind + "\n");
        }
        return str.toString();
    }

    @Override
    public Object clone() {
        Population tmp = new Population();
        for (int i = 0; i < pop.size(); i++) {
            tmp.pop.add((Individual) pop.get(i).clone());
        }
        return tmp;
    }

    public Individual get(int index) {
        return pop.get(index);
    }

    public void set(int index, Individual ind) {
        pop.add(index, ind);
    }

    public void addIndividual(Individual ind) {
        pop.add(ind);
    }

    public Individual getRandom() {
        return pop.get(rnd.nextInt(pop.size()));
    }

    public int size() {
        return pop.size();
    }

    /**
     * Evaluate the population
     * @return number of evaluations done
     */
    public int evaluate() {
        int evals = 0;
        for (Individual ind : pop) {
            if (ind.evaluate()) {
                evals++;
            }
        }
        sort();
        return evals;
    }
    
    public Iterator<Individual> getIterator(){
        return pop.iterator();
    }
    
    public Iterator<Individual> getSortedIterator(){
        sort();
        return pop.iterator();
    }
    
    public void append(Population other){
        pop.addAll( other.pop);
    }
}
