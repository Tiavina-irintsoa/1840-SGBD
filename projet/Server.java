package connection;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import run.*;
import datacontainer.Fichier;

public class Server {
    public static void main(String[] args) {
        final ServerSocket serverSocket;
        final OutputStream os;
        final InputStream is;
        int port;
        try {
            port=Fichier.getHostPort().getPort();
            serverSocket = new ServerSocket(port);
            System.out.println("Port:"+port);
            System.out.println("En attende de clients...");
            while(true){
                System.out.println("Attente");
                Socket client =  serverSocket.accept();
                try {
                    ServerReceive serverReceive= new ServerReceive(client);
                    serverReceive.start();
                } catch (Exception e) {
                    System.err.println(e);
                }
                // client = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
