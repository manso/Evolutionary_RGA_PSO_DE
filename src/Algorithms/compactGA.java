/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Algorithms;

import java.util.Random;

/**
 * http://home.arcor.de/claudi-und-martin/Diplom_17_08/Goldberg,%20Harik-The%20compact%20genetic%20algorithm.pdf
 *
 * @author ZULU
 */
public class compactGA {

    static Random random = new Random();

    public static boolean[] generate(int size, double[] p) {
        boolean[] ind = new boolean[size];
        for (int i = 0; i < ind.length; i++) {
            if (random.nextDouble() < p[i]) {
                ind[i] = true;
            }
        }
        return ind;
    }

    public static int evaluate(boolean[] ind) {
        int ones = 0;
        for (int i = 0; i < ind.length; i++) {
            if (ind[i]) {
                ones++;
            }
        }
        return ones;
    }

    public static boolean converge(double[] p) {
        for (int i = 0; i < p.length; i++) {
            if (p[i] > 0 && p[i] < 1) {
                return false;
            }
        }
        return true;
    }

    public static String toString(boolean[] x) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < x.length; i++) {
            if (x[i]) {
                str.append("1");
            } else {
                str.append("0");
            }
        }
        str.append(" = " + evaluate(x));
        return str.toString();
    }
    
     public static String toString(double[] x) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < x.length; i++) {
            if( x[i]*10 >= 10)
                str.append(".");
            else str.append((int)(x[i]*10) + "");
//            if (x[i]>= 0.5) {
//                str.append("1");
//            } else {
//                str.append("0");
//            }
        }
        return str.toString();
    }

    public static void main(String[] args) {
        int size = 40;
        int n = 40;

        double[] p = new double[size];
        for (int i = 0; i < p.length; i++) {
            p[i] = 0.5;
        }
        //best individual 
        boolean[] best = generate(size, p);

        while (!converge(p)) {

            boolean[] a = generate(size, p);
            boolean[] b = generate(size, p);
            boolean[] winner;
            boolean[] loser;

            if (evaluate(a) > evaluate(b)) {
                winner = a;
                loser = b;
            } else {
                winner = b;
                loser = a;
            }

            for (int i = 0; i < loser.length; i++) {
                if (winner[i] != loser[i]) {
                    if (winner[i]) {
                        p[i] += 1.0 / n;
                    } else {
                        p[i] -= 1.0 / n;
                    }
                }
            }//update

            if (evaluate(winner) > evaluate(best)) {
                best = winner;
            }
            System.out.println("BEST = " + toString(best) );

        }



    }
}
