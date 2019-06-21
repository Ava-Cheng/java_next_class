package chapter31_thread;

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
         
         DataInputStream fromClient = new DataInputStream( socket.getInputStream()  );
        
         String msg =fromClient.readUTF();
         
         System.out.println(msg);
         
         DataOutputStream toClient = new DataOutputStream( socket.getOutputStream()   );

         //送出訊息
         String response = String.format("伺服器回應:%s你好!", msg);
         toClient.writeUTF(  response   );
        
    }

}
