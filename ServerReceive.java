package run;

import java.io.*;

public class ServerReceive extends Thread{
    String sql;
    ObjectInputStream objis;
    public ServerReceive(InputStream is) throws Exc
    
    
    
    
    eption{
        objis=new ObjectInputStream(is);
    }
    public void run() {
        try {  
            while(true){
                System.out.println("yes");
                sql=(String) objis.readObject();
                System.out.println(sql);
            }
        }
        catch (Exception e) {
                e.printStackTrace();
        }
    }
}
