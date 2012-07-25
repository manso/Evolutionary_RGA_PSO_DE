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
package Permutation.TSP;
/**
 *
 * @author ZULU
 */
public class Simples extends TSP{
    
   static int[] optimum = {0, 1, 2, 3, 4, 5};
    static double[] x = {0,0,1,2,2,1};
    static double[] y = {2,1,0,1,2,3};

    public Simples() {
        super(x, y, optimum);
    }

}
