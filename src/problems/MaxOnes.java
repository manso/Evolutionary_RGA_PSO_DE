/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package problems;

import problems.IndividualBinary;

/**
 *
 * @author ZULU
 */
public class MaxOnes extends IndividualBinary {

    public MaxOnes() {
        this(32);
    }
    public MaxOnes(int n) {
        super(n, MAXIMIZE);
        evaluate();
    }

    @Override
    protected double fitnessFunction() {
        int ones = 0;
        for (int i = 0; i < chromossom.size(); i++) {
            if (chromossom.get(i)) {
                ones++;
            }
        }
        return ones;
    }
}
