package keywords;
import process.*;
import datacontainer.*;
import java.util.Vector;
public class Use extends KeyWord{
    public Use(){
        super("use");
        super.setNext(new From());
    }
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        try {
            exec.setUsed(args.get(0));
            return "database changed";
        } catch (Exception e) {
            throw e;
        }
    }
}
