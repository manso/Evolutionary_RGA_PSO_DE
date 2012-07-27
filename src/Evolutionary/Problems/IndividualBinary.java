/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Evolutionary.Problems;

import java.lang.reflect.Constructor;
import java.util.logging.Level;
import java.util.logging.Logger;
import Evolutionary.Problems.Individual;
import java.util.ArrayList;

/**
 *
 * @author ZULU
 */
public abstract class IndividualBinary extends Individual<Boolean> {
    //number of genes
    private int size ;
    
    public IndividualBinary(int n, boolean optimization) {
        super(optimization);
        this.size = n;
        initialize();
        
    }
    
    public void initialize(){
        chromossom.clear();
        for (int i = 0; i < size; i++) {
            chromossom.add(rnd.nextBoolean());            
        }
        isEvaluated = false;
        evaluate();
    }
    
    public void set(boolean []genes){
        chromossom = new ArrayList<>();
        for (int i = 0; i < genes.length; i++) {
           chromossom.add(genes[i]);            
        }        
    }
    
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(this.getClass().getSimpleName() + "(");
        for (int i = 0; i < chromossom.size(); i++) {
            if (chromossom.get(i)) {
                str.append("1");
            } else {
                str.append("0");
            }
        }
        str.append(")=");
        if( isEvaluated)
            str.append(functionValue);
        else
            str.append("NOT_EVALUATED");
        return str.toString();
    }
    
    @Override
    public Object clone() {
        try {
            //get default constructor
            Constructor co = this.getClass().getConstructor();
            //make tmp object with default constructor
            Object[] objectParam = {}; //type of optimization
            Individual tmp = (Individual) co.newInstance(objectParam);
            tmp.chromossom.clear();
            //deep copy of genes
            for (int i = 0; i < chromossom.size(); i++) {
                if(chromossom.get(i))
                    tmp.chromossom.add(true);
                else
                    tmp.chromossom.add(false);
            }
            //copy fitness
            tmp.functionValue = functionValue;
            tmp.isEvaluated = isEvaluated;
            return tmp;
        } catch (Exception ex) {
            Logger.getLogger(Individual.class.getName()).log(Level.SEVERE, null, ex);
        }
        //something wrong appens
        return null;
    }
    
}
