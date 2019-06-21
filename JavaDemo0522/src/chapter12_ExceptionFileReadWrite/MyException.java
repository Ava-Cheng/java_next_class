package chapter12_ExceptionFileReadWrite;

import java.util.Scanner;

public class MyException {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        int[] array = new int[3];
        try {
        System.out.println(array[3]);
        System.out.println("請輸入一個整數:");        
        array[2] = input.nextInt();
        } catch( ArrayIndexOutOfBoundsException  e  )
        {
            System.out.println("陣列索引出界了!");
            e.printStackTrace();
        } catch ( Exception e)
        {
            System.out.println("發生其他異常了!");
             System.out.println(e.toString());
        }
        finally{
            System.out.println("程式已執行完畢");
        }
        /*
        int n1 = 10;
        int n2 = 0;
        int ans;
        
        try
        {
            ans = n1 / n2;
        } catch(  ArithmeticException   ex  )
        {
            System.out.println("小學老師教過分母不可為0");
            System.out.println(ex.toString());
        }
        */
        //ans = n1 / n2;

        /*
        if (n2 != 0) {
            ans = n1 / n2;
        } else {
            System.out.println("小學老師教過分母不可為0");
        }
*/
        
        
        
    }

}
