package chapter13_abstract;

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
    
    
    @Override
    public void move() {
       System.out.println("輕聲走兩步");
    }
    
    public void meow()
    {
        System.out.println("喵喵!");
    }

    public static void main(String[] args) {
        
        Cat cat1 = new Cat("小貓");
        cat1.showMe();
        cat1.meow();
        cat1.move();
        
        Cat cat2 = new Cat();
        cat2.showMe();
  
    }


}
