package chapter31_network;

import chapter31_network.V15_Client.ClientConnect;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class V15_Server extends Application {

    // 全域變數 整個程式有多個地方會用這些變數
    private final TextArea display = new TextArea();
    private final Button btnSubmit = new Button("送出");
    private final Button btnConnect = new Button("啟動伺服器");
    private final TextArea input = new TextArea("伺服器訊息測試");
    private final int port = 1024;
    private final String ip = "localhost";

    private String name;
    private DataOutputStream toClient;
    private final List<DataOutputStream> output2clients = new ArrayList();
    public V15_Server() {

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
                new ServerStart().start();
                display.appendText("建立一個伺服器執行緒完成\n");

            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    class ServerStart extends Thread {

        @Override
        public void run() {
            try {
                ServerSocket server = new ServerSocket(port);
                display.appendText("伺服器啟動成功!\n");
                System.out.println("here");
                btnConnect.setDisable(true);
                btnSubmit.setDisable(false);
                while (true) {
                    //(1)等待有client連線，
                    // 天荒地老地等下去直到有人連線，才會跳到下一行程式
                    Socket socket = server.accept();
                    display.appendText("上線者IP:" + socket.getInetAddress() + "\n");
                    new ClientConnect(socket).start();
                    display.appendText("建立一個客戶端連線執行緒完成" + socket.getInetAddress() + "\n");
                    System.out.println("建立一個客戶端連線執行緒完成" + socket.getInetAddress());
                }
            } catch (IOException e) {
                display.appendText("建立伺服器異常\n");
                display.appendText(e.toString() + "\n");
                //System.out.println(e.toString());
            }
        }
    }

    class ClientConnect extends Thread {

        private Socket socket;

        public ClientConnect(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // try區塊2-----------------
                // 建立輸出與輸入串流
                DataInputStream fromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
                //讀取一次，取得姓名
                name = fromClient.readUTF();
                display.appendText("上線者:" + name + "\n");
                
                output2clients.add(toClient);
                display.appendText("目前聊天室有" + output2clients.size() + "人\n");
                
                while (true) {
                    //讀取使用者送來的訊息
                    String msg = fromClient.readUTF();
                    //使用者訊息很多，輸出到terminal，或存到log檔
                    display.appendText(String.format("%s: %s\n", name, msg));
                    //System.out.printf("%s: %s\n", name, msg);
                    //toClient.writeUTF(String.format("%s: %s", name, msg));
                    //送出訊息給所有的通道這才是我們要的功能
                    for (DataOutputStream writer : output2clients) {
                        writer.writeUTF(String.format("%s: %s", name, msg));
                        //writer.flush();
                    }
                }
            } catch (IOException e) {
                display.appendText("client結束通訊:" + socket.getInetAddress() + "\n");
                display.appendText("client結束通訊:" + name + "\n");
                output2clients.remove(toClient);//移除toClient串流
                display.appendText("目前聊天室有" + output2clients.size() + "人\n");
            }
        }
    }
}
