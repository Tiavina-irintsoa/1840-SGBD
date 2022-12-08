package keywords;
import process.*;
import process.Execution;

import java.util.Vector;
import datacontainer.*;
public class Delete extends KeyWord{
    public Delete(){
        super("delete");
        super.setNext(null);
    }
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        checkSyntaxe(exec);
        Relation toDelete=(Relation) res;
        int index=exec.getBdd().contains(toDelete.getNom());
        Relation original=exec.getBdd().getRelation(index);
        Relation nouveau=original.difference(toDelete);
        Fichier f=new Fichier("database/"+exec.getBdd().getNom()+"/"+toDelete.getNom()+"/donnees");
        f.rewrite(nouveau.getContent());
        return "Suppresion effectue";
    }
    public void checkSyntaxe(Execution exec ){
        if(exec.getBdd()==null){
            throw new Exception("Aucune base de donnees selectionnee");
        }
    }    
}
