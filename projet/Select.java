package keywords;
import process.*;
import java.util.Vector;

import datacontainer.Relation;
public class Select extends KeyWord{
    public Select(){
        super("select");
        super.setNext(new Delete());
    }
    public String[] getCols(Vector<String> args){
        String[] cols=args.get(0).split(",");
        for(int i=0;i<cols.length;i++){
            cols[i]=cols[i].trim();
        }
        return cols;
    }
    public Relation execute(Object res, Execution exec,Vector<String> args) throws Exception{
        
        if(args.get(0).compareToIgnoreCase("*")!=0){
            String[] cols=getCols(args);
            checkSyntaxe(cols, (Relation) res,exec);
            res=((Relation) res).projection(cols);
        }
        return (Relation) res;
    }
    public void checkSyntaxe(String[] cols, Relation res, Execution exec) throws Exception{
        if(exec.getBdd()==null){
            throw new Exception("Aucune base de donnees selectionnee");
        }
        for(int i=0;i<cols.length;i++){
            if(res.isColonne(cols[i])==false){
                throw new Exception("La colonne "+cols[i]+" n'existe pas dans la relation "+res.getNom());
            }
        }
    }
}
