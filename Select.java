package keywords;
import java.util.Vector;
import datacontainer.Execution;
import datacontainer.Relation;
public class Select extends KeyWord{
    public Select(){
        super("select");
        super.setNext(null);
    }
    public String[] getCols(Vector<String> args){
        String[] cols=args.get(0).split(",");
        for(int i=0;i<cols.length;i++){
            cols[i]=cols[i].trim();
        }
        return cols;
    }
    public Relation execute(Relation res, Execution exec,Vector<String> args) throws Exception{
        if(args.get(0).compareToIgnoreCase("*")!=0){
            String[] cols=getCols(args);
            checkSyntaxe(cols, res);
            res=res.projection(cols);
        }
        res.afficher();
        return res;
    }
    public void checkSyntaxe(String[] cols, Relation res) throws Exception{
        for(int i=0;i<cols.length;i++){
            if(res.isColonne(cols[i])==false){
                throw new Exception("La colonne "+cols[i]+" n'existe pas dans la relation "+res.getNom());
            }
        }
    }
}
