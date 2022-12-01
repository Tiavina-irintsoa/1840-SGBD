package keywords;
import java.util.Vector;
import datacontainer.Execution;
import datacontainer.Relation;
public class Divide extends KeyWord{
    public Divide(){
        super("divide");
        super.setNext(new Now());
    }

    //select rel1 divide rel2 col1 by col2
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        try {
            checkSyntaxe(args,exec, res);
            String colonne1=args.get(1);
            String colonne2=args.get(3);
            String nombdd=args.get(0);
            Relation r=((Relation) res).division(exec.getRelation(exec.contains(nombdd)), colonne1, colonne2);
            return (Relation) res;
        } catch (Exception e) {
            throw e;
        }
    }
    public void checkSyntaxe(Vector<String> args, Execution exec, Object res) throws Exception{
        int relation=exec.contains(args.get(0));
        if(relation==-1){
            throw new Exception("Relation inexistante");
        }
        if(exec.getRelation(relation).isColonne(args.get(1))==false){
            throw new Exception("Cette colonne n'existe pas");
        }
        if(exec.getRelation(relation).isColonne(args.get(3))==false){
            throw new Exception("Cette colonne n'existe pas");
        }
        if(args.get(2).compareToIgnoreCase("by")==0){
            throw new Exception("syntaxe invalide");
        }
    }
} 
