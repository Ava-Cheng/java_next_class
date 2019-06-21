package Book_management;

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

public class CrudUserController implements Initializable {

    //底層
    @FXML
    private AnchorPane baseAnchorPane;

    //查詢
    @FXML
    private TextField urID;
    @FXML
    private TextField urpassword;
    @FXML
    private TextField urpermission;
    @FXML
    private TextField recordNo;

    //特定查詢
    @FXML
    private TextField queryID;

    @FXML
    private TextArea file_display;
    @FXML
    private TextArea status;

    Calendar time = Calendar.getInstance();
    List<User> users = null;
    int current = 0;
    int user_size = 0;

    private TextArea input;
    private String[] names;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (DBUser.connect()) {
            users = DBUser.getAllUser();
            System.out.println("DBUser.getAllUser()" + users);
            showRecord();
            status.appendText(time.getTime() + "連線成功\n");
        } else {
            status.appendText(time.getTime() + "連線失敗\n");
        }
    }

    private void showRecord() {
        System.out.println("showRecord");
        if (!users.isEmpty()) {
            try {
                System.out.println("users不為空");
                urID.setText(users.get(current).getId());
                urpassword.setText(users.get(current).getPassword());
                urpermission.setText(users.get(current).getPermission());
                String msg = String.format("第%d 筆/共%d 筆", current + 1, users.size());
                recordNo.setText(msg);
            } catch (Exception ex) {
                System.out.println("showRecord" + ex);
            }
        } else {
            System.out.println("showRecord沒資料");
        }
    }

    @FXML
    private void nextRecord(ActionEvent event) {
        if (current < users.size() - 1) {
            current += 1;
        } else {
            current = 0;
        }
        showRecord();
    }

    @FXML
    private void previousRecord(ActionEvent event) {
        if (current > 0) {
            current -= 1;
        } else {
            current = users.size() - 1;
        }
        showRecord();
    }
    //第一筆

    @FXML
    private void firstRecord(ActionEvent event) {
        current = 0;
        showRecord();
    }

    //最後一筆
    @FXML
    private void lastRecord(ActionEvent event) {
        current = users.size() - 1;
        showRecord();
    }

    @FXML
    private void update(ActionEvent event) {
        String id = urID.getText();
        String password = urpassword.getText();
        String permission = urpermission.getText();
        boolean sucess = DBUser.update(id, password, permission);
        if (sucess) {
            status.appendText(time.getTime() + "更新成功\n");
            users = DBUser.getAllUser();
        } else {
            status.appendText(time.getTime() + "更新失敗\n");
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        String id = users.get(current).getId();
        boolean sucess = DBUser.delete(id);
        showlog("刪除一筆資料", sucess);
        users = DBUser.getAllUser();
        current = 0;
        showRecord();
    }

    @FXML
    private void insert(ActionEvent event) {
        String id = urID.getText();
        String password = urpassword.getText();
        String permission = urpermission.getText();
        boolean sucess = DBUser.insert(id, password, permission);
        showlog("新增一筆資料", sucess);
        users = DBUser.getAllUser();
        showRecord();
    }

    @FXML
    private void blankRecord(ActionEvent event) {
        urID.setText("");
        urpassword.setText("");
        urpermission.setText("");
        recordNo.setText("");
    }

    //找使用者ID
    @FXML
    private void findID(ActionEvent event) {
        boolean sucess = DBUser.selectByID(queryID.getText());
        showlog("查找使用者ID", sucess);
        users = DBUser.getAllUser();
        current = 0;
        showRecord();
    }

    //尋找全部
    @FXML
    private void findAll(ActionEvent event) {
        DBUser.selectAll();
        users = DBUser.getAllUser();
        showRecord();
    }

    //資料庫連線
    @FXML
    private void connectToDB(ActionEvent event) {
        if (DBUser.connect()) {
            users = DBUser.getAllUser();
            showRecord();
            status.appendText(time.getTime() + "連線成功\n");
        } else {
            status.appendText(time.getTime() + "連線失敗\n");
        }
    }

    //關閉連線
    @FXML
    private void closeConnection(ActionEvent event) {
        boolean sucess = DBUser.closeConnection();
        showlog("關閉連線", sucess);
        users = null;
        urID.setText("");
        urpassword.setText("");
        urpermission.setText("");
        recordNo.setText("");
    }

    //顯示log
    public void showlog(String msg, boolean sucess) {
        if (sucess) {
            status.appendText(time.getTime() + msg + " 成功\n");
        } else {
            status.appendText(time.getTime() + msg + "失敗\n");
        }
    }

}
