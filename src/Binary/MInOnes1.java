/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Binary;

/**
 *
 * @author ZULU
 */
public class MInOnes1 extends Problem {

    public MInOnes1(int dim) {
        super(dim);
    }

    @Override
    protected double calculateFunction() {
        int zeros = 0;
        for (int i = 0; i < bits.length; i++) {
            if (!bits[i]) {
                zeros++;
            }
        }
        return zeros;
    }
}
