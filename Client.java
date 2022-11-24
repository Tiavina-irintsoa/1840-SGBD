package view;
import java.io.*;
import java.net.*;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class Client {
    public void setMessage(String message, ObjectOutputStream object) throws Exception{
        object.writeObject(message);
    }
    public Client(int port, String host){
        try{
            Scanner sc=new Scanner(System.in);
            boolean connected=true;
            Socket client = new Socket(host,port);
            OutputStream os=client.getOutputStream();
            ObjectOutputStream sql=new ObjectOutputStream(os);
            while(connected==true){
                System.out.print(">Requete: ");
                String request = sc.nextLine();
                setMessage(request, sql);

                if(request.compareToIgnoreCase("quit")==0){
                    break;
                }
                InputStream is=client.getInputStream();  
                ObjectInputStream mess=new ObjectInputStream(is);                
                String obj=String.valueOf(mess.readObject());
                System.out.println(obj);

            }
            os.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
