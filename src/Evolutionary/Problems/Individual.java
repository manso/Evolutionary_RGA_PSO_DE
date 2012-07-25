/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evolutionary.Problems;

import Binary.Problem;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZULU
 */
public abstract class Individual<G> implements Comparable<Individual<G>>, Cloneable {

    public static boolean MAXIMIZE = true;
    public static boolean MINIMIZE = false;
    /**
     * type of optimization individual
     */
    boolean isMaximization;
    /**
     * is evaluated
     */
    protected boolean isEvaluated;
    /**
     * random generator
     */
    protected static Random rnd = new Random();
    /**
     * Array of genes
     */
    protected List<G> chromossom = new ArrayList<>();
    /**
     * fitness value of the individual
     */
    protected double functionValue;

    /**
     * Evaluate the fitness of the individual
     *
     * @return fitness
     */
    protected abstract double fitnessFunction();
    
    /**
     * initialize the individual
     * eventaly in the random way
     */
    public abstract void initialize();

    /**
     * Create a new individuals
     *
     * @param optimization TRUE - maximization FALSE- minimization
     */
    public Individual(boolean optimization) {
        this.isMaximization = optimization;
    }

    public boolean evaluate() {
        //if is evaluated 
        // dont evaluate again
        if (isEvaluated) {
            return false;
        }
        functionValue = fitnessFunction();
        isEvaluated = true;
        return true;
    }

    /**
     * Returns the number of genes in the chromossome
     *
     * @return # of genes
     */
    public int size() {
        return chromossom.size();
    }

    /**
     * Return the ith gene in the chromosome.
     *
     * @param i gene index
     * @return gene value
     */
    public G get(int i) {
        return chromossom.get(i);
    }

    public void set(int i, G value) {
        chromossom.set(i, value);
        isEvaluated = false;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(this.getClass().getSimpleName() + "(");
        for (int i = 0; i < chromossom.size(); i++) {
            str.append(chromossom.get(i)).append(" ");
        }
        str.append(")=");
        if (isEvaluated) {
            str.append(functionValue);
        } else {
            str.append("NOT_EVALUATED");
        }
        return str.toString();
    }

    public int compareTo(Individual o) {
        //minimization or maximization
        int type = isMaximization ? 1 : -1;
        if (functionValue > o.functionValue) {
            return type;
        }
        if (functionValue < o.functionValue) {
            return -type;
        }
        return 0;
    }

    @Override
    public Object clone() {
        try {
            //get default constructor
            Constructor co = this.getClass().getConstructor();
            //make tmp object with default constructor
            Object[] objectParam = {}; //type of optimization
            Individual tmp = (Individual) co.newInstance(objectParam);
            tmp.chromossom.clear();
            //deep copy of genes
            for (int i = 0; i < chromossom.size(); i++) {
                tmp.chromossom.add((G)chromossom.get(i));
            }
            //copy fitness
            tmp.functionValue = functionValue;
            tmp.isEvaluated = isEvaluated;
            return tmp;
        } catch (Exception ex) {
            Logger.getLogger(Individual.class.getName()).log(Level.SEVERE, null, ex);
        }
        //something wrong appens
        return null;
    }
}
