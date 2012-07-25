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
import java.util.StringTokenizer;

/**
 *
 * @author ZULU
 */
public class inputCSV {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader(new File("testfile.csv")));

        String str = null;

        Forecast[] forecasts = new Forecast[14];

        for (int i = 0; i < 14; i++) {
            forecasts[i] = new Forecast();
        }

        int classNumber = 0;

        while ((str = reader.readLine()) != null) {
            int num = 0;
            StringTokenizer st_token = new StringTokenizer(str, ",");
            while (st_token.hasMoreTokens()) {

                String tempStr = st_token.nextToken();

                switch (num) {
                    case 0:
                        int a = Integer.parseInt(tempStr);
                        forecasts[classNumber].timespan = Integer.parseInt(tempStr);
                        break;
                    case 1:
                        forecasts[classNumber].fine = Double.valueOf(tempStr);
                        break;
                    case 2:
                        forecasts[classNumber].cloud = Double.valueOf(tempStr);
                        break;
                    case 3:
                        forecasts[classNumber].rain = Double.valueOf(tempStr);
                        break;

                }
                num++;
            }
            classNumber++;
        }

        for (int i = 0; i < 14; i++) {
            System.out.println(i + "”Ô–Ú‚ÉŠi”[‚µ‚??N?‰?X‚??f?[?^");
            System.out.println(forecasts[i].timespan);
            System.out.println(forecasts[i].fine);
            System.out.println(forecasts[i].cloud);
            System.out.println(forecasts[i].rain);
            System.out.println("------------------------------------");
        }
    }
}

class Forecast {

    double fine;//forecast.csv‚?’†‚Éfine‰?‚??”‚?‘Î‰ž‚·‚é   
    double rain;// forecast.csv‚?’†‚Érain‰?‚??”‚?‘Î‰ž‚·‚é   
    double cloud;//forecast.csv‚?’†‚Écloud‰?‚??”‚?‘Î‰ž‚·‚é   
    int timespan;// forecast.csv‚?’†‚Étimespan‰?‚??”‚?‘Î‰ž‚·‚é   
}

