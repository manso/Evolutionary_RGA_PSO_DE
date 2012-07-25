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

import java.io.IOException;
import java.util.Random;

/**
 *
 * @author ZULU
 */
public class LinKerProR111 // 2-opt, 3-opt?C4-opt‚?“K??‚?Š„?‡‚??¬‚?‚?‚?,Š„?‡‚?Ž©•?‚???‚ß‚é   
      {        
                public   static int n=13;//Š???’n‚??”   
                public   static int runs=2000;//?‰?“?_?€’T‚·‰??”   
                public   static   final   int[]   a   =   new int[n];    
                public   static   final   int[]   c   =   new int[n];   
                private   static   Random   rand   =   new   Random();    
                LookFor lookfor=new LookFor();   
                   
            public   static   void   main(String[]   args) throws NumberFormatException, IOException   
                {     
                 double random1=0.2,random2=0.8;//Šm—¦‚?Ž©•?‚???‚ß‚é   
                    
                 System.out.println("2-Opt "+random1);   
                 System.out.println("3-Opt "+(1-random1-random2));   
                 System.out.println("4-Opt "+random2);   
                    
                for(int k=0;k<10;k++)//10‰?‚?Ž??±   
                {   
                double precost=0,cost=100000;   
                long start = System.currentTimeMillis();   
                  
                  for(int i=0;i<n;i++)//Š???’n?‡”Ô‚?random‚??¶?¬   
                        a[i]=i;   
                    int[] b=new int[n];      
                    boolean[] picked=new boolean[a.length];      
                      for(int i=0;i<a.length;i++)   
                        {      
                        int t;      
                        do      
                          t=rand.nextInt(a.length);      
                        while(picked[t]);      
                        b[i]=a[t];    
                        c[i]=b[i];      
                        picked[t]=true;    
                         }   
              
                     for(int j=0;j<runs;j++)//generation‚??¶?¬‚·‚é‰??”   
                      {       
                         for(int l=0;l<c.length;l++)   
                              b[l]=c[l];   
       
                      if(j>=runs*random1)     
                          precost=new LinKerProR111().selection(2, b);//2-Opt   
                      else if(j>=runs*(1-random2))   
                          precost=new LinKerProR111().selection(4, b);//4-Opt   
                      else    
                          precost=new LinKerProR111().selection(3, b);//3-Opt   
                        
                         
                       if(precost<cost)   
                              {   
                           cost=precost;   
                       System.out.println(cost+" "+j);   
                       new LinKerProR111().Input(b);   
                       for(int m=0;m<b.length;m++)   
                           c[m]=b[m];//??”Ô‚?‚??o?H‚?•?‘¶‚·‚é   
                              }   
                       }   
              long end = System.currentTimeMillis();   
            System.out.println("?v‘?ŽžŠÔ: " + (end - start) + " ms. "+k);   
                 
           }   
     }   
               
            private void changeArry(int m,int[] arry)//?o?H‚?’†‚ÉŠ???’n‚?m?Â‘O‚É?Ú“®‚·‚é   
             {   
             for(int g=0;g<m;g++)   
                  {   
                      int change=arry[0];   
                      int A=arry.length-1;   
                         
                      for(int j=0;j<A;j++)   
                          arry[j]=arry[j+1];   
                          arry[A]=change;    
                  }   
             }   
              
           private  double selection(int m,int[] arry) throws NumberFormatException, IOException   
           {   
              int input1=0;   
              int input2=0;   
              double Dsum=0;   
                 
                        input2=rand.nextInt(arry.length);   
                        new LinKerProR111().changeArry(input2, arry);//‚?‚¤‚P‰?–Ú?‰?“?_?€‚???‚é   
                      
                 
              for (int time=0;time<m-1;time++)   
                  {   
                  do   
                        input1=rand.nextInt(arry.length);   
                     while(input1==0||input1==1||input1==arry.length-1);   
                     
                   new LinKerProR111().change(input1, arry);   
                   }   
            return Dsum=lookfor.DistanceSum(arry);//‹——?‘??a‚??vŽZ‚·‚é   
           }   
              
               private void change(int m,int[] arry)//Š???’n‚???Š·   
               {   
                int change=0;   
               for(int j=0;j<(m+1)/2;j++)   
                  {   
                change=arry[j];   
                arry[j]=arry[m-j];   
                arry[m-j]=change;   
                  }          
               }      
                  
               private void Input(int[] arry)//?o—Í   
               {   
                 for(int   i=0;i<arry.length;i++)   
                    {   
                        System.out.print(arry[i]+" ");    
                    }   
                 System.out.println();    
               }   
      }   


