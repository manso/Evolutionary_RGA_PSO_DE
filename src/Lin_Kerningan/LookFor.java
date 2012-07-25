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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.TreeSet;

/**
 *
 * @author ZULU
 */
public class LookFor {

    public double DistanceSum(int[] data) throws NumberFormatException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader(new File("Spot.csv")));

        String str = null;
        int SpotNum = 13;//Š???’n‚??”   

        SpotData[] Spot = new SpotData[SpotNum];
        for (int i = 0; i < SpotNum; i++) {
            Spot[i] = new SpotData();
        }

        int classNumber = 0;

        while ((str = reader.readLine()) != null) {
            int num = 0;
            StringTokenizer st_token = new StringTokenizer(str, ",");
            while (st_token.hasMoreTokens()) {

                String tempStr = st_token.nextToken();

                switch (num) {
                    case 0:
                        Spot[classNumber].number = Integer.parseInt(tempStr);
                        break;
                    case 1:
                        Spot[classNumber].name = String.valueOf(tempStr);
                        break;
                }
                num++;
            }
            classNumber++;
        }//CSV.file‚©‚ç?f?[?^‚?“Ç‚Ý?ž‚?   


        SpotData oo = new SpotData();
        SpotData ro = new SpotData();
        SpotData rf = new SpotData();

        DistancePoint distancePT = new DistancePoint();

        TreeSet ts = new TreeSet();
        LinkedList ml = new LinkedList();
        LinkedList ll = new LinkedList();

        double sum = 0, rr = 0;

        for (int m = 0; m < Spot.length; m++) {
            ll.add(Spot[m]);
        }

        for (int i = 0; i < data.length; i++)//Š???’n‚?”Ô?†‚?“Ç‚Ý?ž‚?   
        {
            for (int m = 0; m < Spot.length; m++) {
                rf = (SpotData) ll.get(m);
                if (data[i] == rf.number) {
                    ml.add(rf);
                }
            }
        }

        for (int n = 0; n < ml.size() - 1; n++)//‘?‚?ŠÔ‚?‹——?   
        {
            ro = (SpotData) ml.get(n);
            oo = (SpotData) ml.get(n + 1);
            rr = distancePT.Pdistance(ro.number, oo.number);
            sum = sum + rr;
        }
        ro = (SpotData) ml.get(ml.size() - 1);//”ö‚?“?‚?‹——?   
        oo = (SpotData) ml.get(0);
        sum = sum + distancePT.Pdistance(ro.number, oo.number);//??‚Â‚??o?H‚?‹——?‘??a   
        return sum;
    }
}
