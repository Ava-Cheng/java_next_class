package chapter31_network;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class V10_ServerEmpty extends Application {

    // 全域變數 整個程式有多個地方會用這些變數
    private final TextArea display = new TextArea();
    private final Button btnSubmit = new Button("送出");
    private final Button btnConnect = new Button("啟動伺服器");
    private final TextArea input = new TextArea("伺服器訊息測試");
    private final int port = 1024;
    private final String ip = "localhost";


    public V10_ServerEmpty() {

    }

    @Override
    public void start(Stage stage) {

        FlowPane root = new FlowPane();
        display.setPrefSize(350, 400);
        input.setPrefSize(350, 100);
        btnSubmit.setDisable(true);

        //texArea自動換行
        display.setWrapText(true);
        input.setWrapText(true);

        root.getChildren().add(display);
        root.getChildren().add(input);
        root.getChildren().add(btnConnect);
        root.getChildren().add(btnSubmit);

        Scene scene = new Scene(root, 350, 600);

        stage.setTitle("Line伺服器");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            System.exit(0); //結束程式
        });

        display.setStyle(""
                + "-fx-control-inner-background:#000000; "
                + "-fx-font-family: Consolas; "
                + "-fx-highlight-fill: #00ff00; "
                + "-fx-highlight-text-fill: #000000; "
                + "-fx-text-fill: #00ff00; ");

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String msg = String.format("server:%s", input.getText());
                display.appendText(msg + "\n");

            }
        });

        btnConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                display.appendText("伺服器啟動成功!\n");
                btnConnect.setDisable(true);
                btnSubmit.setDisable(false);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
