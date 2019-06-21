package chapter32_database;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudentManagementMain extends Application{

    public static void main(String[] args) {
          launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
       Parent root = FXMLLoader.load( this.getClass().getResource("CrudStudent.fxml")     );
       //Parent root = FXMLLoader.load( this.getClass().getResource("GenerateReport.fxml")     );
       Scene scene = new Scene(root);
       
       stage.setScene(scene);
       stage.setTitle("學生資料庫管理系統");
       stage.show();
    }

}
