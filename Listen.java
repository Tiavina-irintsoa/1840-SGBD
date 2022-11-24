package run;
import java.io.*;
import java.net.*;
public class Listen extends Thread{
    ServerSocket server;
    Socket client;
    public Listen(ServerSocket serversocket){
        this.server=serversocket;

    }
    public void run(){
        try{
            while(true){
                client=this.server.accept();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public Socket getClient() {
        return client;
    } 
}
