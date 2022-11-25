package keywords;
import java.util.Vector;
import datacontainer.Execution;
import datacontainer.Relation;
public class Where extends KeyWord{
    public Where(){
        super("where");
        super.setNext(new Join());
    }
    public Relation condition(String selector,Relation res, String seq, String colonne){
        return res.selection(colonne, seq, selector);
    }
    public Relation execute(Relation res, Execution exec,Vector<String> args) throws Exception{
        checkSyntaxe(args);
        Relation r=condition(args.get(1), res, args.get(2), args.get(0));
        if(args.size()>=4){
            int next=3;
            while(args.get(next).compareToIgnoreCase("or")==0 || args.get(next).compareToIgnoreCase("and")==0){
                Relation r1=condition(args.get(next+2),res, args.get(next+3), args.get(next+1));
                System.out.println("R1");
                r1.afficher();

                System.out.println("res");
                res.afficher();
                System.out.println("next : "+ args.get(next));
                if(args.get(next).compareToIgnoreCase("or")==0){
                    r=r.union(r1);
                }
                else{
                    r=r.intersection(r1);
                }
                next=next+4;
                if(next>=args.size()){
                    break;
                }
            }
        }
        return r;
    }
    public void checkSyntaxe(Vector<String> args)throws Exception{
        if(args.size()<3){
            throw new Exception("Condition incomplete");
        }
    }
}