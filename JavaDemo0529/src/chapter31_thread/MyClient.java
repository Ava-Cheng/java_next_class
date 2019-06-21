package chapter31_thread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MyClient {

    public static void main(String[] args) throws IOException {
        
        Socket socket = new Socket("localhost",   1024  );
        
        DataOutputStream toServer = new DataOutputStream( socket.getOutputStream()   );
        
        toServer.writeUTF( "李大同"  );
        
         //讀入
         DataInputStream fromServer = new DataInputStream(socket.getInputStream());
         System.out.println(fromServer.readUTF());
  
        
    }

}
