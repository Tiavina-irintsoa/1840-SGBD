package keywords;
import process.*;
import java.util.Vector;

import datacontainer.Fichier;

import datacontainer.Relation;
public class Create extends KeyWord{
    public Create(){
        super("create");
        super.setNext(new Drop());
    }
    //create table ... with x,y,z
    public Object execute(Object res, Execution exec,Vector<String> args) throws Exception{
        try {
            checkSyntaxe(args,exec,(Relation) res);
            //si create table
            if(args.get(0).compareToIgnoreCase("table")==0){
                Fichier f=new Fichier("database/"+exec.getBdd().getNom()+"/"+args.get(1));
                String[] nomColonnes=args.get(3).split(",");
                f.creerTable(nomColonnes);
                return "Table "+args.get(1)+" creee";
            }
            //si database
            Fichier f=new Fichier("database/"+args.get(1));
            f.mkdir();
            return "Database cree"; 
        }
        catch (Exception e){
            throw e;
        }
    }
    public void checkSyntaxe(Vector<String> args,Execution exec, Relation res)throws Exception{
        if(args.size()<2){
            throw new Exception("Syntax error");
        }
        if(args.get(0).compareToIgnoreCase("table")!=0 && args.get(0).compareToIgnoreCase("database")!=0){
            throw new Exception("Veuillez preciser ce que vous allez creer");
        }
        if(args.get(0).compareToIgnoreCase("table")==0){
            if(exec.getBdd()==null){
                throw new Exception("Aucune base de donnee selectionnee");
            }
            if(args.size()<4){
                throw new Exception("Syntax error");
            }
            if(args.get(2).compareToIgnoreCase("with")!=0){
                throw new Exception("Veuillez preciser les nom de colonnes");
            }
            if(exec.getBdd().contains(args.get(1))!=-1){
                throw new Exception("Cette table existe deja");
            }
            String[] nomColonnes=args.get(3).split(",");
            for(int i=0;i<nomColonnes.length;i++){
                for(int other=i+1;other<nomColonnes.length;other++){
                    if(nomColonnes[i].compareToIgnoreCase(nomColonnes[other])==0){
                        throw new Exception("nom de colonnes redondant");
                    }
                }
            }
        }
        if(args.get(0).compareToIgnoreCase("database")==0){
            if(exec.isDatabase(args.get(1))!=-1){
                throw new Exception("Cette base de donnees existe deja");
            }
        }
        
    }
}