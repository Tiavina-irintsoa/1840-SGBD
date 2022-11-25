package run;

import java.io.*;
import java.net.Socket;

import datacontainer.Execution;

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
                if(sql.compareToIgnoreCase("bye")==0){
                    client.close();
                }
                try {
                    Execution exec=new Execution();
                    returnvalue=exec.execute(sql);
                } catch (Exception e) {
                    e.printStackTrace();
                    returnvalue=e;
                }
                finally{
                    objos.writeObject(returnvalue);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
