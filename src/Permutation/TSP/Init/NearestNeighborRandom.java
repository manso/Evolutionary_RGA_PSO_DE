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

import Permutation.TSP.DSJ_1000;
import Permutation.TSP.Simples;
import Permutation.TSP.TSP;
import Permutation.TSP.Ulysses16;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Traveling Salesman Problem:A Case Study in Local Optimization David S.
 * Johnson Lyle A. McGeoch 1995 2.2. Four Important Tour Construction Algorithms
 * -Nearest Neighbor
 *
 * @author ZULU
 */
public class NearestNeighborRandom {

    static Random random = new Random();
    static double[][] _cost;
    static int[] permut;
    static boolean[] processed;

    public static int[] calculate(double[][] cost) {
        ArrayList<Integer> lst = new ArrayList<>();
        _cost = cost;
        permut = new int[cost[0].length];
        processed = new boolean[permut.length];
        //first city - Random
        lst.add(random.nextInt(permut.length));
        processed[lst.get(0)] = true;
        int last = lst.get(0);
        int first = lst.get(0);
        for (int i = 0; i <permut.length-1; i++) {           
            //calculate nearest city in the begin            
           int left = getNearest(first);
           double costLeft = cost[left][first];
           //calculate nearest city in the end            
           int right = getNearest(last);
           double costRight = _cost[right][last];
           
           if( random.nextDouble() < costRight/(costLeft+costRight)){
               lst.add(0, left);
               first = left;
               processed[first]=true;
           }else{
                lst.add(right);
               last = right;
               processed[right]=true;
           }
           
        }
        for (int i = 0; i < permut.length; i++) {
            permut[i] = lst.get(i);            
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
           
           TSP d1000 = new DSJ_1000();
           System.out.println(d1000);
           d1000.genome = calculate(TSP._distance);
           d1000.evaluate();
           System.out.println(d1000);
           
           
           
    }
}
