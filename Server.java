package model;
import java.io.*;
import java.net.*;
import java.util.Scanner;

import run.*;
import datacontainer.*;
public class Server{
    int port=1010;
    public Server(){
        final ServerSocket server;
        final InputStream is;
        final ObjectInputStream mess;
        final Scanner sc = new Scanner() 
        try{
           server = new ServerSocket(port); 
            boolean connected = true;
            while(connected==true){
                Socket client=server.accept();
                System.out.println("Found a client");
                is=client.getInputStream();
                mess=new ObjectInputStream(is);
                String obj=String.valueOf(mess.readObject());
                System.out.println(obj);

                OutputStream os=client.getOutputStream();
                ObjectOutputStream response=new ObjectOutputStream(os);
                Execution e=new Execution();
                e.execute(obj);
                Scanner sc=new Scanner(System.in);
                String resp="";
                while(sc.hasNextLine()==true){
                    System.out.println("niditra");
                    resp=resp+sc.nextLine();
                }
                System.out.println(resp);
                response.writeObject(resp);
                is.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

}