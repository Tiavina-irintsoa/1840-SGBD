package datacontainer;
import java.io.File;
import java.security.Key;
import java.util.Vector;
import datacontainer.Relation;
import keywords.From;
import keywords.KeyWord;

public class Execution {
    Relation[] listeRelations;
    KeyWord current=new From();
    String bdd="data";
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
    public Relation condition(Relation r,int where,String[] mots) throws Exception{
        String nomcol=mots[where+1];
        String comparateur=mots[where+2];
        String chaine=mots[where+3];
        if(r.isColonne(nomcol)==true){
            Relation r2=r.selection(nomcol, chaine, comparateur);
            int next=where+4;
            if(next<mots.length){
                while(mots[next].compareToIgnoreCase("na")==0 ||mots[next].compareToIgnoreCase("ary")==0){
                        nomcol=mots[next+1];
                        comparateur=mots[next+2];
                        chaine=mots[next+3];
                        Relation r3=r2.selection(nomcol,chaine,comparateur);
                    if(mots[next].compareToIgnoreCase("na")==0){
                        System.out.println("na");
                        r2=r2.union(r3);
                    }
                    else if(mots[next].compareToIgnoreCase("ary")==0){
                        r2=r2.intersection(r3);
                    }
                    next=next+4;
                    if(next>=mots.length){
                        break;
                    }
                }
            }
            return r2;
        }
        else{
            throw new Exception("colonne inexistante");
        }
    }

    //atambaro nomtable arakaraky nomcol
    public void jointure(Relation r1,int join,String[] mots) throws Exception{
        int next=join;
        String nombdd=null;
        String nomCol=null;
       try{
        while(mots[next].compareToIgnoreCase("atambaro")==0){
            nombdd=mots[next+1];
            
            if(mots[next+2].compareToIgnoreCase("arakaraky")!=0){
                throw new Exception("Syntaxe invalide");
            }
            System.out.println(mots[next+2]);
            Relation tojoin=null;
            try{
                nomCol=mots[next+3];
                tojoin=listeRelations[contains(nombdd)];
                // System.out.println(contains(nomCol));
            }
            catch(Exception e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            System.out.println(mots[next+2]);
            nomCol=mots[next+3];
            if(tojoin.isColonne(nomCol)==true && r1.isColonne(nomCol)==true){
                r1=r1.join(tojoin);
            }
            else{
                throw new Exception("colonne inexistante");
            }
            if(next+4>=mots.length){
                break;
            }
            next=next+4;
       }
    }
       catch(Exception e){
        e.printStackTrace();
        }
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

    public void execute(String sql) throws Exception{
        String[] mots=setFlexible(sql);
        int locate=0;
        Relation res=new Relation(null, null, null);
        boolean last=false;
        Vector<KeyWord> kw=new Vector<KeyWord>();
        KeyWord initiateur=new From();
        while(initiateur!=null){
            if(locate(mots,initiateur.getIntitule())!=-1){
                System.out.println(initiateur.getIntitule());
                kw.add(initiateur);
            }
            initiateur=initiateur.next();
        }
        int ikw=0;
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
    }
    public int contains(String nom) {
        for(int i=0;i<listeRelations.length;i++){
            if(nom.compareToIgnoreCase(listeRelations[i].getNom())==0){
                return i;
            }
        }
        return -1;
    }
    public Relation getRelation(int index){
        return listeRelations[index];
    }
}
