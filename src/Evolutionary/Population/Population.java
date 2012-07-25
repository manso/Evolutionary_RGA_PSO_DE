/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evolutionary.Population;

import Evolutionary.Problems.Individual;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author ZULU
 */
public class Population {
    /**
     * data structure to support population
     */
    ArrayList<Individual> pop = new ArrayList<>();
    /**
     * initialize the population
     * @param template template of the individuals
     * @param size  number of individuals
     */
    public void initialize(Individual template, int size){
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
    public void sort(){
        Collections.sort(pop);
        Collections.reverse(pop);
    }
    
     @Override
    public String toString() {
         StringBuilder str = new StringBuilder();
         for( Individual ind : pop){
             str.append(ind + "\n");
         }
         return str.toString();
     }
     
     @Override
    public Object clone() {
         Population tmp = new Population();
         for (int i = 0; i < pop.size(); i++) {
             tmp.pop.add( (Individual)pop.get(i).clone());
         }
         return tmp;
     }
     
     public Individual get(int index){
         return pop.get(index);
     }
     
     public void set(int index, Individual ind){
         pop.add(index, ind);
     }
    
    
    
}
