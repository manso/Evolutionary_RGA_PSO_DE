///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2012                                             ****/
///****     António Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politécnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****************************************************************************/
///****     This software was build with the purpose of learning.          ****/
///****     Its use is free and is not provided any guarantee              ****/
///****     or support.                                                    ****/
///****     If you met bugs, please, report them to the author             ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/
package Problem.Permutation;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ZULU
 */
public abstract class Problem implements Comparable<Problem> {

    protected abstract double calculateFunction();
    protected static Random rnd = new Random();
    protected double functionValue;
    public int[] genome;

    public Problem(int dim) {
        genome = new int[dim];
        fillRandom();
    }

    public void evaluate() {
        repair();
        functionValue = calculateFunction();
    }

    public final void fillRandom() {
        for (int i = 0; i < genome.length; i++) {
            genome[i] = i;
        }
        for (int i = genome.length - 2; i > 0; i--) {
            int index = rnd.nextInt(i);
            int aux = genome[index];
            genome[index] = genome[i];
            genome[i] = aux;
        }
    }

    public int getNumberOfGenes() {
        return genome.length;
    }

    public double getGeneValue(int index) {
        return genome[index];
    }

    public void setGeneValue(int index, int value) {
        this.genome[index] = value;
        evaluate();
    }

    @Override
    public int compareTo(Problem o) {
        if (functionValue > o.functionValue) {
            return -1;
        }
        if (functionValue < o.functionValue) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(this.getClass().getSimpleName() + "(");
        str.append(functionValue + " ) = ");
        str.append(Arrays.toString(genome));                
        return str.toString();
    }

    //deep clone using reflexion - need default constructor of of the objects
    public Problem getClone() {
        try {
            //get default constructor
            Constructor co = this.getClass().getConstructor();
            //make tmp object with default constructor
            Object[] objectParam = null; //no parameters
            Problem tmp = (Problem) co.newInstance(objectParam);
            //deep copy of genes
            System.arraycopy(genome, 0, tmp.genome, 0, genome.length);
            //copy fitness
            tmp.functionValue = functionValue;
            return tmp;
        } catch (Exception ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }
        //something wrong appens
        return null;
    }

    protected void repair() {
        //already normalized
        if (genome[0] == 0) {
            return;
        }

        int index = 0;
        int aux[] = new int[genome.length];
        System.arraycopy(genome, 0, aux, 0, aux.length);
        //index of zero 
        while (genome[index] != 0) {
            index++;
        }
        //rotate
        for (int i = 0; i < aux.length; i++) {
            genome[i] = aux[(index + i) % aux.length];
        }
    }

   
}
