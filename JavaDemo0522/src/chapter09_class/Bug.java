package chapter09_class;

public class Bug {
    
    private String name; //同屋簷Bug內可以存取
    //public String name; //所有程式可以都存取
    //String name; //同一個目錄底下的程式可以存取
   
    public static int count=0;
    
    public  Bug(String name)
    {
        this.name = name;
        count++;
    }
            
    public void showMe()
    {
        //System.out.println("I am a bug.");
        System.out.printf("My name is %s.\n", this.name);
    }
    

    public static void main(String[] args) {
  
    }

}
