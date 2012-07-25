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
package Permutation;

import java.util.Arrays;
import java.util.Random;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author David Pinheiro
 */
public class PMX {

    static Random random = new Random();
    //--------------------------------------------------------------
    /**
     * execute execute crossover between two parents
     *
     * @param tmp1 parent
     * @param tmp2 parent
     */
    public static int[][] execute(int[] parent1, int[] parent2) {
        int[][] offspring = new int[2][parent1.length];
        //alias to bidimensional array      
        int[] child1 = offspring[0];
        int[] child2 = offspring[1];
        //make copys of parents and clean genes
        for (int i = 0; i < parent2.length; i++) {
            //clean genes
            child1[i] = -1;
            child2[i] = -1;
        }

        //select two points of crossover
        int cut1 = random.nextInt(child1.length - 3);
        int cut2 = cut1 + random.nextInt(child1.length - 1 - cut1);
        //copy genes between cut points
        for (int j = cut1; j < cut2; ++j) {
            child1[j] = parent2[j];
            child2[j] = parent1[j];
        }
        //fill the left side of the 1st cut point
        for (int j = 0; j < cut1; ++j) {
            child1[j] = getMappedGene(parent2[j], child1, parent2, cut1, cut2);
            child2[j] = getMappedGene(parent1[j], child2, parent1, cut1, cut2);
        }
        // fill the right side of the 2st cut point
        for (int j = cut2; j < child1.length; ++j) {
            child1[j] = getMappedGene(parent2[j], child1, parent2, cut1, cut2);
            child2[j] = getMappedGene(parent1[j], child2, parent1, cut1, cut2);
        }

        return offspring;
    }

    /**
     * get maped gene of execute crossover
     *
     * @param gene original gene
     * @param destination individual to put the gene
     * @param origin individual to get the gene
     * @param cut1 first cut point
     * @param cut2 second cut point
     * @return value of mapped gene
     */
    private static int getMappedGene(int gene, int destination[], int origin[], int cut1, int cut2) {
        //if destination not contais the gene
        if (!contains(gene, destination)) {
            return gene;
        } //get mapped value
        else {
            //index of mapping
            int index = cut1;
            //get mapped value
            while (index < cut2 && gene != destination[index]) {
                index++;
            }
            //destination not contains the value
            if (!contains(origin[index], destination)) {
                return origin[index];
            } else {
                //calculate new mapping value                    
                return getMappedGene(origin[index], destination, origin, cut1, cut2);
            }
        }
    }

    /**
     * verify if the value is present in vector
     *
     * @param value value to search
     * @param vector array of values
     * @return
     */
    private static boolean contains(int value, int[] vector) {
        for (int i = 0; i < vector.length; i++) {
            if (vector[i] == value) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] p1 = {1, 2, 3, 4, 5, 6};
        int[] p2 = {6, 3, 1, 4, 5, 2};
        execute(p1, p2);
        System.out.println("P1 :" + Arrays.toString(p1));
        System.out.println("P2 :" + Arrays.toString(p2));


//        int[] p1 = {6, 2, 3, 4, 1, 7, 5};
//        int[] p2 = {5, 2, 4, 1, 3, 7, 6};
//        execute(p1, p2);
//        System.out.println("P1 :" + Arrays.toString(p1));
//        System.out.println("P2 :" + Arrays.toString(p2));


    }
}