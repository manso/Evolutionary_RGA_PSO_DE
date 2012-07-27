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
package Problem.Permutation.TSP;

import Problem.Permutation.Problem;
import java.util.Arrays;

/**
 *
 * @author ZULU
 */
public class TSP extends Problem {

    static int[] _optimum;
    static double _optimumValue;
    public static double[] _x;
    public static double[] _y;
    public static double[][] _distance;


    public TSP(double[] xx, double[] yy, int[] best) {
        super(xx.length);
        initialize(xx, yy, best);
        evaluate();

    }

    @Override
    protected double calculateFunction() {
        return calculateFunction(_distance, genome);
    }

    public static void initialize(double[][] xy, int[] best) {
        initialize(xy[0], xy[1], best);
    }

    public static void initialize(double[] xx, double[] yy, int[] best) {
        //already computed
        if (_x == xx && _y == yy) {
            return;
        }
        //initialize matrix
        _optimum = best;
        _x = xx;
        _y = yy;
        _distance = new double[_x.length][_x.length];
        for (int i = 0; i < _distance.length; i++) {
            for (int j = 0; j < _distance.length; j++) {
                double dx = _x[j] - _x[i];
                double dy = _y[j] - _y[i];
                _distance[i][j] = Math.sqrt(dx * dx + dy * dy);
                // System.out.printf("\t%4.2f" , _distance[_y][_x] );
            }
            //  System.out.println("");
        }
        _optimumValue = calculateFunction(_distance, _optimum);
    }

    public static double calculateFunction(double[][] distance, int[] genes) {
        double lenght = 0;
        for (int i = 0; i < genes.length; i++) {
            lenght += distance[genes[i]][genes[(i + 1) % genes.length]];
        }
        return lenght;
    }
    
    @Override
    public String toString() {
        if( functionValue > _optimumValue)
            return super.toString();
        else
            return super.toString() + " <BEST>";  
    }
}
