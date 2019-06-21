package chapter11_inheritance;

public class Pet {

    private String name;

    public Pet(String name) {
        this.name = name; //this 是指"這個類別"
        System.out.printf("產生新物件:%s\n", name);
    }

    public void showMe() {
        System.out.printf("我是%s，大家好!\n", name);
    }

    public static void main(String[] args) {
        
        Pet p1 = new Pet("Kattie");
        p1.showMe();
        new Pet("小花").showMe();

    }

}
