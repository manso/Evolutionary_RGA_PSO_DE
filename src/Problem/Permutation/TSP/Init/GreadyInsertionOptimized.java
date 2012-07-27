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
package Problem.Permutation.TSP.Init;

import Problem.Permutation.TSP.Simples2;
import Problem.Permutation.TSP.TSP;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The Traveling Salesman Problem:A Case Study in Local Optimization David S.
 * Johnson Lyle A. McGeoch 1995 2.2. Four Important Tour Construction Algorithms
 * -Nearest Neighbor
 *
 * @author ZULU
 */
public class GreadyInsertionOptimized {

    /**
     * build a TSP tour using greedy insertion of citys
     * starting from a rondom city
     * insert the remains minimizing the subtour lenght
     * @param cost matrix of costs
     * @return tour of TSP
     */
    public static int[] greadyCityInsertion(double[][] cost) {
        //auxiliary variables
        int city, previous, next, index;
        double edge, newLenght, increaseTour, minLenght;
        //array of permutation
        int[] permut = new int[cost[0].length];
        //citys selected to tour
        ArrayList<Integer> tour = new ArrayList<>(permut.length);
        //remain citys
        ArrayList<Integer> notSelected = new ArrayList<>(permut.length);
        for (int i = 0; i < permut.length; i++) {
            notSelected.add(i);
        }
        Collections.shuffle(notSelected);
        double tourLenght = 0;
        //insert first city
        tour.add(notSelected.get(0));
        //insert remains
        for (int k = 1; k < permut.length; k++) {
            //get random city
            city = notSelected.get(k);
            //-----  calculate best insertion point -----
            minLenght = Double.MAX_VALUE;
            increaseTour = 0;
            index = 0;
            for (int i = 0; i < tour.size(); i++) {
                previous = tour.get(i % tour.size());
                next = tour.get((i + 1) % tour.size());
                edge = cost[previous][next];
                newLenght = tourLenght - edge + cost[previous][city] + cost[city][next];
                if (minLenght > newLenght) {
                    minLenght = newLenght;
                    index = i;
                    increaseTour = cost[previous][city] + cost[city][next] - edge;
                }
            }
            tour.add((int) index + 1, city);
            tourLenght += increaseTour;
        }
        //transfer ArrayList to permutation
        for (int i = 0; i < permut.length; i++) {
            permut[i] = tour.get(i);
        }
        return permut;
    }

    public static void main(String[] args) {
        int[] v = {0, 2, 4, 1, 3};
        Simples2 i = new Simples2();

        i.genome = v;
        System.out.println("i = " + i);
        i.genome = greadyCityInsertion(TSP._distance);
        i.evaluate();
        System.out.println("i = " + i);


    }
}
