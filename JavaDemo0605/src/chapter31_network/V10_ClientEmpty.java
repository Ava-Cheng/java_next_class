package chapter31_network;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class V10_ClientEmpty extends Application {

    TextArea display = new TextArea();
    TextArea input = new TextArea("How is it going?");
    TextField user = new TextField("路人甲");
    Button btnConnect = new Button("連線");
    Button btnSubmit = new Button("送出");
    private final int port = 1024;
    private final String ip = "localhost";

    //建構子
    //建構子會優先執行，之後再執行public void start(Stage primaryStage){}
    public V10_ClientEmpty() {

    }

    @Override
    public void start(Stage primaryStage) {
        FlowPane root = new FlowPane();
        display.setPrefSize(350, 400);
        input.setPrefSize(350, 100);
        display.setStyle(""
                + "-fx-control-inner-background:#000000; "
                + "-fx-font-family: Consolas; "
                + "-fx-highlight-fill: #00ff00; "
                + "-fx-highlight-text-fill: #000000; "
                + "-fx-text-fill: #00ff00; ");

        //texArea自動換行
        display.setWrapText(true);
        input.setWrapText(true);

        btnSubmit.setDisable(true);

        root.getChildren().add(display);
        root.getChildren().add(input);
        root.getChildren().add(user);
        root.getChildren().add(btnConnect);
        root.getChildren().add(btnSubmit);

        Scene scene = new Scene(root, 350, 550);
        primaryStage.setTitle("賴一下");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });

        btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                display.appendText(input.getText() + "\n");
            }
        });

        btnConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String user_name = user.getText();
                display.appendText(user_name + "連線成功!\n");
                btnConnect.setDisable(true);
                btnSubmit.setDisable(false);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
