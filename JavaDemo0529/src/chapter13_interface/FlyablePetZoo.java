package chapter13_interface;

public class FlyablePetZoo {

    public static void main(String[] args) {
        
        Flyable[] pets = new Flyable[4];
        pets[0] = new Cat("小花");
        pets[1] = new Cat("Kitty");
        pets[2] = new Dog("小黑");
        pets[3] = new Dog("小白");

        for (int i=0; i< pets.length; i++)
        {
            //pets[i].fly();
            //( (Pet)pets[i] ).showMe();
            
            if( pets[i] instanceof Cat)
            {
                ((Cat)pets[i]).meow();
            }

        }
        
  
    }

}
