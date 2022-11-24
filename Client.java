package connection;

import java.io.*;
import java.net.Socket;
import java.util.*;

import run.ClientReceive;
import run.ClientSend;

public class Client {
    public static void main(String[] args) {
        final Socket clientSocket;
        OutputStream os;
        final InputStream is;
        final Scanner sc = new Scanner(System.in);
        try {
            clientSocket = new Socket("localhost",1000);
            os = clientSocket.getOutputStream();
            is = clientSocket.getInputStream();
            ClientSend clientsend=new ClientSend(sc, os, clientSocket);
            clientsend.start();

            ClientReceive ClientReceive=new ClientReceive(clientSocket);
            ClientReceive.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
