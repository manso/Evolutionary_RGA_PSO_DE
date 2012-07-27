/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evolutionary.Genetics.Selection;

import Evolutionary.Genetics.Selection.Selection;
import Evolutionary.Population.Population;
import Evolutionary.Problems.Individual;

/**
 *
 * @author ZULU
 */
public class Tournament extends Selection {

    int SIZE_OF_TOURNAMENT = 2;

    @Override
    public Population execute(Population parents, int size) {
        Population selected = new Population();

        for (int i = 0; i < size; i++) {
            Individual best = parents.getRandom();
            for (int j = 1; j < SIZE_OF_TOURNAMENT; j++) {
                Individual pivot = parents.getRandom();
                if (pivot.compareTo(best) > 0) {
                    best = pivot;
                }
            }
            selected.addIndividual(best);
        }
        return selected;
    }

    public void setParams(String params) {
        try {
            String[] p = params.split(" ");
            int tourn = Integer.parseInt(p[0]);
            SIZE_OF_TOURNAMENT = tourn > 0 ? tourn : SIZE_OF_TOURNAMENT;
        } catch (Exception e) {
            //do nothing
        }
    }
}
