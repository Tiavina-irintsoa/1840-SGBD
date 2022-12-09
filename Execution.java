package process;
import java.io.File;
import datacontainer.*;
import java.util.ArrayList;
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

    //pour savoir si l'argument nom est une bdd
    public int isDatabase(String nom){
        for(int i=0;i<listeDatabases.length;i++){
            if(nom.compareToIgnoreCase(listeDatabases[i].getNom())==0){
                return i;
            }
        }
        return -1;
    }
    //mise a jour a chaque requete
    public void update() throws Exception{
        File directory=new File("database");
        File[] files=directory.listFiles();
        this.listeDatabases=new Database[files.length];
        try {
            for(int i=0;i<files.length;i++){
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
    //situer un mot dans la requete 
    public int locate(String[] sql,String recherche){
        for(int i=0;i<sql.length;i++){
            if(recherche.compareToIgnoreCase(sql[i])==0){
                return i;
            }
        }
        return -1;
    }

    //enlever les espaces du sql
    public String[] setFlexible(String sql){
        String[] mots=sql.split(" ");
        Vector<String> vrais=new Vector<String>();
        for(int i=0;i<mots.length;i++){
            if(mots[i].compareToIgnoreCase(" ")!=-1){
                vrais.add(mots[i].trim());
            }
        }

        String[] res=new String[vrais.size()];
        for(int i=0;i<vrais.size();i++){
            res[i]=vrais.get(i);
        }
        return res;
    }
    //liste des base de donnees
    public ArrayList<String> getDatabaseName(){
        ArrayList<String> res=new ArrayList<String>();
        for(int i=0;i<listeDatabases.length;i++){
            res.add(listeDatabases[i].getNom());
        }
        return res;
    }

    //executer la requete
    public Object execute(String sql) throws Exception{
        String[] mots=setFlexible(sql);
        int locate=0;
        boolean last=false;
        Vector<KeyWord> kw=new Vector<KeyWord>();
        KeyWord initiateur=new Create();
        while(initiateur!=null){
            if(locate(mots,initiateur.getIntitule())!=-1){
                kw.add(initiateur);
            }
            initiateur=initiateur.next();
        }
        if(kw.size()==0){
            throw new Exception("commande inconnue");
        }
        int ikw=0;
        Object res=null;
        while(ikw<kw.size()){
            locate=locate(mots,kw.get(ikw).getIntitule());
            Vector<String> args=new Vector<String>();
            int nearest=kw.get(ikw).nearest(mots,kw,locate,kw.get(ikw).getIntitule());
            for(int i=locate+1;i<nearest;i++){
                args.add(mots[i]);
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
