package chapter31_thread;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ThreadDemoJavaFXMain2 extends Application {

    TextArea display = new TextArea();

    //建構子
    public ThreadDemoJavaFXMain2() {
        System.out.println("建構子");

        //Thread m1 = new Thread(new Machine(2000));
        //Thread m2 = new Thread(new Machine(5000));

        //m1.start();
        //m2.start();

        //new Thread(new Machine(3000)).start(); 
        //為何需要執行緒?
        //順序執行:等待輸入
        /*
        new Thread(new Runnable() {

            @Override
            public void run() {

                while (true) {
                    System.out.println("請輸入:");
                    Scanner input = new Scanner(System.in);
                    String msg = input.next();
                    System.out.println("輸入值:" + msg);
                }

            }

        }).start();
         */
 /*
//執行緒執行:無窮迴圈
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(2000);
                        System.out.println("looping 2 seconds");
                    } catch (InterruptedException ex) {
                        System.out.println("break");
                    }
                }
            }
        }).start();
         */
 /*
        //執行緒執行:無窮迴圈
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(5000);
                        System.out.println("looping 5 seconds");
                    } catch (InterruptedException ex) {
                        System.out.println("break");
                    }
                }
            }
        }).start();
         */
 /*
        System.out.println("請輸入第1個數:");
        Scanner input = new Scanner(System.in);
        String msg = input.next();
        System.out.println("第1個數輸入值:" + msg);
         */
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //產生第1個視窗
        Button btn = new Button("OK");
        FlowPane root = new FlowPane();
        root.getChildren().add(display);
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("多執行緒");
        primaryStage.setScene(scene);
        primaryStage.show();

        System.out.println("start");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("按下按鈕!");

                new MachineThread(3000).start();
                //new Thread( new Machine(3000)  ).start();

            }
        });

    }

    public static void main(String[] args) {
        launch(args);
    }

    //類別內部之類別  內部類別inner class
    //為何寫成內部類別?
    class MachineThread extends Thread {

        private int time;

        public MachineThread(int time) {
            this.time = time;
        }

        @Override
        public void run() {
            int count = 0;
            while (true) {
                try {
                    sleep(time);
                    String msg = "零件" + count + "加工" + this.time + "毫秒\n";
                    display.appendText(msg);
                    System.out.println(msg);
                    count++;

                } catch (InterruptedException ex) {
                    System.out.println("中斷");
                }
            }
        }

    }

}

//檔案內部類別 生命範圍存在於套件內
class Machine3 implements Runnable {

    private int time;

    public Machine3(int time) {
        this.time = time;
    }

    @Override
    public void run() {

        while (true) {
            try {

                Thread.sleep(time);
                System.out.println("加工" + this.time + "毫秒");
            } catch (InterruptedException ex) {
                System.out.println("中斷");
            }
        }
    }

}
