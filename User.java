package connection;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import run.*;
public class User {
    OutputStream os;
    InputStream is;
    int id;

    public User(int id){
        this.id=id;
    }
    public void interact(Socket clientSocket){
        System.out.println("Nouveau client: "+id);
        try {
            is=clientSocket.getInputStream();  
            ServerReceive serverReceive= new ServerReceive(is);
            serverReceive.start();      
        } catch (Exception e) {
            // TODO: handle exception
        }
        
    }
}
