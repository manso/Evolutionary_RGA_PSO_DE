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
import Problem.Permutation.TSP.Ulysses16;
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
public class GreadyInsertion {

    static Random random = new Random();
    static double[][] _cost;
    static int[] permut;
    static boolean[] processed;

    public static int[] calculate(double[][] cost) {
        _cost = cost;
        permut = new int[cost[0].length];
        //first city - Random
        ArrayList<Integer> lst = new ArrayList<>();
        ArrayList<Integer> rndCitys = new ArrayList<>();
        for (int i = 0; i < permut.length; i++) {
            rndCitys.add(i);
        }
        Collections.shuffle(rndCitys);
        while (!rndCitys.isEmpty()) {
            //remove last for efficiency
            int city = rndCitys.remove(rndCitys.size() - 1);
            insertCityGreedy(lst, city);
        }
        for (int i = 0; i < permut.length; i++) {
            permut[i] = lst.get(i);
        }
        return permut;
    }

    private static void insertCityGreedy(ArrayList<Integer> lst, Integer city) {
        if (lst.size() < 3) {
            lst.add(city);
            return;
        }
        double oldLengh = evaluate(lst);
        int index = 0;
        double minLenght = Double.MAX_VALUE;
        for (int i = 0; i < lst.size(); i++) {
            int x1 = lst.get(i % lst.size());
            int x2 = lst.get((i + 1) % lst.size());
            double edge = _cost[x1][x2];
            double newLenght = oldLengh - edge + _cost[x1][city] + _cost[city][x2];
            if (minLenght > newLenght) {
                minLenght = newLenght;
                index = i;
            }
        }
        lst.add((int) index + 1, city);
    }

    private static double evaluate(ArrayList<Integer> lst) {
        double value = 0.0;
        for (int i = 0; i < lst.size(); i++) {
            value += _cost[lst.get(i)][lst.get((i + 1) % lst.size())];

        }
        return value;
    }

    public static void main(String[] args) {
        int[] v = {0, 2, 4, 1, 3};
        Simples2 i = new Simples2();

        i.genome = v;
        System.out.println("i = " + i);
        i.genome = calculate(TSP._distance);
        i.evaluate();
        System.out.println("i = " + i);

        TSP s = new Ulysses16();
        System.out.println("RANDOM \t" + s);

        s.genome = calculate(TSP._distance);
        s.evaluate();
        System.out.println("GREADY \t" + s);
    }
}
