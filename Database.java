package datacontainer;
import java.io.File;

public class Database {
    Relation[] listeRelations;
    String nom;
    
    public String getNom(){
        return nom;
    }
    public void setListeRelation(int index, Relation r){
        this.listeRelations[index]=r;
    }
    public int contains(String nom) {
        System.out.println("length "+ listeRelations.length);
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
    public Relation[] getListeRelations() {
        return listeRelations;
    }
    public void setListeRelation(Relation[] nouveau){
        this.listeRelations=nouveau;
    }
    public Database(String nom) throws Exception{
        this.nom=nom;
        File directory=new File("database/"+this.nom);
        File[] files=directory.listFiles();
        System.out.println("database/"+this.nom);
        this.listeRelations= new Relation[files.length];
        try {
            for(int i=0;i<files.length;i++){
                System.out.println("listtable: "+files[i].getName());
                setListeRelation(i,new Relation(files[i].getName(),nom));
            }
        } 
        catch (Exception e) {
            throw e;
        }
    }
}
