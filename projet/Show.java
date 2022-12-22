package keywords;
import process.Execution;
import java.util.ArrayList;
import java.util.Vector;
import datacontainer.*;
public class Show extends KeyWord{
    public Show() {
        super("show");
        super.setNext(new Join());
    }
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        checkSyntaxe(exec, args,(Relation) res);
        if(args.get(0).compareToIgnoreCase("databases")==0){
            ArrayList<String> resultat=exec.getDatabaseName();
            return resultat;
        }
        else if(args.get(0).compareToIgnoreCase("details")==0){
            return ((Relation) res).getListeColonnes();
        }
        ArrayList<String> resultat=exec.getBdd().getTableName();
        return resultat;
    }
    public void checkSyntaxe(Execution exec,Vector<String> args, Relation res) throws Exception{
        if (args.size()!=1){
            throw new Exception("Syntax Error");
        }
        if( args.get(0).compareToIgnoreCase("details")!=0 && args.get(0).compareToIgnoreCase("tables")!=0 && args.get(0).compareToIgnoreCase("databases")!=0){
            throw new Exception("Syntax Error");
        }
        if(args.get(0).compareToIgnoreCase("tables")==0){
            if(exec.getBdd()==null){
                throw new Exception("Aucune base de donnees selectionnees");
            }
        }
        if(args.get(0).compareToIgnoreCase("details")==0){
            if(exec.getBdd()==null){
                throw new Exception("Aucune base de donnees selectionnee");
            }
            if(res==null){
                throw new Exception("Veuillez preciser la table");
            }
        }
    }
}
