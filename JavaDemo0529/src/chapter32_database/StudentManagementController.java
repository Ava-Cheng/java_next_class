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

/**
 * FXML Controller class
 *
 * @author user
 */
public class StudentManagementController implements Initializable {

    @FXML
    private Pane pane_container;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void gotoReport(ActionEvent event) throws IOException {

        Parent nodes = FXMLLoader.load(this.getClass().getResource("GenerateReport.fxml"));

        pane_container.getChildren().addAll(nodes);

        //System.out.println("報表");
    }

    @FXML
    private void gotoReportStage(ActionEvent event) throws IOException {

        //產生新的容器，裝一大堆的元件
        Parent root = FXMLLoader.load(getClass().getResource("GenerateReport.fxml"));
        //產生新的布景
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("報表輸出系統");
        stage.show();
    }

}
