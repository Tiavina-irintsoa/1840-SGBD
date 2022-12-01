package keywords;
import process.*;
import java.util.Vector;

import datacontainer.Relation;
public class Join extends KeyWord{
    public Join(){
        super("join");
        super.setNext(new Divide());
    }
    public Object execute(Object res, Execution exec,Vector<String> args){
        try {
            checkSyntaxe(args, exec);
            String nombdd=args.get(0);
            Relation tojoin=exec.getBdd().getRelation(exec.getBdd().contains(nombdd));
            Relation r=((Relation) res).join(tojoin);
            if(args.size()>1){
                int next=1;
                while(args.get(next).compareToIgnoreCase("join")==0){
                    nombdd=args.get(next+1);
                    tojoin=exec.getBdd().getRelation(exec.getBdd().contains(nombdd));
                    r=r.join(tojoin);
                    next=next+2;
                    if(next>=args.size()){
                        break;
                    }
                }
            }
            return r;
        } catch (Exception e) {

        }

        return (Relation) res;
    }
    public void checkSyntaxe(Vector<String> args, Execution exec) throws Exception{
        if(exec.getBdd().contains(args.get(0))==-1){
            throw new Exception("Relation inexistante");
        }
    }
} 
