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
import java.util.Random;

/**
 * The Traveling Salesman Problem:A Case Study in Local Optimization David S.
 * Johnson Lyle A. McGeoch 1995 2.2. Four Important Tour Construction Algorithms
 * -Nearest Neighbor
 *
 * @author ZULU
 */
public class GreadyInsertionMinimum {

    static Random random = new Random();
    static double[][] _distance;
    static int[] permut;

    public static int[] calculate(double[][] cost) {
        _distance = cost;
        permut = new int[cost[0].length];
        // tour of tsp
        ArrayList<Integer> tour = new ArrayList<>();
        //cities not in the tour
        ArrayList<Integer> notSelectedCitys = new ArrayList<>();
        //add all the citys
        for (int i = 0; i < permut.length; i++) {
            notSelectedCitys.add(i);
        }
        //add random city to the tour
        tour.add(notSelectedCitys.remove(random.nextInt(permut.length)));
        //add remains citys
        while (!notSelectedCitys.isEmpty()) {
            //----select next city to insert in the tour ---------
            // city closest to the one city in the tour
            int indexInTour = -1;
            Integer selectedCity = -1;
            double minLenght = Double.MAX_VALUE;
            //all the citys in the tour
            for (int i = 0; i < tour.size(); i++) {
                //all citys not selected
                for (int j = 0; j < notSelectedCitys.size(); j++) {
                    int previous = tour.get(i );
                    int next = tour.get((i + 1) % tour.size());
                    int city = notSelectedCitys.get(j);
                    //minimal distance between city and the tour
                    double dist = _distance[previous][city] + _distance[city][next];
                    if (dist < minLenght) {
                        minLenght = dist;
                        indexInTour = i;
                        selectedCity = city;
                    }
                }
            }
//            System.out.print("TOUR " + tour + " + " + selectedCity + " position " + indexInTour);
            tour.add(indexInTour,selectedCity );
//            System.out.println(" =>  " + tour);
            
            notSelectedCitys.remove(selectedCity);

        }
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
        i.genome = calculate(TSP._distance);
        i.evaluate();
        System.out.println("i = " + i);


    }
}
