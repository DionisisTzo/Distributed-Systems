
import java.io.IOException;
import java.net.ServerSocket;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;


public class ClientMain {
    
    public static void main(String[] args) {
        new HomePage(); // start the program
    }
    
//    public ServerSocket createServerSocket(int port)throws IOException{
//        ServerSocketFactory factory = SSLServerSocketFactory.getDefault();
//        ServerSocket socket = factory.createServerSocket(port);
//        return socket;
//    }
}
