package chapter12_ExceptionFileReadWrite;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MyThrowException {

    public static double sqrt (int n) throws Exception
    {
        if ( n >= 0)
            return Math.sqrt(n);
        else
        {
            throw new Exception("不可小於零!");
        }
    }
    public static void main(String[] args) throws Exception {
        
        System.out.println(sqrt(-4));
      
    }

}


 /*
            try {
            System.out.println(sqrt(4));
            } catch (  Exception ex)
            {
            System.out.println("捕抓到異常!");
            System.out.println(ex.toString());
            System.out.println(ex.getMessage());
            }
        */    