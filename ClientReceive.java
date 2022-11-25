package run;

import java.io.ObjectInputStream;
import java.net.Socket;

import datacontainer.Relation;

public class ClientReceive extends Thread{
    Socket client;
    public ClientReceive(Socket clientSocket){
        this.client = clientSocket;
    }
    public void run(){
        try {
            Object objis=new ObjectInputStream(client.getInputStream());
            while(true){
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
