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
package Permutation.TSP.Init;

import Permutation.TSP.*;
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
public class GreadyInsertionMinimum_vertex {

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
        tour.add( notSelectedCitys.remove( random.nextInt(permut.length)));
        //add remains citys
        while (!notSelectedCitys.isEmpty()) {
            //----select next city to insert in the tour ---------
            // city closest to the one city in the tour
            int indexOfTour = -1;
            int indexOfCity = -1;
            double min = Double.MAX_VALUE;
            //all the citys in the tour
            for (int i = 0; i < tour.size(); i++) {
                //all citys not selected
                for (int j = 0; j < notSelectedCitys.size(); j++) {
                   //minimal distance between city and the tour
                    if( _distance[tour.get(i)][notSelectedCitys.get(j)] < min){
                        min = _distance[tour.get(i)][notSelectedCitys.get(j)];
                        indexOfTour = i;
                        indexOfCity = j;
                    }
                }                
            }
            //city in the tour
            int city = tour.get(indexOfTour);
            //city selected
            int selected = notSelectedCitys.get(indexOfCity);
            //previous city in the tour
            int previous = tour.get((indexOfTour - 1 + tour.size()) % tour.size());
            //next city in the tour
            int next = tour.get((indexOfTour + 1 ) % tour.size());
            //insert after City in the Tour?
            if( _distance[previous][city] + _distance[selected][next]   < 
               _distance[previous][selected]+ _distance[city][next] ){
                 tour.add((int) indexOfTour+1 , selected);                
            }
            //insert before city
            else{
               tour.add((int) indexOfTour , selected);  
            }
            //remove selected city from the citys not selected
            notSelectedCitys.remove((int) indexOfCity);
            
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
