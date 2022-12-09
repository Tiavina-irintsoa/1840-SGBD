package keywords;
import process.*;
import java.util.Vector;

import datacontainer.Relation;
public class Join extends KeyWord{
    public Join(){
        super("join");
        super.setNext(new Where());
    }
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
            checkSyntaxe(args, exec);
            String nombdd=args.get(0);
            Relation tojoin=exec.getBdd().getRelation(exec.getBdd().contains(nombdd));
            Relation r=tojoin.join(((Relation) res));
            if(args.size()>1){
                int next=1;
                while(args.get(next).compareToIgnoreCase("join")==0){
                    nombdd=args.get(next+1);
                    if(exec.getBdd().contains(nombdd)==-1){
                        throw new Exception("La relation n'existe pas");
                    }
                    tojoin=exec.getBdd().getRelation(exec.getBdd().contains(nombdd));
                    r=tojoin.join(r);
                    next=next+2;
                    if(next>=args.size()){
                        break;
                    }
                }
            }
            return r;
    }
    public void checkSyntaxe(Vector<String> args, Execution exec) throws Exception{
        if(exec.getBdd()==null){
            throw new Exception("Aucune base de donnees selectionnee");
        }
        if(exec.getBdd().contains(args.get(0))==-1){
            System.out.println("tsy hitany");
            throw new Exception("Relation inexistante");
        }
    }
} 
