package keywords;
import java.util.Vector;
import process.*;
import datacontainer.*;
public class Update extends KeyWord{
    public Update(){
        super("update");
        super.setNext(new Divide());
    }
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        checkSyntaxe(args, (Relation) res);
        Relation condition=(Relation) res;
        int index=exec.getBdd().contains(condition.getNom());
        Relation original=exec.getBdd().getRelation(index);
        Relation nouvelle=original.update(condition, args.get(1), args.get(3));
        Fichier f=new Fichier("database/"+exec.getBdd().getNom()+"/"+condition.getNom()+"/donnees");
        f.rewrite(nouvelle.getContent());
        return "Mis a jour";
    }
    public void checkSyntaxe(Vector<String> args,Relation res) throws Exception{
        if(args.size()!=4){
            throw new Exception("Syntax Error");
        }
        if(args.get(0).compareToIgnoreCase("set")!=0){
            throw new Exception("Syntax error");
        }
        if(res.isColonne(args.get(1))==false){
            throw new Exception("Colonne introuvable");
        }
        if(args.get(2).compareToIgnoreCase("=")!=0){
            throw new Exception("Syntaxe error");
        }
    }
}
