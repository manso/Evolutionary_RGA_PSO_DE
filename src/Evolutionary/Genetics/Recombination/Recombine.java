/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evolutionary.Genetics.Recombination;

import Evolutionary.Population.Population;

/**
 *
 * @author ZULU
 */
public abstract class Recombine {
    public abstract Population execute( Population parents, int size);
    public void setParams(String params){
        //do nothing
    }
    
}
