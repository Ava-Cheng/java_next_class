package chapter20_List;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListDemo {

    public static void main(String[] args) {

        ArrayList list = new ArrayList();
        list.add(5);
        list.add("A");
        list.add("red");//在最後面位置加入元素
        list.add("black");
        print(list);
        System.out.println(list.get(3));
        list.add(3, "green");
        list.add('B');
        print(list);
        

        //list.remove("red");
        //list.remove( 2 );
        list.remove(new Integer(5));
        print(list);

        String[] colors = {"red", "green", "blue", "white"};
        ArrayList list2 = new ArrayList(Arrays.asList(colors));
        print(list2);
        Collections.sort(list2);
        print(list2);

        ArrayList<String> list4 = new ArrayList();
        list4.add("5");
        list4.add("A");
        //list4.add(5);
        //list4.add('A');
        
        LinkedList<String> list5 = new LinkedList();
        list5.add("red");
        list5.add("green");
        list5.add(1, "black");
        list5.addFirst("purple");
        print(list5);
        
        // addFirst是在那裡定義的方法?
        List l6= new ArrayList();
        List l7 = new LinkedList();
        l6.add("red");
        
        
        /*
        Pet p1 = new Cat("Katti");
        Flyable f1 = new Pig();
        */
                


    }

    public static void print(List list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.println();
    }

}
