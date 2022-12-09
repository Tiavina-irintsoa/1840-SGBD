package connection;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final ServerSocket serverSocket;
        final OutputStream os;
        final InputStream is;
        int port=1000;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("En attende de clients...");
            int iduser=1;
            while(true){
                System.out.println("Attente");
                Socket client =  serverSocket.accept();
                try {
                    User user=new User(iduser);
                    user.interact(client);
                    iduser++;
                } catch (Exception e) {
                    System.err.println(e);
                }
                // client = null;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
