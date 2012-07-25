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
package Binary;

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
    protected double functionValue;
    public boolean[] bits;
    protected static Random rnd = new Random();

    public Problem(int dim) {
        bits = new boolean[dim];
        fillRandom();
        evaluate();
    }

    public void evaluate() {
        functionValue = calculateFunction();
    }

    public final void fillRandom() {
        for (int i = 0; i < bits.length; i++) {
            bits[i] = rnd.nextBoolean();
        }
        evaluate();
    }

    public int getNumberOfGenes() {
        return bits.length;
    }

    public boolean getGeneValue(int index) {
        return bits[index];
    }

    public void setGeneValue(int index, boolean value) {
        this.bits[index] = value;
        evaluate();
    }

    @Override
    public int compareTo(Problem o) {
        if (functionValue > o.functionValue) {
            return 1;
        }
        if (functionValue < o.functionValue) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(this.getClass().getSimpleName() + "(");
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                str.append("1");
            } else {
                str.append("0");
            }
        }
        str.append(")=").append(functionValue);
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
            System.arraycopy(bits, 0, tmp.bits, 0, bits.length);
            //copy fitness
            tmp.functionValue = functionValue;
            return tmp;








        } catch (Exception ex) {
            Logger.getLogger(Problem.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        //something wrong appens
        return null;
    }
}
