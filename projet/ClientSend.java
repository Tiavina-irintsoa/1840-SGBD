package run;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

public class ClientSend extends Thread{
    String sql;
    Scanner sc;
    Socket client;
    public ClientSend(Scanner sc, OutputStream os, Socket client) throws Exception{
        this.sc=sc;
        this.client=client;
    }
    public void run() {
        try {
            OutputStream os=client.getOutputStream();
            ObjectOutputStream objos=new ObjectOutputStream(os);
            boolean connected=true;
            System.out.print("sql> ");
            while(connected){
                sql=sc.nextLine();
                objos.writeObject(sql);
                //si le client quitte
                if(sql.compareToIgnoreCase("bye")==0){
                    client.close();
                    connected=false;
                }
            }
        } catch (Exception e) {
        }
    }
}