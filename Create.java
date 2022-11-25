package keywords;
import java.util.Vector;
import datacontainer.Execution;
import datacontainer.Fichier;

import datacontainer.Relation;
public class Create extends KeyWord{
    public Create(){
        super("create");
        super.setNext(new Insert());
    }

    //create table ... with x,y,z
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        try {
            checkSyntaxe(args);
            Fichier f=new Fichier(exec.getBdd()+"/"+args.get(1));
            String[] nomColonnes=args.get(3).split(",");
            f.creerTable(nomColonnes);
            return "Table "+args.get(1)+" creee";
        }
        catch (Exception e) {
            throw e;
        }
    }
    public void checkSyntaxe(Vector<String> args)throws Exception{
        if(args.size()<4){
            throw new Exception("Syntaxe error");
        }
        if(args.get(0).compareToIgnoreCase("table")!=0){
            throw new Exception("Veuillez preciser ce que vous allez creer");
        }
        if(args.get(2).compareToIgnoreCase("with")!=0){
            throw new Exception("Veuillez preciser les nom de colonnes");
        }
        
    }
}