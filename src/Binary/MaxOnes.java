/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Binary;

/**
 *
 * @author ZULU
 */
public class MaxOnes extends Problem {

    public MaxOnes(int dim) {
        super(dim);
    }

    @Override
    protected double calculateFunction() {
        int ones = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                ones++;
            }
        }
        return ones;
    }
}
