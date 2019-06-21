package chapter11_inheritance;

public class PetZoo {

    public static void main(String[] args) {

        Pet[] pets = new Pet[4];
        pets[0] = new Cat("小花");
        pets[1] = new Cat("Kitty");
        pets[2] = new Dog("小黑");
        pets[3] = new Dog("小白");
        for (int i=0; i < pets.length;  i++)
        {
            pets[i].showMe();
            
            if (pets[i]  instanceof Cat)
            {
                Cat cat = (Cat)pets[i];
                cat.meow();
            } else
                if (pets[i]  instanceof Dog)
            {
                Dog dog = (Dog)pets[i];
                dog.bark();
            }
        }

    }

}
