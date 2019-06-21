package chapter31_network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final String server_ip = "localhost";

    DataOutputStream toServer;
    DataInputStream fromServer;

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
                try {
                    //送出留言訊息給伺服器
                    toServer.writeUTF(input.getText());
                } catch (IOException ex) {
                    display.appendText("傳送訊息發生異常(斷線)\n");
                    System.out.println("傳送訊息發生異常(斷線)" + ex.toString());
                }

                display.appendText(input.getText() + "\n");
            }
        });

        btnConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new ClientConnect().start();
                System.out.println("建立一個Client連線執行緒完成\n");
                display.appendText("建立一個Client連線執行緒完成\n");
                /*try {
                    //產生一個socket物件-連線到伺服器
                    Socket socket = new Socket(server_ip, 1024);
                    toServer = new DataOutputStream(socket.getOutputStream());
                    fromServer = new DataInputStream(socket.getInputStream());

                    display.appendText("連線成功\n");
                    System.out.println("連線成功\n");

                    //送出使用者名稱給伺服器
                    String user_name = user.getText();
                    toServer.writeUTF(user_name);

                    //按鈕狀態與顯示
                    display.appendText(user_name + "送出使用者名稱給伺服器\n");
                    btnConnect.setDisable(true);
                    btnSubmit.setDisable(false);

                    //讀入一次伺服器送過來資訊
                    String msg = "";
                    msg = fromServer.readUTF();
                    display.appendText(msg + "\n");

                    //再讀一次
                    //msg = fromServer.readUTF();
                    //display.appendText(msg + "\n");
                    while (true) {
                        msg = fromServer.readUTF();
                        display.appendText(msg + "\n");
                    }

                } catch (IOException ex) {
                    System.out.println("無法連線\n" + ex.toString());
                }*/

                //display.appendText(user_name + "連線成功!\n");
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    class ClientConnect extends Thread {

        @Override
        public void run() {
            try {
                //產生一個socket物件-連線到伺服器
                Socket socket = new Socket(server_ip, 1024);
                toServer = new DataOutputStream(socket.getOutputStream());
                fromServer = new DataInputStream(socket.getInputStream());

                display.appendText("連線成功\n");
                System.out.println("連線成功\n");

                //送出使用者名稱給伺服器
                String user_name = user.getText();
                toServer.writeUTF(user_name);

                //按鈕狀態與顯示
                display.appendText(user_name + "送出使用者名稱給伺服器\n");
                btnConnect.setDisable(true);
                btnSubmit.setDisable(false);

                //讀入一次伺服器送過來資訊
                String msg = "";
                msg = fromServer.readUTF();
                display.appendText(msg + "\n");

                //再讀一次
                //msg = fromServer.readUTF();
                //display.appendText(msg + "\n");
                while (true) {
                    msg = fromServer.readUTF();
                    //display.appendText(msg + "\n");
                }

            } catch (IOException ex) {
                System.out.println("無法連線\n" + ex.toString());
            }
        }
    }
}
