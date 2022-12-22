package datacontainer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;

import connection.HostPort;

import java.io.File;
public class Fichier extends File{
    String path;

    public Fichier(String path){
        super(path);
        this.path=path;
    }
    public void writeCols(String[] nomColonnes, Fichier file) throws Exception{
        FileWriter fr=new FileWriter(file,true);
        BufferedWriter bfw=new BufferedWriter(fr);
        for(int i=0;i<nomColonnes.length;i++){
            bfw.write(nomColonnes[i]+"%%");
        }
        bfw.close();
    }
    public void creerTable(String[] nomCols) throws Exception{
        if(this.exists()==false){
            this.mkdir();

            Fichier  nomColonnes=new Fichier(path+"/nomcolonnes");
            nomColonnes.createNewFile();
            Fichier donnees=new Fichier (path+"/donnees");
            donnees.createNewFile();
            writeCols(nomCols,nomColonnes);
        }
        else{
            throw new Exception("Table existante");
        }
    }
    public void insert(String[] values) throws Exception{
        FileWriter fr=new FileWriter(this,true);
        BufferedWriter bfw=new BufferedWriter(fr);
        for(int i=0;i<values.length;i++){
            bfw.write(values[i]+"%%");
        }
        bfw.newLine();
        bfw.close();
    }
    public void rewrite(Object[][] values) throws Exception{
        FileWriter fr=new FileWriter(this.path,false);
        BufferedWriter bfw=new BufferedWriter(fr);
        for(int row=0;row<values.length;row++){
            String toInsert="";
            for(int col=0;col<values[0].length;col++){
                toInsert=toInsert+String.valueOf(values[row][col])+"%%";
            }
            bfw.write(toInsert);
            bfw.newLine();
        }
        bfw.close();
    }
    public void drop() throws Exception{
        File[] inside=this.listFiles();
        for(int i=0;i<inside.length;i++){
            if(inside[i].isDirectory()){
                Fichier directory=new Fichier(inside[i].getPath());
                directory.drop();
            }
            inside[i].delete();
        }
        this.delete();
    }

    public static HostPort getHostPort() throws Exception{
        HostPort hp=new HostPort();
        Scanner sc=new Scanner(new File("config/config.txt"));
        Vector<String> v=new Vector<String>();
        String nextLine=null;
        while(sc.hasNextLine()==true){
            nextLine=sc.nextLine();
            String content=nextLine.split(":")[1].trim();
            v.add(content);
        }
        if(v.size()<2){
            throw new Exception("Hote ou port introuvable");
        }
        hp.setHost(v.get(1));
        hp.setPort(v.get(0));
        sc.close();
        return hp;
    }
    public Vector<String> read(String fichier) throws Exception{
        Scanner sc=new Scanner(new File(this.path+"/"+fichier));
        Vector<String> v=new Vector<String>();
        String nextLine=null;
        while(sc.hasNextLine()==true){
            nextLine=sc.nextLine();
            v.add(nextLine);
        }
        sc.close();
        return v;
    }
}

