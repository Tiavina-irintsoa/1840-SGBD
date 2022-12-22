package datacontainer;
import java.io.File;
import java.util.ArrayList;

public class Database {
    Relation[] listeRelations;
    String nom;
    
    public String getNom(){
        return nom;
    }
    public void setListeRelation(int index, Relation r){
        this.listeRelations[index]=r;
    }

    //pour savoir si l'argument nom est une table dans cette bdd
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
    public Relation[] getListeRelations() {
        return listeRelations;
    }

    // la liste des tables
    public ArrayList<String> getTableName(){
        ArrayList<String> res=new ArrayList<String>();
        for(int i=0;i<listeRelations.length;i++){
            res.add(listeRelations[i].getNom());
        }
        return res;
    }
    public void setListeRelation(Relation[] nouveau){
        this.listeRelations=nouveau;
    }
    public Database(String nom) throws Exception{
        this.nom=nom;
        File directory=new File("database/"+this.nom);
        File[] files=directory.listFiles();
        this.listeRelations= new Relation[files.length];
        try {
            for(int i=0;i<files.length;i++){
                setListeRelation(i,new Relation(files[i].getName(),nom));
            }
        } 
        catch (Exception e) {
            throw e;
        }
    }
}
