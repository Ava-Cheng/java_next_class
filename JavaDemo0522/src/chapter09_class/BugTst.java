package chapter09_class;

public class BugTst {

    public static void main(String[] args) {
        
        Bug bug1 = new Bug("小花");
        bug1.showMe();
        System.out.println(bug1.count);
        Bug bug2 = new Bug("小毛");
        bug2.showMe();
        System.out.println(bug1.count);
        
        System.out.println(Bug.count);
       
  
    }

}
