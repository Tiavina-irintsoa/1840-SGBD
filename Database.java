package datacontainer;
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
    public void setListeRelation(Relation[] nouveau){
        this.listeRelations=nouveau;
    }
}
