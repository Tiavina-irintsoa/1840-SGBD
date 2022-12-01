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
    public static void insert(String[] values,String nomTable) throws Exception{
        Fichier f=new Fichier("data/"+nomTable+"/donnees");
        FileWriter fr=new FileWriter(f,true);
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
    public static Vector<String> read(String path,String fichier) throws Exception{

        Scanner sc=new Scanner(new File("data/"+path+"/"+fichier));
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

