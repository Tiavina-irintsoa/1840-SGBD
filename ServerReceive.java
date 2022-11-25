package run;

import java.io.*;
import java.net.Socket;

public class ServerReceive extends Thread{
    String sql;
    Object returnvalue;
    Socket client;
    public ServerReceive(Socket client) throws Exception{
        this.client=client;
    }
    public void run() {
        try {  
            InputStream is= client.getInputStream();
            ObjectInputStream objis=new ObjectInputStream(is);

            OutputStream os= client.getOutputStream();
            ObjectOutputStream objos=new ObjectOutputStream(os);

            while(true){
                System.out.println("new Request:");
                sql=(String) objis.readObject();
                System.out.println(sql);

                
                returnvalue="Retour";
                objos.writeObject(returnvalue);
            }
        }
        catch (Exception e) {
                e.printStackTrace();
        }
    }
}
