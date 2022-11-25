package datacontainer;
import java.io.File;
import java.util.Vector;
import datacontainer.Relation;
import keywords.*;

public class Execution {
    Relation[] listeRelations;
    KeyWord current=new From();
    String bdd="data";

    public String getBdd(){
        return bdd;
    }   
    public Execution() throws Exception{
        File directory=new File(bdd);
        File[] files=directory.listFiles();
        this.listeRelations=new Relation[files.length];
        try {
            for(int i=0;i<this.listeRelations.length;i++){
                this.listeRelations[i]=new Relation(files[i].getName());
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Veuillez verifier les fichiers");
       }
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
        return res;
    }
    public int contains(String nom) {
        for(int i=0;i<listeRelations.length;i++){
            if(nom.compareToIgnoreCase(listeRelations[i].getNom())==0){
                System.out.println("contains"+nom);
                return i;
            }
        }
        return -1;
    }
    public Relation getRelation(int index){
        return listeRelations[index];
    }
}
