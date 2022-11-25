package keywords;
import java.util.Vector;
import datacontainer.Execution;
import datacontainer.Relation;
public class Join extends KeyWord{
    public Join(){
        super("join");
        super.setNext(new Select());
    }
    public Object execute(Object res, Execution exec,Vector<String> args){
        try {
            System.out.println("gonna join...");
            checkSyntaxe(args, exec);
            String nombdd=args.get(0);
            System.out.println("nombdd "+nombdd);
            System.out.println("exe"+ exec.getRelation(exec.contains(nombdd)).getNom());
            Relation tojoin=exec.getRelation(exec.contains(nombdd));
            Relation r=((Relation) res).join(tojoin);
            System.out.println("res:");
            r.afficher();
            if(args.size()>1){
                int next=1;
                while(args.get(next).compareToIgnoreCase("join")==0){
                    nombdd=args.get(next+1);
                    tojoin=exec.getRelation(exec.contains(nombdd));
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
        if(exec.contains(args.get(0))==-1){
            throw new Exception("Relation inexistante");
        }
    }
} 
