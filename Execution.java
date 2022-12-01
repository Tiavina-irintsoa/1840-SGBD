package process;
import java.io.File;
import datacontainer.*;
import java.util.Vector;
import datacontainer.Relation;
import keywords.*;

public class Execution {
    KeyWord current=new From();
    Database used;
    Database[] listeDatabases;
    public void update() throws Exception{
        File directory=new File("database/"+used.getNom());
        File[] files=directory.listFiles();
        used.setListeRelation(new Relation[files.length]);
        try {
            for(int i=0;i<files.length;i++){
                this.used.setListeRelation(i,new Relation(files[i].getName()));
            }
        } 
        catch (Exception e) {
            throw e;
        }
    }
    public void setUsed(String db){
        
    }
    public Execution(){
        File directory=new File("database");
        File[] files=directory.listFiles();
        this.listeDatabases=new Database[files.length];
        
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
            res=kw.get(ikw).execute(res,this,args);
            
            ikw++;
        }
        if(res instanceof Relation){
            return ((Relation) res).toVector();
        }
        return res;
    }
}
