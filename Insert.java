package keywords;
import java.util.Vector;
import datacontainer.Execution;
import datacontainer.Fichier;

import datacontainer.Relation;
public class Insert extends KeyWord{
    public Insert(){
        super("insert");
        super.setNext(new From());
    }
    //insert into nomtable a,b,n
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        checkSyntaxe(args, exec);
        String[] values=args.get(2).split(",");
        Relation r=exec.getRelation(exec.contains(args.get(1)));
        Fichier.insert(values, r.getNom());
        return "Donnees inserees";
    }
    public void checkSyntaxe(Vector<String> args, Execution exec)throws Exception{
        if(args.get(0).compareToIgnoreCase("into")!=0){
            throw new Exception("Syntax Error");
        }
        if(exec.contains(args.get(1))==-1){
            throw new Exception("table inexistante");
        }
    }
}