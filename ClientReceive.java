package run;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientReceive extends Thread{
    Socket client;
    public ClientReceive(Socket clientSocket){
        this.client = clientSocket;
    }
    public void run() {
        try {
            ObjectInputStream objis=new ObjectInputStream(client.getInputStream());
            while(true){
                System.out.println((String) objis.readObject());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
