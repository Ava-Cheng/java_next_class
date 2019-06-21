package chapter13_abstract_demo;

import chapter13_abstract.Pet;

public class PetTest {

    public static void main(String[] args) {
        
        Pet p1 = new Pet("Kattie") {
            @Override
            public void move() {
                System.out.println("輕聲走兩步");
            }
        };
        
        p1.showMe();
  
    }

}
