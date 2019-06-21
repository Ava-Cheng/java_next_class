package chapter51_threading;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class threading extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button btn = new Button("OK");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("OK");
            }
        });

        FlowPane root = new FlowPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("第1個視窗");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public threading() {
        Thread m1 = new Thread(new Machine2(2000));
        m1.start();
        new Thread(new Machine2(5000)).start();
        /*
        //執行緒執行:無窮迴圈
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        System.out.println("looping 2s");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(threading.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();

        //執行緒執行:無窮迴圈
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        System.out.println("looping 5s");
                    } catch (InterruptedException ex) {
                        Logger.getLogger(threading.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }).start();

        //為何需要執行緒?
        //順序執行:等待輸入
        System.out.println("請輸入第1個數:");
        Scanner input = new Scanner(System.in);
        String msg = input.next();
        System.out.println("第1個數輸入值:" + msg);
        //執行緒執行:等待輸入
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("請輸入第2個數:");
                Scanner input = new Scanner(System.in);
                String msg = input.next();
                System.out.println("第2個數輸入值:" + msg);
            }
        }).start();
         */
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Machine2 implements Runnable {

    int time;

    public Machine2(int time) {
        this.time = time;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                System.out.println(this.time + "s");
            } catch (InterruptedException ex) {
                Logger.getLogger(threading.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
