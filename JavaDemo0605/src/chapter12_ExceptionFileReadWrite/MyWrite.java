package chapter12_ExceptionFileReadWrite;

import java.io.FileNotFoundException;
import java.util.Formatter;

public class MyWrite {

    public static void main(String[] args) throws FileNotFoundException {
        
        Formatter out = new Formatter("doc.txt");
        String doc1 = "大家好，歡迎來到Java的世界\n這裡有無盡的寶藏等你來挖角!\n";
        out.format("%s",    doc1);
        out.format("Hello World!\n");
        out.format("%s,%d,%d\n", "Bill", 95, 92);
        out.format("%s,%d,%d\n", "Kattie", 91, 98);
        
        out.flush();
        out.close();
    }

}
