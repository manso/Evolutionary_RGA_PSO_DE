/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evolutionary.Genetics.Replacement;

import Evolutionary.Population.Population;

/**
 *
 * @author ZULU
 */
public abstract class Replace {
    public abstract Population execute( Population parents, Population children);
    public void setParams(String params){
        //do nothing
    }
}
