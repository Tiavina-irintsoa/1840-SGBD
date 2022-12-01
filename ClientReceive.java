package run;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Vector;

import datacontainer.Relation;

public class ClientReceive extends Thread{
    Socket client;
    public ClientReceive(Socket clientSocket){
        this.client = clientSocket;
    }
    public void run(){
        try {
            ObjectInputStream objis=new ObjectInputStream(client.getInputStream());
            while(true){
                Object returnvalue=objis.readObject();
                if(returnvalue instanceof Vector){
                    Vector<Vector<String>> v=(Vector<Vector<String>>) returnvalue;
                    for(int i=0;i<v.get(0).size();i++){
                        System.out.print("| "+v.get(0).get(i)+" |");
                    }
                    System.out.println("");
                    for(int i=1;i<v.size();i++){
                        for(int j=0;j<v.get(i).size();j++){
                            System.out.print("| "+v.get(i).get(j)+" | ");
                        }
                        System.out.println("");
                    }
                }
                if(returnvalue instanceof Exception){
                    System.out.println(((Exception) returnvalue).getMessage());
                }
                if(returnvalue instanceof String){
                    System.out.println((String) returnvalue);
                }
                System.out.println("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
