/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter32_database;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Formatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import static jdk.nashorn.tools.ShellFunctions.input;

/**
 * FXML Controller class
 *
 * @author user
 */
public class GenerateReportController implements Initializable {

    @FXML
    private TextArea display;
    @FXML
    private TextField field_student_id;
    @FXML
    private TextField field_teacher_id;
    @FXML
    private TextArea display_teacher;
    @FXML
    private TextArea display_studAdvisor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DBReport.connect();
    }

    @FXML
    private void generate_stud_report(ActionEvent event) {
        String student_id = field_student_id.getText();
        display.setText(DBReport.reportStudClass(student_id));
    }

    @FXML
    private void generate_stud_report_file(ActionEvent event) {
        //選擇檔案或輸入檔案名稱存檔 若檔案已存在會被覆寫
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("選擇檔案或輸入檔案名稱存檔");
        File savedFile = fileChooser.showSaveDialog(null);
        if (savedFile != null) {
            try {
                //輸出成csv
                /*
                Formatter output = new Formatter(savedFile); //資料加入檔案後面
                String student_id = field_student_id.getText();
                output.format(DBReport.reportStudClass(student_id)+ "\n");
                output.flush();
                output.close();
                 */
                PrintWriter writer;
                writer = new PrintWriter(savedFile);
                String student_id = field_student_id.getText();
                writer.println(DBReport.reportStudClass(student_id));
                writer.close();

            } catch (IOException ex) {
                display.setText("存檔錯誤!");
                return;
            }
            display.setText("存檔於: " + savedFile.toString());
        } else {
            display.setText("File save cancelled.");
        }
    }

    @FXML
    private void generate_teacher_report(ActionEvent event) {
        String teacher_id = field_teacher_id.getText();
        display_teacher.setText(DBReport.reportTeacherStud(teacher_id));
    }

    @FXML
    private void generate_teacher_report_file(ActionEvent event) {
        //選擇檔案或輸入檔案名稱存檔 若檔案已存在會被覆寫
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("選擇檔案或輸入檔案名稱存檔");
        File savedFile = fileChooser.showSaveDialog(null);
        if (savedFile != null) {
            try {
                //輸出成csv
                /*
                Formatter output = new Formatter(savedFile); //資料加入檔案後面
                String student_id = field_student_id.getText();
                output.format(DBReport.reportStudClass(student_id)+ "\n");
                output.flush();
                output.close();
                 */
                PrintWriter writer;
                writer = new PrintWriter(savedFile);
                String teacher_id = field_teacher_id.getText();
                writer.println(DBReport.reportTeacherStud(teacher_id));
                writer.close();

            } catch (IOException ex) {
                display.setText("存檔錯誤!");
                return;
            }
            display.setText("存檔於: " + savedFile.toString());
        } else {
            display.setText("File save cancelled.");
        }
    }

    @FXML
    private void generate_studAdvisor_report(ActionEvent event) {
        display_studAdvisor.setText(DBReport.reportStudAdvisor());
    }

    @FXML
    private void generate_studAdvisor_report_file(ActionEvent event) {
        //選擇檔案或輸入檔案名稱存檔 若檔案已存在會被覆寫
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("選擇檔案或輸入檔案名稱存檔");
        File savedFile = fileChooser.showSaveDialog(null);
        if (savedFile != null) {
            try {
                //輸出成csv
                /*
                Formatter output = new Formatter(savedFile); //資料加入檔案後面
                String student_id = field_student_id.getText();
                output.format(DBReport.reportStudClass(student_id)+ "\n");
                output.flush();
                output.close();
                 */
                PrintWriter writer;
                writer = new PrintWriter(savedFile);
                writer.println(DBReport.reportStudAdvisor());
                writer.close();

            } catch (IOException ex) {
                display.setText("存檔錯誤!");
                return;
            }
            display.setText("存檔於: " + savedFile.toString());
        } else {
            display.setText("File save cancelled.");
        }
    }

}
