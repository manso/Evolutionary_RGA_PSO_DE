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
 *
 * @author ZULU
 */
public class Opt2 {
//    static Simples2 ind = new Simples2();

    static Random random = new Random();
    static double[][] _cost;

    public static void reverse(int[] genes, int min, int max) {
        min = (min + genes.length) % genes.length;
        max = (max + genes.length) % genes.length;
        if (min > max) {
            reverse(genes, max, min);
        }
        while (min < max) {
            int aux = genes[min];
            genes[min] = genes[max];
            genes[max] = aux;
            min++;
            max--;
        }
    }

    private static double getLenght(int[] genome, int y, int x) {
        return _cost[genome[y % _cost.length]][genome[x % _cost.length]];
    }

    /**
     * p1 o o p2 |\ / | | x | |/ \| p1+1 o o p2+1
     *
     * @param cost
     * @param genome
     */
    public static void search(double[][] cost, int[] genome) {
        _cost = cost;
        boolean done = false;
        int radius = genome.length - 2; //Math.min(20, genome.length / 10 + 2);
        while (!done) {
            done = true;
            for (int position = 0; position < genome.length - 2; position++) {
                for (int k = 0; k < radius; k++) {
                    int index = position + 2 + k;
                    //while (index < genome.length ) {
                    double d1 = getLenght(genome, position, position + 1);
                    double d2 = getLenght(genome, index, index + 1);

                    double d3 = getLenght(genome, position, index);
                    double d4 = getLenght(genome, position + 1, index + 1);

                    if (d1 + d2 > d3 + d4) {
                        reverse(genome, position + 1, index);
                        done = false;
                    } else {
                        index++;
                    }
                }//for
            }//for
            System.out.println("XXXValue" + evaluate(genome));
        }//while ! done

    }

    public static void LS_2_opt(double[][] cost, int[] genome) {
        _cost = cost;
        
        boolean done = false;
        while( !done) {
            done = true;
            for (int p1 = 0; p1 < genome.length; p1++) {
                for (int p2 = p1 + 2; p2 < genome.length; p2++) {
                    double d1 = getLenght(genome, p1, p1 + 1);
                    double d2 = getLenght(genome, p2, p2 + 1);
                    double d3 = getLenght(genome, p1, p2);
                    double d4 = getLenght(genome, p1 + 1, p2 + 1);
                    if (d1 + d2 > d3 + d4) {
                        reverse2(genome, p1 + 1, p2);
                        done = false;
                    }
                }
            }
//            System.out.println("XXXValue" + evaluate(cities));
        }
    }
    
        public static void heuristics2optOriginal(double[][] cost, int[] cities) {
        _cost = cost;
        
        boolean done = false;
        int count = cities.length;
        for (int k = 0; k < count && !done; k++) {
            done = true;
            for (int i = 0; i < count; i++) {
                for (int j = i + 2; j < count; j++) {
                    double d1 = getLenght(cities, i, (i + 1) % count);
                    double d2 = getLenght(cities, j, (j + 1) % count);

                    double d3 = getLenght(cities, i, j);
                    double d4 = getLenght(cities, (i + 1) % count, (j + 1) % count);
                    if (d1 + d2 > d3 + d4) {
                        int tmp = cities[(i + 1) % count];
                        cities[(i + 1) % count] = cities[j];
                        cities[j] = tmp;
                        reverse2(cities, i + 2, j - 1);
                        done = false;
                    }
                }
            }
//            System.out.println("XXXValue" + evaluate(cities));
        }
    }

    public static void reverse2(int[] cities, int startIndex, int stopIndex) {
        if (startIndex >= stopIndex || startIndex >= cities.length || stopIndex < 0) {
            return;
        }
       while(startIndex < stopIndex) {
            int tmp = cities[startIndex];
            cities[startIndex] = cities[stopIndex];
            cities[stopIndex] = tmp;
            startIndex++;
            stopIndex--;
        }

    }

    private static double evaluate(int[] lst) {
        double value = 0.0;
        for (int i = 0; i < lst.length; i++) {
            value += _cost[lst[i]][lst[(i + 1) % lst.length]];

        }
        return value;
    }

    public static void main(String[] args) {
        int[] v = {0, 2, 4, 1, 3};
        Simples2 i = new Simples2();

        i.genome = v;
        System.out.println("i = " + i);
        search(TSP._distance, i.genome);
        i.evaluate();
        System.out.println("i = " + i);


    }
}
