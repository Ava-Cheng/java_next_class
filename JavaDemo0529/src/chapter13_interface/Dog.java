package chapter13_interface;

public class Dog extends Pet implements Flyable{
    
    public Dog(String name) {
        super(name);  //呼叫父類別的建構子
    }

   public void bark() //定義屬於Dog類別專屬的方法bark()
    {
        System.out.println("汪汪!");
    }


    @Override
    public void fly() {
       System.out.println("狗飛100公尺");
    }

    public static void main(String[] args) {
  
    }
}
