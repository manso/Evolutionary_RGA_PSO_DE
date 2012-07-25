
import Evolutionary.Population.Population;
import Evolutionary.Problems.MaxOnes;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ZULU
 */
public class TestPopulation {

    public static void main(String[] args) {
        Population p = new Population();

        p.initialize(new MaxOnes(), 5);
        p.sort();
        System.out.println("POPULATION: \n" + p);

        Population p1 = (Population) p.clone();
        System.out.println("POPULATION2: \n" + p);

        p.initialize(new MaxOnes(), 10);
        p.sort();
        System.out.println("POPULATION: \n" + p);


    }
}
