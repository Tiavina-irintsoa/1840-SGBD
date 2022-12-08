package datacontainer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;


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
            System.out.println(path+"/donnees");

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
            System.out.println("delete");
        }
        this.delete();
    }
    public Vector<String> read(String fichier) throws Exception{
        System.out.println("Fichier.read()");
        Scanner sc=new Scanner(new File(this.path+"/"+fichier));
        Vector<String> v=new Vector<String>();
        // System.out.println("read");
        String nextLine=null;
        while(sc.hasNextLine()==true){
            // System.out.println("in")
            nextLine=sc.nextLine();
            v.add(nextLine);
            // System.out.println(nextLine);
        }
        sc.close();
        return v;
    }
}

