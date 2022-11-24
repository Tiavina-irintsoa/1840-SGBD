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
            while(true){
                sql=sc.nextLine();
                System.out.println("sql: "+sql);
                objos.writeObject(sql);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}