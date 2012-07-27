/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evolutionary.Genetics.Selection;

import Evolutionary.Population.Population;

/**
 *
 * @author ZULU
 */
public abstract class Selection {
    public abstract Population execute( Population parents, int size);
    public void setParams(String params){
        //do nothing
    }
}
