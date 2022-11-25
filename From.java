package keywords;
import java.util.Vector;

import datacontainer.Execution;
import datacontainer.Relation;

public class From extends KeyWord{
    public From(){
        super("from");
        super.setNext(new Where());
    }
    public Relation execute(Object res, Execution exec,Vector<String> args) throws Exception{
        try{
            checkSyntaxe(exec, args);
            res = exec.getRelation(exec.contains(args.get(0)));
            return (Relation) res;
        }        
        catch(Exception e){
            System.out.println("nahita exeption");
            e.printStackTrace();
            throw e;
        }
    }
    public void checkSyntaxe(Execution exec,Vector<String> args) throws Exception{
        if(args.size()==0){
            throw new Exception("Nom de table absente");
        }
        if(exec.contains(args.get(0))==-1){
            System.out.println("nomtable"+args.get(0));
            throw new Exception("Table inexistante");
        }
    }

}