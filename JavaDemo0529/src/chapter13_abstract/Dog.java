package chapter13_abstract;

public class Dog extends Pet{
    
    public Dog( String name  )
    {
        super(  name   );
    }
    
    public void bark()
    {
        System.out.println("汪汪!");
    }

    @Override
    public void showMe()
    {
        super.showMe();
        System.out.println("我是一隻狗!");
    }
    public static void main(String[] args) {
        
        Dog dog1 = new Dog("小黑");
        dog1.showMe();
        dog1.bark();
        dog1.move();
  
    }

    @Override
    public void move() {
        System.out.println("我大跳5公尺");
    }

}
