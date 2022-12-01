package keywords;
import process.*;
import keywords.KeyWord;
import keywords.Select;
import datacontainer.*;
import java.sql.Timestamp;
import java.util.Vector;
public class Now extends KeyWord{
    public Now(){
        super("now()");
        super.setNext(new Select());
    }
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        long millis=System.currentTimeMillis();
        Timestamp now=new Timestamp(millis);
        return now.toString();
    }
}
