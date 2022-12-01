package keywords;
import datacontainer.*;
import process.*;
import java.util.Vector;
public class Drop extends KeyWord{
    public Drop(){
        super("drop");
        super.setNext(new Insert());
    }
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        checkSyntaxe(args,exec);
        if(args.get(0).compareToIgnoreCase("table")==0){
            Fichier f=new Fichier("database/"+exec.getBdd().getNom()+"/"+args.get(1));
            f.delete();
            return "table supprimee";
        }
        Fichier f=new Fichier("database/"+args.get(1));
        f.delete();
        return "base de donnees supprimees";
    }
    public void checkSyntaxe(Vector<String> args, Execution exec) throws Exception{
        if(args.size()!=2){
            throw new Exception("Syntaxe error");
        }
        if(args.get(0).compareToIgnoreCase("table")==0 && args.get(0).compareToIgnoreCase("database")==0){
            throw new Exception("Syntaxe error");
        }
        if (args.get(0).compareToIgnoreCase("database")==0) {
            if(exec.getBdd()!=null){
                if(args.get(1).compareToIgnoreCase(exec.getBdd().getNom())==0){
                    throw new Exception("Vous etes encore connecte a cette base de donnees");
                }
            }
        }
    }
}
