package keywords;
import java.util.Vector;
import datacontainer.Execution;
import datacontainer.Relation;
public class Join extends KeyWord{
    public Join(){
        super("join");
        super.setNext(new Select());
    }
    public Relation execute(Relation res, Execution exec,Vector<String> args){
        return res;
    }
} 
