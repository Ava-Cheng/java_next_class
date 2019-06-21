/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chapter32_database;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static jdk.nashorn.internal.runtime.regexp.joni.Syntax.Java;

public class StudentManagementController implements Initializable {

    @FXML
    private Pane pane_container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void gotoReport(ActionEvent event) throws IOException {
//此處用nodes命名
        Parent nodes = FXMLLoader.load(getClass().getResource("GenerateReport.fxml"));
//取得pane_container內所有的元件，並重新設定為新的nodes
        pane_container.getChildren().setAll(nodes);
//pane_container.getChildren().set(0,root); //另一種寫法
    }

    @FXML
    private void gotoReportStage(ActionEvent event) throws IOException {
//產生新的容器，裝一大堆的元件
        Parent root = FXMLLoader.load(getClass().getResource("GenerateReport.fxml"));
//產生新的布景
        Scene scene = new Scene(root);
//產生新的一個視窗
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("報表輸出系統");
        stage.show();
    }

    @FXML
    private void gotoCrud(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CrudStudent.fxml"));
            pane_container.getChildren().setAll(root); //設定pane窗格底下所有元件
        } catch (IOException ex) {
            System.out.println("發生異常:載入CrudStudent.fxml");
        }
    }

    @FXML
    private void gotoCrudStage(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("CrudStudent.fxml"));
        } catch (IOException ex) {
            System.out.println("發生異常:載入CrudStudent.fxml");
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("學生資料維護系統");
        stage.show();
    }

    @FXML
    private void gotoCrudTableView(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CrudStudentTableView.fxml"));
            pane_container.getChildren().setAll(root);
        } catch (IOException ex) {
            System.out.println("發生異常:載入CrudStudentTableView.fxml");
        }
    }

    @FXML
    private void login(ActionEvent event) {
        JOptionPane pane = new JOptionPane();
        JDialog dialog = pane.createDialog("訊息");

        JLabel label_login = new JLabel("Username:");
        JTextField login = new JTextField();

        JLabel label_password = new JLabel("Password:");
        JPasswordField password = new JPasswordField();

        Object[] array = {label_login, login, label_password, password};

        int res = JOptionPane.showConfirmDialog(null, array, "Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (res == JOptionPane.OK_OPTION) {
            DBLogin.connect();
            boolean loginStatus = DBLogin.login(login.getText().trim(), new String(password.getPassword()));
            if (loginStatus) {
                JOptionPane.showMessageDialog(null, "登入成功");
                dialog.setAlwaysOnTop(true);
            } else {
                JOptionPane.showMessageDialog(null, "登入失敗");
                dialog.setAlwaysOnTop(true);
            }
            //System.out.println("username: " + login.getText().trim());
            //System.out.println("password: " + new String(password.getPassword()));
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        JOptionPane pane = new JOptionPane();
        JOptionPane.showMessageDialog(null, "登出成功");
        JDialog dialog = pane.createDialog("訊息");
        dialog.setAlwaysOnTop(true);

        JLabel label_login = new JLabel("Username:");
        JTextField login = new JTextField();

        JLabel label_password = new JLabel("Password:");
        JPasswordField password = new JPasswordField();

        Object[] array = {label_login, login, label_password, password};

        int res = JOptionPane.showConfirmDialog(null, array, "Login",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);

        if (res == JOptionPane.OK_OPTION) {
            DBLogin.connect();
            boolean loginStatus = DBLogin.login(login.getText().trim(), new String(password.getPassword()));
            if (loginStatus) {
                JOptionPane.showMessageDialog(null, "登入成功");
                dialog.setAlwaysOnTop(true);
            } else {
                JOptionPane.showMessageDialog(null, "登入失敗");
                dialog.setAlwaysOnTop(true);
            }
        }
    }
}
