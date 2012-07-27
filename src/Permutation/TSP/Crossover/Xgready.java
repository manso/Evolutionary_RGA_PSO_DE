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
package Permutation.TSP.Crossover;

import Problem.Permutation.TSP.Simples2;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author ZULU
 */
public class Xgready {

    /**
     * In short, algorithm takes first city from parent1 and looks for the way
     * from this first city in both, parent1 and parent2. then it uses the
     * better next city
     *
     * @see org.jgap.impl.GreedyCrossover
     * @param chromosome1 - first chromosome
     * @param chromosome2 - second chromosome
     * @return newly ordered array of cities (=child =new chromosome)
     */
    public static int[] crossover(double[][] cost, int[] chromosome1, int[] chromosome2) {
        Random rnd = new Random();
        int[] c1 = chromosome1;
        int[] c2 = chromosome2;

        int lenght = c1.length;
        //first parent
        LinkedList<Integer> p1 = new LinkedList<>();
        //second parent
        LinkedList<Integer> p2 = new LinkedList<>();
        //children
        LinkedList<Integer> children = new LinkedList<>();
        //copy cities to parents
        for (int j = 0; j < lenght; j++) { // g[0] picked
            p1.add(c1[j]);
            p2.add(c2[j]);
        }
        //include first random city in the children
        children.add(p1.get(rnd.nextInt(c1.length)));

        //insert cheapest city from the last one
        while (p1.size() > 1) {
            //select last city
            int pivot = children.getLast();
            //select next city of parents
            int city1 = findNext(p1, pivot);
            int city2 = findNext(p2, pivot);
            //select cheapes edge
            if (cost[pivot][city1] < cost[pivot][city2]) {
                children.add(city1);
            } else {
                children.add(city2);
            }
            //remove pivot from parents
            p1.removeFirstOccurrence(pivot);
            p2.removeFirstOccurrence(pivot);
        }


        int[] rets = new int[c1.length];
        for (int i = 0; i < rets.length; i++) {
            rets[i] = children.get(i);
        }
        return rets;
    }

    /**
     * Helper for GreedyCrossover getChild() algorithm. It finds the next city
     * after city "x" in the chromosome "cities"
     *
     * @param cities - array in which to find the next city after city "x"
     * @param x - city for which we are looking for the next path
     * @return next city to go from the chromosome
     */
    protected static int findNext(LinkedList<Integer> cities, int x) {
        Iterator<Integer> it = cities.iterator();
        while (it.hasNext()) {
            if (it.next() == x && it.hasNext()) {
                return it.next();
            }
        }
        //from the last city we go to the first one
        return cities.getFirst();
    }

    public static void main(String[] args) {
        int[] g1 = {0, 3, 2, 1, 4};
        Simples2 p1 = new Simples2();
        p1.genome = g1;
        p1.evaluate();


        int[] g2 = {0, 2, 4, 1, 3};
        Simples2 p2 = new Simples2();
        p2.genome = g2;
        p2.evaluate();

        System.out.println("P1 :" + p1);
        System.out.println("P2 :" + p2);

        for (int i = 0; i < 10; i++) {
            Simples2 p3 = new Simples2();
            p3.genome = crossover(Simples2._distance, g1, g2);
            p3.evaluate();
            System.out.println("P3 :" + p3);
        }


    }
}
