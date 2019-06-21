package chapter13_interface;

public class Pet {
    private String name;  //instance variable, field 實體變數或欄位
    //private int age;
    
    public Pet(  String  name   )
    {
        this.name = name; //this 是指 "這個類別"
        System.out.printf("產生新物件:%s\n", name);
    }
    public void showMe()
    {
        System.out.printf("我是%s，大家好!\n", name);
    }
}
