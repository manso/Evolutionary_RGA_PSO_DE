/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Binary;

import problems.MaxOnes;

/**
 *
 * @author ZULU
 */
public class TestMaxOnes {

    public static void main(String[] args) {
        MaxOnes m = new MaxOnes();
        MaxOnes m1 = (MaxOnes) m.clone();
        m.set(0, true);
        System.out.println(m);
        System.out.println(m1);

    }
}
