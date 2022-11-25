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
                if(objis instanceof Relation){
                    ((Relation) objis).afficher();
                }
                if(objis instanceof Exception){
                    System.out.println(((Exception) objis).getMessage());
                }
                if(objis instanceof String){
                    System.out.println((String) objis);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
