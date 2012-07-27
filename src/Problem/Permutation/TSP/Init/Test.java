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

import Problem.Permutation.TSP.DSJ_1000;
import Problem.Permutation.TSP.PCB_3000;
import Problem.Permutation.TSP.TSP;

/**
 *
 * @author ZULU
 */
public class Test {

    public static void main(String[] args) {
        
        TSP tsp = new DSJ_1000();
        TSP x2 = (TSP) tsp.getClone();
        TSP x3 = (TSP) tsp.getClone();
        
        System.out.println("RANDOM \t" + tsp);

        tsp.genome = NearestNeighbor.calculate(TSP._distance);
        tsp.evaluate();
        System.out.println("NN  \t" + tsp);

        tsp.genome = NearestNeighborDual.calculate(TSP._distance);
        tsp.evaluate();
        System.out.println("NND \t" + tsp);


        tsp.genome = NearestNeighborRandom.calculate(TSP._distance);
        tsp.evaluate();
        System.out.println("NNR \t" + tsp);

        tsp.genome = GreadyInsertion.calculate(TSP._distance);
        tsp.evaluate();
        System.out.println("GI \t" + tsp);



//        tsp.genome = GreadyInsertionMinimum.calculate(TSP._distance);
//        tsp.evaluate();
//        System.out.println("GIM \t" + tsp);
//
//
//        tsp.genome = GreadyInsertionMinimum_vertex.calculate(TSP._distance);
//        tsp.evaluate();
//        System.out.println("GIV \t" + tsp);
//
//        TSP x = (TSP) tsp.getClone();
//        Opt2.search(TSP._distance, x.genome);
//        x.evaluate();
//        System.out.println("2-Opt \t" + x);
        
         
        Opt2.heuristics2optOriginal(TSP._distance, x2.genome);
        x2.evaluate();
        System.out.println("2-Opt ORIG \t" + x2);
        
         Opt2.LS_2_opt(TSP._distance, x3.genome);
        x3.evaluate();
        System.out.println("2-Opt OPT \t" + x3);
        
        
        Opt2.LS_2_opt(TSP._distance, tsp.genome);
        tsp.evaluate();
        System.out.println("2-Opt NEW \t" + tsp);




    }
}
