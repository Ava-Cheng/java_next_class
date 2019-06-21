package chapter51_threading;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class client {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 1024);
        DataInputStream fromServer = new DataInputStream(socket.getInputStream());
        DataOutputStream toServer = new DataOutputStream(socket.getOutputStream());
        //送出訊息
        toServer.writeUTF("李大同");
        //讀入
        System.out.println(fromServer.readUTF());
    }

}
