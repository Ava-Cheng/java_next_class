/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Book_management;

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

public class bookManagementController implements Initializable {

    static String loginID;

    @FXML
    private Pane pane_container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    //借還書
    @FXML
    private void gotobook(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CrudBook.fxml"));
            pane_container.getChildren().setAll(root); //設定pane窗格底下所有元件
        } catch (IOException ex) {
            System.out.println("發生異常:載入CrudBook.fxml");
            System.out.println(ex);
        }
    }

    //書籍管理
    @FXML
    private void gotoBookManagement(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CrudBookAll.fxml"));
            System.out.println(root);
            pane_container.getChildren().setAll(root); //設定pane窗格底下所有元件
        } catch (IOException ex) {
            System.out.println("發生異常:載入CrudBookAll.fxml");
            System.out.println(ex);
        }
    }

    //使用者管理
    @FXML
    private void gotoUserManagement(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("CrudUser.fxml"));
            System.out.println(root);
            pane_container.getChildren().setAll(root); //設定pane窗格底下所有元件
        } catch (Exception ex) {
            System.out.println("發生異常:載入CrudUser.fxml");
            System.out.println(ex);
            ex.printStackTrace();
        }
    }

    //登入
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
                loginID = login.getText().trim();
                dialog.setAlwaysOnTop(true);
            } else {
                JOptionPane.showMessageDialog(null, "登入失敗");
                dialog.setAlwaysOnTop(true);
            }
            //System.out.println("username: " + login.getText().trim());
            //System.out.println("password: " + new String(password.getPassword()));
        }
    }

    //登出
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
                loginID = login.getText().trim();
                dialog.setAlwaysOnTop(true);
            } else {
                JOptionPane.showMessageDialog(null, "登入失敗");
                dialog.setAlwaysOnTop(true);
            }
        }
    }
}
