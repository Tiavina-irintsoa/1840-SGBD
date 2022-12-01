package process;
import java.io.File;
import datacontainer.*;
import java.util.Vector;
import datacontainer.Relation;
import keywords.*;

public class Execution {
    KeyWord current=new Use();
    Database used;
    Database[] listeDatabases;
    public void setUsed(String db) throws Exception{
        this.used=getDatabase(db);
    }
    public void update() throws Exception{
        File directory=new File("database");
        File[] files=directory.listFiles();
        this.listeDatabases=new Database[files.length];
        try {
            for(int i=0;i<files.length;i++){
                System.out.println("base de donnees"+files[i].getName());
                listeDatabases[i]=new Database(files[i].getName());
                if(used!=null){
                    if(listeDatabases[i].getNom().compareTo(used.getNom())==0){
                        this.used=listeDatabases[i];
                    }
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Database getDatabase(String nom) throws Exception{
        for(int i=0;i<listeDatabases.length;i++){
            if(nom.compareToIgnoreCase(listeDatabases[i].getNom())==0){
                return listeDatabases[i];
            }
        }
        throw new Exception("base de donnees introuvable");
    }
    public Execution() throws Exception{
        File directory=new File("database");
        File[] files=directory.listFiles();
        this.listeDatabases=new Database[files.length];
        try {
            for(int i=0;i<files.length;i++){
                listeDatabases[i]=new Database(files[i].getName());
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Database getBdd(){
        return this.used;
    }
    public int locate(String[] sql,String recherche){
        for(int i=0;i<sql.length;i++){
            if(recherche.compareToIgnoreCase(sql[i])==0){
                return i;
            }
        }
        return -1;
    }
    public String[] setFlexible(String sql){
        String[] mots=sql.split(" ");
        Vector<String> vrais=new Vector<String>();
        for(int i=0;i<mots.length;i++){
            if(mots[i].compareToIgnoreCase(" ")!=-1){
                System.out.println(mots[i]);
                vrais.add(mots[i].trim());
            }
        }
        String[] res=new String[vrais.size()];
        for(int i=0;i<vrais.size();i++){
            System.out.println("res[i] "+vrais.get(i));
            res[i]=vrais.get(i);
        }
        return res;
    }

    public Object execute(String sql) throws Exception{
        String[] mots=setFlexible(sql);
        int locate=0;
        boolean last=false;
        Vector<KeyWord> kw=new Vector<KeyWord>();
        KeyWord initiateur=new Create();
        while(initiateur!=null){
            if(locate(mots,initiateur.getIntitule())!=-1){
                System.out.println(initiateur.getIntitule());
                kw.add(initiateur);
            }
            initiateur=initiateur.next();
        }
        int ikw=0;
        Object res=null;
        while(ikw<kw.size()){
            locate=locate(mots,kw.get(ikw).getIntitule());
            System.out.println(locate+"locate");
            Vector<String> args=new Vector<String>();
            int nearest=kw.get(ikw).nearest(mots,kw,locate,kw.get(ikw).getIntitule());
            System.out.println("nearest"+nearest);
            for(int i=locate+1;i<nearest;i++){
                args.add(mots[i]);
                System.out.println("args: "+mots[i]);
            }
            if(kw.get(ikw).getIntitule().compareToIgnoreCase("drop")!=0 && kw.get(ikw).getIntitule().compareToIgnoreCase("use")!=0 && kw.get(ikw).getIntitule().compareToIgnoreCase("create")!=0){
                if(this.used==null){
                    throw new Exception("Aucune base de donnee selectionnee");
                }
            }  
            res=kw.get(ikw).execute(res,this,args);          
            ikw++;
        }
        if(res instanceof Relation){
            return ((Relation) res).toVector();
        }
        return res;
    }
}
