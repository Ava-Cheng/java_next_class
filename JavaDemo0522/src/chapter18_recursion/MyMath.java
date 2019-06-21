package chapter18_recursion;

public class MyMath {
        
    public static long fibonacci(int n) {
        
        if (n == 0)
        {
            return 0;
        } else if (n == 1)
        {
            return 1;
        } else
        {
        //return  fibonacci(n-1)+fibonacci(n-2);
         long result = fibonacci(n-1)+fibonacci(n-2);
        return  result;
        }
    }
    
    
    public static long fibonacciFor(int n) {
        long n0 = 0;
        long n1 = 1;
        long n2 = 1;

        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        }

        for (int i = 2; i <= n; i++) {
            n2 = n1 + n0;
            n0 = n1;
            n1 = n2;

        }
        return n2;
    }

    
    public static long factorial(int n) {
        
        if ( n <= 0 ) {
            return 1;
        } else {
            long result = factorial(n - 1);
            return n * result;
        }
    }

    public static void main(String[] args) {
        System.out.println("最後計算結果:"+MyMath.factorial(5));
    }

}
