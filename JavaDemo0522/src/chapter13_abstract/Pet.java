package chapter13_abstract;

public abstract class Pet {

    private String name;

    public Pet(String name) {
        this.name = name; //this 是指"這個類別"
        System.out.printf("產生新物件:%s\n", name);
    }

    public abstract void move();
    
   public void showMe() {
        System.out.printf("我是%s，大家好!\n", name);
    }

    public static void main(String[] args) {
        
        Pet p1 = new Pet("Kattie") {
            @Override
            public void move() {
                System.out.println("輕聲走兩步");
            }
        };
        
        
        p1.showMe();
        p1.move();
        
        //new Pet("小花").showMe();

    }

}
