package chapter18_recursion;

public class Hanoi {

    public static void hanoi(int n, char from, char temp, char to) {
        if (n == 1) {
            System.out.println("盤子" + n + "從" + from + "到" + to);
        }else
        {
            hanoi(n-1, from, to , temp);
            System.out.println("盤子" + n + "從" + from + "到" + to+"--");         
            hanoi(n-1, temp, from, to);
        }
    }

    public static void main(String[] args) {
        hanoi(3, 'A', 'B', 'C');

    }

}
