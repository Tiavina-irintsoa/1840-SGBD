package keywords;
import java.util.Vector;
import datacontainer.*;
public class Delete extends KeyWord{
    public Delete(){
        super("delete");
        super.setNext(null);
    }
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        Relation toDelete=(Relation) res;
        int index=exec.contains(toDelete.getNom());
        Relation original=exec.getRelation(index);
        System.out.println("original");
        original.afficher();

        Relation nouveau=original.difference(toDelete);
        Fichier f=new Fichier("data/"+toDelete.getNom()+"/donnees");
        f.rewrite(nouveau.getContent());
        return "Suppresion effectue";
    }    
}
