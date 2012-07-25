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

import Permutation.TSP.Simples;
import Permutation.TSP.TSP;
import Permutation.TSP.Ulysses16;
import java.util.Random;

/**
 * The Traveling Salesman Problem:A Case Study in Local Optimization David S.
 * Johnson Lyle A. McGeoch 1995 2.2. Four Important Tour Construction Algorithms
 * -Nearest Neighbor
 *
 * @author ZULU
 */
public class NearestNeighbor {

    static Random random = new Random();
    static double[][] _cost;
    static int[] permut;
    static boolean[] processed;

    public static int[] calculate(double[][] cost) {
        _cost = cost;
        permut = new int[cost[0].length];
        processed = new boolean[permut.length];
        //first city - Random
        permut[0] = random.nextInt(permut.length);
        processed[permut[0]] = true;
        int index = 1;
        while( index < permut.length){
            //calculate nearest city
            permut[index] = getNearest(permut[index-1]);
            //mark city
            processed[permut[index]] = true;
            //next city
            index++;
        }
        return permut;
    }

    private static int getNearest(int i) {
        int index = -1;
        double value = Double.MAX_VALUE;
        for (int j = 0; j < _cost.length; j++) {
           if( !processed[j] && _cost[i][j] < value){
               value = _cost[i][j];
               index =j;
           }            
        }
        return index;
    }
    
    
    public static void main(String[] args) {
           TSP tsp = new Ulysses16();
           System.out.println(tsp);
           tsp.genome = calculate(TSP._distance);
           tsp.evaluate();
           System.out.println(tsp);
           
           TSP s = new Simples();
           System.out.println(s);
           s.genome = calculate(TSP._distance);
           s.evaluate();
           System.out.println(s);
           
           
    }
}
