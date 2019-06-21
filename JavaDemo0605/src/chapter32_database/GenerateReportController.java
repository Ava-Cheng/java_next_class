package chapter32_database;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBReport.connect(); //DBReport資料庫連線只要連線一次即可
    }    

    @FXML
    private void generate_stud_report(ActionEvent event) {
        String student_id = field_student_id.getText();
        display.setText(DBReport.reportStudClass(student_id));
    }

    @FXML
    private void generate_teacher_report(ActionEvent event) {
        String teacher_id = field_teacher_id.getText();
        display_teacher.setText(DBReport.reportTeacherStud(teacher_id));
    }

    @FXML
    private void generate_studAdvisor_report(ActionEvent event) {
    }
    
}
