package chapter11_inheritance;

public class Cat extends Pet{
    
    public Cat( String name  )
    {
        super(  name   );
    }
    public Cat()
    {
        this("標準貓");
        System.out.println("初始化一個標準貓在Cat建構子!");

    }
    public void meow()
    {
        System.out.println("喵喵!");
    }

    public static void main(String[] args) {
        
        Cat cat1 = new Cat("小貓");
        cat1.showMe();
        cat1.meow();
        
        Cat cat2 = new Cat();
        cat2.showMe();
  
    }

}
