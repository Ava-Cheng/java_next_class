package chapter31_network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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

public class V15_Client extends Application {

    TextArea display = new TextArea();
    TextArea input = new TextArea("How is it going?");
    TextField user = new TextField("路人甲");
    Button btnConnect = new Button("連線");
    Button btnSubmit = new Button("送出");
    private final int port = 1024;
    private final String server_ip = "localhost";
    //private final String server_ip = "163.18.23.21";

    DataOutputStream toServer;

    //建構子
    //建構子會優先執行，之後再執行public void start(Stage primaryStage){}
    public V15_Client() {

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

                //方式1:最簡單的
                try {
                    toServer.writeUTF(input.getText());
                    toServer.flush();
                } catch (IOException e) {
                    display.appendText("可能伺服器斷線，無法送出!\n");
                }

                /*
                //方式1:
                try {
                    toServer.writeUTF(input.getText());
                    toServer.flush();
                    System.out.println("執行1:完成傳送一筆訊息給伺服器");//這一行可以正常運作
                    
                    display.appendText("傳送一筆訊息給伺服器\n");
                    //上面這行會有異常：java.lang.ArrayIndexOutOfBoundsException: -1
                    //原因: toServer與display分屬不同的執行緒，個別運作不會產生問題，同時一起運作會產生異常
                    //方式1:改用以下方式執行:Platform.runLater去更新UI元件
                    //方式2: 執行緒方式執行
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            display.appendText("傳送一筆訊息給伺服器\n");
                            System.out.println("執行2:之後才會執行runLater()更新UI");
                        }
                    });

                } catch (IOException e) {
                    display.appendText("可能伺服器斷線，無法送出!\n");
                }
                 */
 /*
                //方式2: 執行緒方式執行
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("執行0:Thread run()開始");
                        try {
                            toServer.writeUTF(input.getText());
                            //toServer.flush();
                            System.out.println("執行1:完成傳送一筆訊息給伺服器");//這一行可以正常運作

                            //display.appendText("傳送一筆訊息給伺服器\n");
                            //上面這行會有異常：java.lang.ArrayIndexOutOfBoundsException: -1
                            //原因: toServer與display分屬不同的執行緒，個別運作不會產生問題，同時一起運作會產生異常
                            //方式1:改用以下方式執行:Platform.runLater去更新UI元件
                            //方式2: 執行緒方式執行
                            display.appendText("傳送一筆訊息給伺服器\n");
                            System.out.println("執行2:執行runLater()更新UI");
                        } catch (IOException e) {
                            display.appendText("可能伺服器斷線，無法送出!\n");
                        }
                        System.out.println("執行3:Thread run()結束");
                    }//run()
                }).start(); */
                System.out.println("執行4:先完成按鈕事件handle()");
            } //handle()
        }); //button setOnAction()

        btnConnect.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //方式1: 這種寫法最好
                new ClientConnect().start();
                System.out.println("建立一個Client連線執行緒完成");
                display.appendText("建立一個Client連線執行緒完成");

                /*方式2
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Socket socket = new Socket(server_ip, port);
                            display.appendText("連線成功!\n");

                            toServer = new DataOutputStream(socket.getOutputStream());

                            String user_name = user.getText();
                            toServer.writeUTF(user_name);

                            display.appendText(user_name + "已送出暱稱給伺服器!\n");
                            btnConnect.setDisable(true);
                            btnSubmit.setDisable(false);

                            DataInputStream fromServer = new DataInputStream(socket.getInputStream());
                            String msg = "";

                            msg = fromServer.readUTF();
                            display.appendText(msg);

                            //再讀一次!
                            //msg = fromServer.readUTF();
                            //display.appendText(msg);
                            while (true) {
                                msg = fromServer.readUTF();
                                display.appendText(msg+"\n");
                            }

                        } catch (IOException e) {
                            display.appendText("無法連線!\n");
                            System.out.println("無法連線\n" + e.toString());
                        }

                    }
                }).start();*/
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
                Socket socket = new Socket(server_ip, port);
                display.appendText("連線成功!\n");

                toServer = new DataOutputStream(socket.getOutputStream());

                String user_name = user.getText();
                toServer.writeUTF(user_name);

                display.appendText(user_name + "已送出暱稱給伺服器!\n");
                btnConnect.setDisable(true);
                btnSubmit.setDisable(false);

                DataInputStream fromServer = new DataInputStream(socket.getInputStream());
                String msg = "";

                //msg = fromServer.readUTF();
                //display.appendText(msg + "\n");
                //再讀一次!
                //msg = fromServer.readUTF();
                //display.appendText(msg);
                while (true) {
                    msg = fromServer.readUTF();
                    display.appendText(msg + "\n");
                }

            } catch (IOException e) {
                display.appendText("無法連線!\n");
                System.out.println("無法連線\n" + e.toString());
            }

        }
    }

}
