package chapter32_database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class StudentManagementMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Parent root = FXMLLoader.load( this.getClass().getResource("CrudStudent.fxml")     );
        Parent root = FXMLLoader.load(this.getClass().getResource("StudentManagement.fxml"));
        Scene scene = new Scene(root);

        //登入
        JOptionPane pane = new JOptionPane();
        JDialog dialog = pane.createDialog("訊息");
        JLabel label_login = new JLabel("Username:admin");
        JTextField login = new JTextField();

        JLabel label_password = new JLabel("Password:1234");
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
        
        stage.setScene(scene);
        stage.setTitle("學生資料庫管理系統");
        stage.show();
    }

}
