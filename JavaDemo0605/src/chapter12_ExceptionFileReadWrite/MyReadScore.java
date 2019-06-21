package chapter12_ExceptionFileReadWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyReadScore {

    public static void main(String[] args) {

        String row = "";
        int student_size = 0;
        int numgrades = 0;//紀錄有幾次成績

        try {
            Scanner input = new Scanner(new File("doc.txt"));
            while (input.hasNextLine()) {
                row = input.nextLine();
                //System.out.println(row);
                student_size++;
            }
            System.out.printf("學生人數:%d \n", student_size);
            numgrades = row.split(",").length - 1;
            System.out.printf("成績個數:%d \n", numgrades);
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println("檔案讀取錯誤!");
        }

        String[] names;
        int[][] grades;

        names = new String[student_size];
        grades = new int[student_size][numgrades];

        try {
            Scanner input = new Scanner(new File("doc.txt"));
            int stu = 0;
            while (input.hasNextLine()) {
                row = input.nextLine();
                String[] rec = row.split(","); //這裡改成各種分隔符號都可以!!

                names[stu] = rec[0];

                for (int j = 0; j < numgrades; j++) {
                    grades[stu][j] = Integer.parseInt(rec[ j +1]);
                }
                stu++;
            }

        } catch (FileNotFoundException ex) {
            System.out.println("檔案讀取錯誤!");
        }

        //System.out.println(names[2]);
        //System.out.println(grades[2][0]);
        
        
        //GradBook gb = new GradeBook(grades, names);
        //gb.printGrades();
        //gb.getAvg();
        
        printGrades(grades, names);

    }

    public static void printGrades(int[][] grades, String[] names) {
        for (int i = 0; i < grades.length; i++) {
            System.out.printf("%s ", names[i]);
            for (int j = 0; j < grades[i].length; j++) {
                System.out.printf("%d ", grades[i][j]);
            }
            System.out.println();
        }
    }

}
