package connection;

import java.io.*;
import java.net.Socket;
import java.util.*;

import datacontainer.Fichier;
import run.ClientReceive;
import run.ClientSend;

public class Client {
    public static void main(String[] args) {
        Socket clientSocket;
        OutputStream os;
        final InputStream is;
        final Scanner sc = new Scanner(System.in);
        try {
            //connexion socket
            HostPort hp=Fichier.getHostPort();
            String host=hp.getHost();
            int port=hp.getPort();
            clientSocket = new Socket(host,port);
            os = clientSocket.getOutputStream();
            is = clientSocket.getInputStream();
            System.out.println("");
            System.out.println("____________1840-SGBD____________");
            System.out.println("");
            //thread d'envoi de requetes
            ClientSend clientsend=new ClientSend(sc, os, clientSocket);
            clientsend.start();

            //thread de reception des requetes
            ClientReceive ClientReceive=new ClientReceive(clientSocket);
            ClientReceive.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
