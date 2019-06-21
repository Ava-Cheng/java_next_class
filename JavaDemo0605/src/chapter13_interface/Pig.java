package chapter13_interface;

public class Pig implements Flyable{

    public void showMe() {
        System.out.println("我是豬!");
    }

  
    @Override
    public void fly() {
        System.out.println("飛天豬逍遙飛20公尺");
    }  
    
    
    public static void main(String[] args) {
        //Pig p1 = new Pig();
        //p1.showMe();
        //p1.fly();
        
        //Flyable p2 = new Pig();
        //p2.fly();
        
        //Pig p2a = (Pig)p2;
        //p2a.showMe();
        
        //( (Pig)p2 ).showMe();
        
        //Flyable p3 = new Flyable();  //介面可以當作型別，但不能被new !!!
        
        Flyable p4 = new Flyable() {
            @Override
            public void fly() {
                System.out.println("在內部定義一個匿名類別，此類別implements自Flyable");
            }
        };
        
        p4.fly();
        
        Flyable p5 = new Flyable(){
        
            @Override
            public void fly()
            {
                
            }

        };// for p5  

    }


}
