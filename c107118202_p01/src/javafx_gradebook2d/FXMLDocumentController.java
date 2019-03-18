/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafx_gradebook2d;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Formatter;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextArea display;
    private String[] names;
    private int[][] grades;
    private String fileName = "score.csv";
    private GradeBook2D gb = new GradeBook2D();
    @FXML
    private Button btn_min;
    @FXML
    private Button btn_classAvg;
    @FXML
    private TextArea input;
    @FXML
    private ComboBox<String> list_all;

    private String[] status_all = {"讀取檔案", "顯示成績", "顯示班平均", "顯示全班最低分"};

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list_all.setValue(status_all[0]);
        for (int i = 0; i < status_all.length; i++) {
            list_all.getItems().add(status_all[i]);
        }
        //list_all.setValue(status_all[status_all.length - 1]);
    }

    @FXML
    private void select_file(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("請選取檔案");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            this.fileName = selectedFile.getName();
            display.setText(fileName + "<--開啟檔名\n");
            //openFile(this.fileName);
            openFile(selectedFile);
            gb.setGrades(grades);
        } else {
            display.setText("File selection cancelled.\n");
            return;
        }

    }

    @FXML
    private void print_all(ActionEvent event) {
        display.appendText(gb.outputGrades());
    }

    private void openFile(File file) {

        String row = "";
        int student_size = 0;
        int numgrades = 0;//紀錄有幾次成績
        try {
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                row = input.nextLine();
                //System.out.println(row);
                student_size++;
            }
            System.out.printf("學生人數:%d \n", student_size);
            numgrades = row.split("\\s*,\\s*|\\s+").length - 1;
            System.out.printf("成績個數:%d \n", numgrades);
            input.close();
        } catch (FileNotFoundException ex) {
            System.out.println("檔案讀取錯誤!");
        }
        names = new String[student_size];
        grades = new int[student_size][numgrades];
        try {
            Scanner input = new Scanner(file);
            int stu = 0;
            while (input.hasNextLine()) {
                row = input.nextLine();
                String[] rec = row.split("\\s*,\\s*|\\s+"); //這裡改成各種分隔符號都可以!!
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

    @FXML
    private void read_default_file(ActionEvent event) {
    }

    @FXML
    private void append_file(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("選擇檔案或輸入檔案名稱附加資料");
        File savedFile = fileChooser.showSaveDialog(null);
        if (savedFile != null) {
            try {
                Formatter output = new Formatter(new FileWriter(savedFile, true)); //資料加入檔案後面
                output.format(input.getText() + "\n"); //塞入一個換行符號
                output.flush();
                output.close();
            } catch (IOException e) {
                display.setText("存檔錯誤!");
                return;
            }
            display.setText("存檔於:" + savedFile.toString());
        } else {
            display.setText("File save cancelled.");
        }
    }

    @FXML
    private void replace_file(ActionEvent event) {
        //選擇檔案或輸入檔案名稱存檔若檔案已存在會被覆寫
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("選擇檔案或輸入檔案名稱存檔");
        File savedFile = fileChooser.showSaveDialog(null);
        if (savedFile != null) {
            try {
                Formatter output = new Formatter(savedFile); //資料覆蓋檔案
                output.format(input.getText() + "\n");
                output.flush();
                output.close();
            } catch (IOException e) {
                display.setText("存檔錯誤!");
                return;
            }
            display.setText("存檔於: " + savedFile.toString());
        } else {
            display.setText("File save cancelled.");
        }
    }

    @FXML
    private void get_min(ActionEvent event) {
        display.appendText("全班最低分:" + gb.getMinimum() + "\n");
    }

    @FXML
    private void get_classAvg(ActionEvent event) {
        display.appendText("班平均分:" + gb.get_classAvg() + "\n");
    }

    @FXML
    private void check(ActionEvent event) {
        int index_num = list_all.getSelectionModel().getSelectedIndex();
        if (index_num == 0) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("請選取檔案");
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                this.fileName = selectedFile.getName();
                display.setText(selectedFile.toString() + "<--開啟檔名\n");
                //openFile(this.fileName);
                openFile(selectedFile);
                gb.setGrades(grades);
            } else {
                display.setText("File selection cancelled.\n");
                return;
            }
        }else if(index_num == 1){
            display.appendText(gb.outputGrades());
        }else if(index_num == 2){
            display.appendText("班平均分:" + gb.get_classAvg() + "\n");
        }else{
            display.appendText("全班最低分:" + gb.getMinimum() + "\n");
        }
    }

}
