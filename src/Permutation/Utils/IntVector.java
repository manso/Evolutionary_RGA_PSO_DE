/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Permutation.Utils;

import java.util.Arrays;

/**
 *
 * @author small
 */
public class IntVector {

    int genome[];

    public IntVector(int[] genome) {
        this.genome = genome;
    }

    private void swap(int i, int j) {
        int aux = genome[i];
        genome[i] = genome[j];
        genome[j] = aux;
    }

    public void invert(int begin, int end) {
        //inversion <-<-E - - - -B<-<-<-
        if (begin > end) {
            int itera = (genome.length - begin + end) / 2;
            while (itera-- > 0) {
                swap(begin, end);
                end = (genome.length + end - 1) % genome.length;
                begin = (begin + 1) % genome.length;
                System.out.println(toString());
            }
        } else {
            //inversion between ---B<-<-<-E---
            while (begin < end) {
                swap(begin, end);
                begin++;
                end--;
            }
        }

    }

    @Override
    public String toString() {
        return Arrays.toString(genome);
    }

    public void invert2(int begin, int end) {
        int itera = (genome.length - begin + end) / 2;
        while (itera-- > 0) {
            swap(begin, end);
            end = (genome.length + end - 1) % genome.length;
            begin = (begin + 1) % genome.length;
            System.out.println(toString());
        }
    }

    public static void main(String[] args) {
        int[] v = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        IntVector vi = new IntVector(v);
        System.out.println(vi);

        vi.invert(4, 8);
        System.out.println(vi);
    }
}
