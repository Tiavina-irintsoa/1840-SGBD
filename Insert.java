package keywords;
import process.*;
import java.util.Vector;

import datacontainer.Fichier;

import datacontainer.Relation;
public class Insert extends KeyWord{
    public Insert(){
        super("insert");
        super.setNext(new Use());
    }
    //insert into nomtable a,b,n
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        checkSyntaxe(args, exec);
        String[] values=args.get(2).split(",");
        Relation r=exec.getBdd().getRelation(exec.getBdd().contains(args.get(1)));
        if(values.length>r.getNomColonnes().length){
            throw new Exception("Exces de valeurs a inserer");
        }
        Fichier f=new Fichier("database/"+exec.getBdd().getNom()+"/"+r.getNom()+"/donnees");
        f.insert(values);
        return "Donnees inserees";
    }
    public void checkSyntaxe(Vector<String> args, Execution exec)throws Exception{
        if(args.get(0).compareToIgnoreCase("into")!=0){
            throw new Exception("Syntax Error");
        }
        if(exec.getBdd().contains(args.get(1))==-1){
            throw new Exception("table inexistante");
        }
    }
}