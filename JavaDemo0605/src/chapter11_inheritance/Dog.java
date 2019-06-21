package chapter11_inheritance;

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
  
    }

}
