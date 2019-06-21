package chapter32_database;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import java.io.FileNotFoundException;
import com.opencsv.CSVReader;
import java.io.BufferedReader;
import java.util.Scanner;

/**
 * FXML Controller class
 *
 * @author user
 */
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
    //修課資料維護
    @FXML
    private TextField clID;
    @FXML
    private TextField clName;
    @FXML
    private TextField clCredit;
    @FXML
    private TextField clTeacherID;
    @FXML
    private TextField recordClNo;
    @FXML
    private TextField queryclassID;
    @FXML
    private TextField queryclassName;
    @FXML
    private TextArea file_display;
    Calendar time = Calendar.getInstance();
    List<Student> students = null;
    int current = 0;
    int student_size = 0;

    Calendar cltime = Calendar.getInstance();
    List<Classes> classes = null;
    int cl_current = 0;
    int cl_size = 0;

    private TextArea input;
    private String[] names;
    private int[][] grades;
    private String fileName = "score.csv";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        DBStudent.connect();
        DBStudent.selectAll2();
        students = DBStudent.getAllStudent();
        showRecord();
         */
    }

    private void showRecord() {
        if (!students.isEmpty()) {
            stID.setText(students.get(current).getId());
            stName.setText(students.get(current).getName());
            stPhone.setText(students.get(current).getPhone());
            String msg = String.format("第%d 筆/共%d 筆", current + 1, students.size());
            recordNo.setText(msg);
        } else {
//return;
        }
    }

    private void showClRecord() {
        if (!classes.isEmpty()) {
            clID.setText(classes.get(cl_current).getId());
            clName.setText(classes.get(cl_current).getName());
            clCredit.setText(classes.get(cl_current).getCredit());
            clTeacherID.setText(classes.get(cl_current).getTeacher_id());
            String msg = String.format("第%d 筆/共%d 筆", cl_current + 1, classes.size());
            recordClNo.setText(msg);
        } else {
//return;
        }
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
    private void clnextRecord(ActionEvent event) {
        if (cl_current < classes.size() - 1) {
            cl_current += 1;
        } else {
            cl_current = 0;
        }
        showClRecord();
    }

    @FXML
    private void previousRecord(ActionEvent event) {
        if (current > 0) {
            current -= 1;
        } else {
            current = students.size() - 1;
        }
        showRecord();
    }

    @FXML
    private void clpreviousRecord(ActionEvent event) {
        if (cl_current > 0) {
            cl_current -= 1;
        } else {
            cl_current = classes.size() - 1;
        }
        showClRecord();
    }

    @FXML
    private void previousClRecord(ActionEvent event) {
        if (cl_current > 0) {
            cl_current -= 1;
        } else {
            cl_current = classes.size() - 1;
        }
        showClRecord();
    }

    @FXML
    private void update(ActionEvent event) {
        String id = stID.getText();
        String name = stName.getText();
        String phone = stPhone.getText();
        boolean sucess = DBStudent.update(id, name, phone);
        if (sucess) {
            status.appendText(time.getTime() + "更新成功\n");
            students = DBStudent.getAllStudent();
            showRecord();
        } else {
            status.appendText(time.getTime() + "更新失敗\n");
        }
    }

    @FXML
    private void clupdate(ActionEvent event) {
        String class_id = clID.getText();
        String class_name = clName.getText();
        String credit = clCredit.getText();
        String teacher_id = clTeacherID.getText();
        boolean sucess = DBClass.update(class_id, class_name, credit, teacher_id);
        if (sucess) {
            status.appendText(time.getTime() + "更新成功\n");
            classes = DBClass.getAllClass();
            showClRecord();
        } else {
            status.appendText(time.getTime() + "更新失敗\n");
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        String id = students.get(current).getId();
        boolean sucess = DBStudent.delete(id);
        showlog("刪除一筆資料", sucess);
        students = DBStudent.getAllStudent();
        current = 0;
        showRecord();
    }

    @FXML
    private void cldelete(ActionEvent event) {
        String id = classes.get(cl_current).getId();
        boolean sucess = DBClass.delete(id);
        showlog("刪除一筆資料", sucess);
        classes = DBClass.getAllClass();
        cl_current = 0;
        showClRecord();
    }

    @FXML
    private void insert(ActionEvent event) {
        String id = stID.getText();
        String name = stName.getText();
        String phone = stPhone.getText();
        boolean sucess = DBStudent.insert(id, name, phone);
        showlog("新增一筆資料", sucess);
        students = DBStudent.getAllStudent();
        showRecord();
    }

    @FXML
    private void clinsert(ActionEvent event) {
        String class_id = clID.getText();
        String class_name = clName.getText();
        String credit = clCredit.getText();
        String teacher_id = clTeacherID.getText();
        boolean sucess = DBClass.insert(class_id, class_name, credit, teacher_id);
        showlog("新增一筆資料", sucess);
        classes = DBClass.getAllClass();
        showClRecord();
    }

    @FXML
    private void clblankRecord(ActionEvent event) {
        clID.setText("");
        clName.setText("");
        clCredit.setText("");
        clTeacherID.setText("");
        recordClNo.setText("");
    }

    @FXML
    private void blankRecord(ActionEvent event) {
        stID.setText("");
        stName.setText("");
        stPhone.setText("");
        recordNo.setText("");
    }

    @FXML
    private void firstRecord(ActionEvent event) {
        current = 0;
        showRecord();
    }

    @FXML
    private void clfirstRecord(ActionEvent event) {
        cl_current = 0;
        showClRecord();
    }

    @FXML
    private void lastRecord(ActionEvent event) {
        current = students.size() - 1;
        showRecord();
    }

    @FXML
    private void cllastRecord(ActionEvent event) {
        cl_current = classes.size() - 1;
        showClRecord();
    }

    @FXML
    private void findID(ActionEvent event) {
        boolean sucess = DBStudent.selectByID(queryID.getText());
        if (sucess) {
            students = DBStudent.getAllStudent();
            current = 0;
            showRecord();
//System.out.println(students.size());
            status.appendText(time.getTime() + "查找學號成功\n");
        } else {
            status.appendText(time.getTime() + "查找學號失敗\n");
        }
    }

    @FXML
    private void clfindID(ActionEvent event) {
        boolean sucess = DBClass.selectByID(queryclassID.getText());
        if (sucess) {
            classes = DBClass.getAllClass();
            cl_current = 0;
            showClRecord();
//System.out.println(students.size());
            status.appendText(time.getTime() + "查找課程ID成功\n");
        } else {
            status.appendText(time.getTime() + "查找課程ID失敗\n");
        }
    }

    @FXML
    private void findName(ActionEvent event) {
        boolean sucess = DBStudent.selectByName(queryName.getText());
        showlog("查找姓名", sucess);
        students = DBStudent.getAllStudent();
        current = 0;
        showRecord();
    }

    @FXML
    private void clfindName(ActionEvent event) {
        boolean sucess = DBClass.selectByName(queryclassName.getText());
        showlog("查找課程名稱", sucess);
        classes = DBClass.getAllClass();
        cl_current = 0;
        showClRecord();
    }

    @FXML
    private void findAll(ActionEvent event) {
        DBStudent.selectAll2();
        students = DBStudent.getAllStudent();
        showRecord();
    }

    @FXML
    private void clfindAll(ActionEvent event) {
        DBClass.selectAll2();
        classes = DBClass.getAllClass();
        showClRecord();
    }

    @FXML
    private void report_all_student(ActionEvent event) {
        DBStudent.selectAll2();
        List<Student> all_stud = DBStudent.getAllStudent();
        display.setText("學生基本資料報表\n");
        String row = String.format("%s\t%s\t %s\n", "學號", "姓名", "電話");
        display.appendText(row);
        for (Student stud : all_stud) {
            row = String.format("%s\t%s\t%s\n", stud.getId(), stud.getName(), stud.getPhone());
            display.appendText(row);
        }
    }

    @FXML
    private void connectToDB(ActionEvent event) {
        if (DBStudent.connect()) {
            students = DBStudent.getAllStudent();
            showRecord();
            status.appendText(time.getTime() + "連線成功\n");
        } else {
            status.appendText(time.getTime() + "連線失敗\n");
        }
    }

    @FXML
    private void clconnectToDB(ActionEvent event) {
        if (DBClass.connect()) {
            classes = DBClass.getAllClass();
            showClRecord();
            status.appendText(time.getTime() + "連線成功\n");
        } else {
            status.appendText(time.getTime() + "連線失敗\n");
        }
    }

    @FXML
    private void closeConnection(ActionEvent event) {
        boolean sucess = DBStudent.closeConnection();
        showlog("關閉連線", sucess);
        students = null;
        stID.setText("");
        stName.setText("");
        stPhone.setText("");
        recordNo.setText("");
    }

    public void showlog(String msg, boolean sucess) {
        if (sucess) {
            status.appendText(time.getTime() + msg + " 成功\n");
        } else {
            status.appendText(time.getTime() + msg + "失敗\n");
        }
    }

    @FXML
    private void stu_file(ActionEvent event) {
        //選擇檔案或輸入檔案名稱存檔 若檔案已存在會被覆寫
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("選擇檔案或輸入檔案名稱存檔");
        File savedFile = fileChooser.showSaveDialog(null);
        if (savedFile != null) {
            try {
                PrintWriter writer;
                writer = new PrintWriter(savedFile);

                DBStudent.selectAll2();
                List<Student> all_stud = DBStudent.getAllStudent();
                String row = String.format("%s\t%s\t %s\n", "學號", "姓名", "電話");
                for (Student stud : all_stud) {
                    row = String.format("%s\t%s\t%s\n", stud.getId(), stud.getName(), stud.getPhone());
                    writer.append(row);
                }

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
    private void select_file(ActionEvent event) throws FileNotFoundException, IOException {
        //選取檔案
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("請選取檔案");
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            file_display.appendText(selectedFile.getAbsolutePath() + "<--開啟檔名\n");
            BufferedReader reader = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()));
            String line = null;

            while ((line = reader.readLine()) != null) {
                int i = 0;
                String item[] = line.split(",");
                while (i < item.length) {
                    file_display.appendText(item[i] + " ");
                    i++;
                }
                //System.out.println(item[0].getClass());
                try {
                    DBStudent.connect();
                    DBStudent.insert(item[0], item[1], item[2]);
                    status.appendText("批次新增資料成功");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                file_display.appendText("\n");

            }
        } else {
            return;
        }
    }

}
