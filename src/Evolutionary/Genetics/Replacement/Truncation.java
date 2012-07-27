/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evolutionary.Genetics.Replacement;

import Evolutionary.Population.Population;
import Evolutionary.Problems.Individual;
import java.util.Iterator;

/**
 *
 * @author ZULU
 */
public class Truncation extends Replace {

    @Override
    public Population execute(Population parents, Population children) {
        int size = parents.size();
        Population newPop = new Population();
        
        children.append(parents);
        //append best childrens
        Iterator<Individual> it = children.getSortedIterator();
        while (newPop.size() < size && it.hasNext()) {
            newPop.addIndividual(it.next());
        }
       
        return newPop;
    }
}
