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
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author ZULU
 */
public class Gready {

    static Random random = new Random();
    static double[][] _cost;
    static int[] permut;
    static int[] processed;
    static ArrayList<Integer> lst;

    public static int[] calculate(double[][] cost) {
        _cost = cost;
        permut = new int[cost[0].length];
        processed = new int[permut.length];
        lst = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        while (true) {
            Edge min = getMinimumEdge();
            if (min == null) {
                break;
            }
            //infinity cost
            _cost[min.p1][min.p2] = Double.MAX_VALUE;
            _cost[min.p2][min.p1] = Double.MAX_VALUE;

            processed[min.p1]++;
            processed[min.p2]++;

            if (!lst.contains(min.p1)) {
                lst.add(min.p1);
            }
            if (!lst.contains(min.p2)) {
                lst.add(min.p2);
            }
            edges.add(min);
        }
        System.out.println(edges);
        buildPath(edges);
        System.out.println("PERMUT FINAL: " + Arrays.toString(permut));
        return permut;
    }

    private static void buildPath(ArrayList<Edge> lst) {
        Edge nextEdge = lst.remove(0);
        repairCost(nextEdge);
        permut[0] = nextEdge.p1;
        permut[1] = nextEdge.p2;
        int index = 1;
        while (lst.size() > 1) {
            System.out.println("PERMUT: " + Arrays.toString(permut) + " EDGES " + lst);
            for (Edge e : lst) {
                nextEdge = e;
                if (e.p1 == permut[index]) {
                    index++;
                    permut[index] = e.p2;
                    break;
                }
                if (e.p2 == permut[index]) {
                    index++;
                    permut[index] = e.p1;
                    break;
                }
            }
            //repair cost
            repairCost(nextEdge);
            System.out.println("PERMUT: " + Arrays.toString(permut) + "REMOVE " + nextEdge);
            lst.remove(nextEdge);
        }
        repairCost(lst.remove(0));

    }

    private static void repairCost(Edge edge) {
        _cost[edge.p1][edge.p2] = edge.cost;
        _cost[edge.p2][edge.p1] = edge.cost;
    }

    public static Edge getMinimumEdge() {
        int x = -1;
        int y = -1;
        double minValue = Double.MAX_VALUE;
        for (int i = 0; i < _cost.length; i++) {
            for (int j = 0; j < _cost.length; j++) {
                //edge processed
                if (i == j || processed[i] > 1 || processed[j] > 1) {
                    continue;
                }
//                if(lst.contains(i) && lst.contains(j))
//                    continue;
                if (_cost[i][j] < minValue) {
                    minValue = _cost[i][j];
                    x = i;
                    y = j;
                }
            }
        }
        if (x >= 0) {
            return new Edge(x, y);
        } else {
            return null;
        }
    }

    static class Edge {

        int p1;
        int p2;
        double cost;

        public Edge(int p1, int p2) {
            this.p1 = p1;
            this.p2 = p2;
            cost = _cost[p1][p2];
        }

        public String toString() {
            return p1 + " == " + p2;
        }
    }

    public static void main(String[] args) {
        TSP s = new Ulysses16();
        System.out.println("RANDOM \t" + s);

        s.genome = calculate(TSP._distance);
        s.evaluate();
        System.out.println("GREADY \t" + s);
    }
}
