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

/**
 *
 * @author ZULU
 */
public class SpotData implements java.lang.Comparable {

    int number;//Š???’n‚?”Ô?†   
    double pref;// ?d—v“x   
    double point;//–‘«“x   
    double time;// ?€—?ŠÔ    
    double starttime;
    double stoptime;
    String name;// Š???’n‚?–?‘O   
    String attribute;// Š???’n‚?‘®?«   

    SpotData(int b, double p, double i, double t, String n, String a, double m, double e) {
        number = b;
        pref = p;
        point = i;
        time = t;
        name = n;
        attribute = a;
        starttime = m;
        stoptime = e;
    }

    SpotData(int b, double i, String n, double e, double m, String a) {
        number = b;
        point = i;
        name = n;
        stoptime = e;
        starttime = m;
        attribute = a;
    }

    public SpotData() {
        this.number = 0;
        this.pref = 0;
        this.point = 0;
        this.time = 0;
        this.starttime = 0;
        this.stoptime = 0;
        this.name = null;
        this.attribute = null;

    }

    public String toString() {
        return number + " " + pref + "  " + point + "  " + time + "  " + name + "  " + attribute + "  " + starttime + " " + stoptime + " ";
    }

    public int compareTo(Object obj) {
        if (obj instanceof SpotData) {
            SpotData std = (SpotData) obj;
            if (this.name == std.name) {
                return 0;
            }
            if (this.point > std.point) {
                return 1;
            }
        }
        return -1;
    }
}
