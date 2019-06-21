/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxgradebook2d;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

/**
 *
 * @author user
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private TextArea display;
    private String[] names;
    private int[][] grades;
    private String fileName = "score.csv";
    private GradeBook2D gb = new GradeBook2D();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void read_file(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("請選擇欲開啟檔案");
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null){
            fileName = selectedFile.getName();
            display.setText(fileName+"<--開啟檔名\n");
        }else{
            display.setText("File selection cancelled.\n");
        }
        
        openFile(fileName);
        gb.setGrades(grades);
    }
    
    @FXML
    private void print_all(ActionEvent event) {
        display.appendText(gb.outputGrades());
    }

    //寫一個方法openFile()
    public void openFile(String fileName) {
        String row = "";
        int student_size = 0;
        int numgrades = 0;//紀錄有幾次成績
        try {
            Scanner input = new Scanner(new File(fileName));
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
        names = new String[student_size];
        grades = new int[student_size][numgrades];
        try {
            Scanner input = new Scanner(new File(fileName));
            int stu = 0;
            while (input.hasNextLine()) {
                row = input.nextLine();
                String[] rec = row.split(","); //這裡改成各種分隔符號都可以!!
                names[stu] = rec[0];
                for (int j = 0; j < numgrades; j++) {
                    grades[stu][j] = Integer.parseInt(rec[j + 1]);
                }
                stu++;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("檔案讀取錯誤!");
        }
    }
}
