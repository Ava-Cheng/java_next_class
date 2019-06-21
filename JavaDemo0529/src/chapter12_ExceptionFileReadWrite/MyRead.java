package chapter12_ExceptionFileReadWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyRead {

    public static void main(String[] args) {
        
        String msg="";
        try {
            Scanner input = new Scanner(  new File("doc.txt")  );
            while (input.hasNextLine())
            {
                 msg = input.nextLine();
                 System.out.println(msg);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("檔案讀取錯誤!");
        }
  
    }

}
