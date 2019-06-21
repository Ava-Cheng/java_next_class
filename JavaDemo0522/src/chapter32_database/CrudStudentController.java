package chapter32_database;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CrudStudentController implements Initializable {

    @FXML
    private AnchorPane baseAnchorPane;
    @FXML
    private TextField stName;
    @FXML
    private TextField stID;
    @FXML
    private TextField stPhone;
    @FXML
    private TextField recordNo;
    @FXML
    private TextField queryID;
    @FXML
    private TextField queryName;
    @FXML
    private TextArea display;
    @FXML
    private TextArea status;

    List<Student> students = null;
    int current = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBStudent.connect();
        DBStudent.selectAll();
        students = DBStudent.getAllStudent();
        showRecord();
    }

    private void showRecord() {

        stID.setText(students.get(current).getId());
        stName.setText(students.get(current).getName());
        stPhone.setText(students.get(current).getPhone());
        String msg = String.format("第%d 筆/共%d 筆", current + 1, students.size());
        recordNo.setText(msg);

    }

    @FXML
    private void nextRecord(ActionEvent event) {

        if (current < students.size() - 1) {
            current += 1;
        } else {
            current = 0;
        }
        showRecord();
    }

    @FXML
    private void previousRecord(ActionEvent event) {
    }

    @FXML
    private void update(ActionEvent event) {
    }

    @FXML
    private void delete(ActionEvent event) {
    }

    @FXML
    private void insert(ActionEvent event) {
    }

    @FXML
    private void blankRecord(ActionEvent event) {
    }

    @FXML
    private void firstRecord(ActionEvent event) {
    }

    @FXML
    private void lastRecord(ActionEvent event) {
    }

    @FXML
    private void findID(ActionEvent event) {
    }

    @FXML
    private void findName(ActionEvent event) {
    }

    @FXML
    private void findAll(ActionEvent event) {
    }

    @FXML
    private void connectToDB(ActionEvent event) {
    }

    @FXML
    private void report_all_student(ActionEvent event) {
        
        DBReport.connect();
         String report  = DBReport.reportStudClass("u001");
         display.setText(  report   );
        
        
        /*
        DBStudent.selectAll();
        List<Student> all_stud = DBStudent.getAllStudent();
        display.setText("學生基本資料報表\n");
        String row = String.format("%s\t%s\t %s\n", "學號", "姓名", "電話");
        display.appendText(row);
        for (Student stud : all_stud) {
            row = String.format("%s\t%s\t%s\n", stud.getId(), stud.getName(), stud.getPhone());
            display.appendText(row);
        }
        */
        //String report  = DBStudent.getAllStudentReport();
       //display.setText(  report   );

    }

}
