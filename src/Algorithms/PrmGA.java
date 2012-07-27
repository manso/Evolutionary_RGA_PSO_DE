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
package Algorithms;

import Evolutionary.Genetics.Recombination.PMX;
import Problem.Permutation.Problem;
import Problem.Permutation.TSP.Ulysses16;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author ZULU
 */
public class PrmGA {
     protected static Random rnd = new Random();

    public static ArrayList<Problem> createRandom(Problem template, int size) {
        ArrayList<Problem> pop = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Problem newProblem = template.getClone();
            newProblem.fillRandom();
            pop.add(newProblem);
        }
        return pop;
    }

    public static ArrayList<Problem> tournament(ArrayList<Problem> p, int size) {
        ArrayList<Problem> selects = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Problem p1 = p.get(rnd.nextInt(p.size()));
            Problem p2 = p.get(rnd.nextInt(p.size()));
            if (p1.compareTo(p2) > 0) {
                selects.add(p1.getClone());
            } else {
                selects.add(p2.getClone());
            }
        }
        return selects;
    }

    public static ArrayList<Problem> CrossoverPMX(ArrayList<Problem> parents, double probability) {
        //new population to crossover individuals
        ArrayList<Problem> newChildren = new ArrayList<>();
       while( parents.size() > 1) {
           //make a clone of the individual
           if( rnd.nextDouble() > probability){
               newChildren.add(parents.remove(rnd.nextInt(parents.size())));
               continue;
           }
            //select random parents
            Problem c1 = parents.remove(rnd.nextInt(parents.size()));
            Problem c2 = parents.remove(rnd.nextInt(parents.size()));
            //execute PMX
            PMX.execute(c1.genome, c2.genome);            
            c1.evaluate();
            c2.evaluate();
            //add new individuals to the children population
            newChildren.add(c1);
            newChildren.add(c2);
        }
        //parents who did not reproduce
        newChildren.addAll(parents);
        //return crossover population
        return newChildren;
    }

    public static ArrayList<Problem> mutationInversion(ArrayList<Problem> population, double probability) {
        for (Problem p : population) {
            //change the genes using Gaussian distribution
            for (int gene = 0; gene < p.getNumberOfGenes(); gene++) {
                //verify the probability of mutatation
                if (rnd.nextDouble() < probability) {
                    //first cut
                    int cut1 = gene;
                    //second cut
                    int cut2 = gene + rnd.nextInt(p.getNumberOfGenes() - gene );
//                    while( cut1 < cut2){
                        //swap genes
                        int aux = p.genome[cut1];
                        p.genome[cut1] = p.genome[cut2];
                        p.genome[cut2]=aux;
//                        //advance cuts
//                        cut1++;
//                        cut2--;
//                    }
                }
            }
            p.evaluate();
        }
        //return population mutated
        return population;
    }

    protected ArrayList<Problem> replacement(ArrayList<Problem> parents, ArrayList<Problem> children) {
        ArrayList<Problem> newPopulation = new ArrayList<>();
        //size of the original population
        int size = parents.size();
        //join parents and childrens
        parents.addAll(children);
        //Sort Population
        Collections.sort(parents);
        Collections.reverse(parents);
        //select best individuals
        for (int i = 0; i < size; i++) {
            newPopulation.add(parents.get(i));            
        }
        return newPopulation;
    }

    public Problem solve(int Iterations, int sizeOfPopulations, Problem problem) {
        ArrayList<Problem> parents;
        ArrayList<Problem> children;
        //create random population
        parents = createRandom(problem, sizeOfPopulations);
        for (int i = 0; i < Iterations; i++) {
            //select the same number of parents
            children = tournament(parents, parents.size());
            //execute crossover with 75% of probability
            children = CrossoverPMX(children, 0.5);
            //execute mutationInversion with 25% of probability
            children = mutationInversion(children, 1.0/ problem.getNumberOfGenes());
            //join parents and childrens
            parents = replacement(parents, children);
            //display best individual
            Collections.sort(parents);
            System.out.println("Iteration : " + i + " = " + parents.get(parents.size() - 1));
        }
        //Sort Population
        Collections.sort(parents);
        //returns best individual (last)
        return parents.get(parents.size() - 1);
    }

    public static void main(String[] args) {
        Ulysses16 u = new Ulysses16();
        PrmGA ga = new PrmGA();
        ga.solve(100, 100000, u);
      
    }
}
