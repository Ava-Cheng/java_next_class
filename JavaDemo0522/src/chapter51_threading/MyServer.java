package chapter51_threading;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1024);
        //等待連線...
        System.out.println("等待連線...");
        Socket socket = server.accept();
        System.out.println(socket.getInetAddress());
        //建立串流
        DataInputStream fromClient = new DataInputStream(socket.getInputStream());
        DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
        //讀訊息伺服器read讀取次數必須與client配合
        //讀不到資料時會天荒地老等read下去甚至會打死結
        String msg = fromClient.readUTF();
        System.out.println(msg);
        
        //送出訊息
        String response = String.format("伺服器回應:%s", msg);
        toClient.writeUTF(response);
        
    }

}
