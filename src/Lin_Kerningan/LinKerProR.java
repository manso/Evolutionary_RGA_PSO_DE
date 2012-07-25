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
package Lin_Kerningan;

import java.io.IOException;
import java.util.Random;

/**
 *
 * @author ZULU
 */
public class LinKerProR // 2-opt, 3-opt?C4-opt‚?“K??‚?Š„?‡‚??¬‚?‚?‚?,Š„?‡‚?Ž©“®“I‚É?¶?¬‚·‚é?A??–?•”•?‚ÍLinKerProR‚??©‚Ä‚­‚?‚?‚?   
{

    public static int n = 13, runs = 2000;
    public static final int[] a = new int[n];
    public static final int[] c = new int[n];
    public static final double[][] result = new double[63][20];
    private static Random rand = new Random();
    LookFor lookfor = new LookFor();

    public static void main(String[] args) throws NumberFormatException, IOException {
        int Norandom1 = 0, Norandom2 = 0, Norandom3 = 0, NUMB = 0;//Šm—¦   

        for (Norandom1 = 0; Norandom1 < 10; Norandom1++) {
            for (Norandom2 = 0; Norandom2 < 10; Norandom2++) {
                for (Norandom3 = 0; Norandom3 < 10; Norandom3++) {
                    if (Norandom1 + Norandom2 + Norandom3 == 10) {

                        System.out.println("2-Opt " + Norandom1);
                        System.out.println("3-Opt " + Norandom2);
                        System.out.println("4-Opt " + Norandom3 + "  " + NUMB);

                        for (int k = 0; k < 20; k++)//20‰?‚?Ž??±   
                        {
                            double precost = 0, cost = 100000;
                            long start = System.currentTimeMillis();

                            for (int i = 0; i < n; i++)//Š???’n?‡”Ô‚?random‚??¶?¬   
                            {
                                a[i] = i;
                            }
                            int[] b = new int[n];
                            boolean[] picked = new boolean[a.length];
                            for (int i = 0; i < a.length; i++) {
                                int t;
                                do {
                                    t = rand.nextInt(a.length);
                                } while (picked[t]);
                                b[i] = a[t];
                                c[i] = b[i];
                                picked[t] = true;
                            }

                            for (int j = 0; j < runs; j++)//generation‚??¶?¬‚·‚é‰??”   
                            {
                                for (int l = 0; l < c.length; l++) {
                                    b[l] = c[l];
                                }

                                if (j >= runs * Norandom1 / 10) {
                                    precost = new LinKerProR().selection(2, b);//2-Opt   
                                } else if (j >= runs * (10 - Norandom3) / 10) {
                                    precost = new LinKerProR().selection(4, b);//4-Opt   
                                } else {
                                    precost = new LinKerProR().selection(3, b);//3-Opt   
                                }

                                if (precost < cost) {
                                    cost = precost;
                                    for (int m = 0; m < b.length; m++) {
                                        c[m] = b[m];
                                    }
                                }
                            }
                            long end = System.currentTimeMillis();
                            result[NUMB][k] = cost;
                        }
                        NUMB++;

                    }
                }
            }
        }
        new LinKerProR().Evaluate(NUMB, result);
    }

    private void changeArry(int m, int[] arry) {
        for (int g = 0; g < m; g++) {
            int change = arry[0];
            int A = arry.length - 1;

            for (int j = 0; j < A; j++) {
                arry[j] = arry[j + 1];
            }
            arry[A] = change;
        }
    }

    private double selection(int m, int[] arry) throws NumberFormatException, IOException {
        int input1 = 0;
        int input2 = 0;
        double Dsum = 0;

        input2 = rand.nextInt(arry.length);
        new LinKerProR().changeArry(input2, arry);


        for (int time = 0; time < m - 1; time++) {
            do {
                input1 = rand.nextInt(arry.length);
            } while (input1 == 0 || input1 == 1 || input1 == arry.length - 1);

            new LinKerProR().change(input1, arry);
        }
        return Dsum = lookfor.DistanceSum(arry);
    }

    private void change(int m, int[] arry) {
        int change = 0;
        for (int j = 0; j < (m + 1) / 2; j++) {
            change = arry[j];
            arry[j] = arry[m - j];
            arry[m - j] = change;
        }
    }

    private void Input(int[] arry) {
        for (int i = 0; i < arry.length; i++) {
            System.out.print(arry[i] + " ");
        }
        System.out.println();
    }

    private void Evaluate(int l, double[][] arry) {
        for (int i = 0; i < l; i++) {
            int m = 0;
            double distancepoint = 0;
            System.out.println(i);
            System.out.print("20‰?‚?”­?©‚µ‚??‹‰?: ");
            for (int j = 0; j < 20; j++) //////////   
            {
                System.out.print(arry[i][j] + "  ");
                distancepoint = distancepoint + arry[i][j] / 20;//////////   
                if (arry[i][j] == 41734.0) {
                    m++;
                }
            }
            System.out.println();
            System.out.println("??“K?‹‰?‚?”­?©‚·‚é‰??”: " + m);
            System.out.println("•?‹?‹——?: " + distancepoint);
        }
    }
}
