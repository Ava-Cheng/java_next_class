package chapter13_interface;

public class Cat  extends Pet implements Flyable{

    public Cat(String name)
    {
        super(name);
    }
    public void meow() {
        System.out.println("喵喵!");
    }

    
    @Override
    public void fly() {
        System.out.println("貓飛100公尺");
    }
    
    public static void main(String[] args) {
        //Cat cat = new Cat("小貓");
        //cat.meow();
        //cat.fly();
        //cat.showMe();

        /*
        Pet pet = new Cat("Kitty");
        pet.showMe();
        
        ((Cat)pet).fly();
        ((Cat)pet).meow();
        */
        
        Flyable ff = new Cat("Kitty");
        ff.fly();
        ((Cat)ff).meow();
        ((Cat)ff).showMe();
        
    }


}
