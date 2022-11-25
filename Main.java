package run;
import java.util.Scanner;

import datacontainer.*;
public class Main {
    public static void main(String[] args) throws Exception{
        try{
            Scanner sc= new Scanner(System.in);
            while(true){
                System.out.print("Requete: ");
                String req = sc.nextLine();
                if(req.compareToIgnoreCase("bye")==0){
                    System.out.print("disconnected");
                    break;
                }
                else{
                    Execution ex=new Execution();
                    ex.execute(req);
                    // System.out.println("///relation1");
                    // System.out.println(ex.getRelation(1).getNom());
                    // ex.getRelation(1).afficher();
                    // System.out.println("///relation2");
                    // ex.getRelation(2).afficher();
                    // System.out.println(ex.getRelation(2).getNom());
                    // Relation join=ex.getRelation(1).join(ex.getRelation(2),"y");
                    // System.out.println("///join");
                    // join.afficher();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
//alaivo * @ inscription1 divise cours etu by idcours
// alaivo * @ inscription1 difference inscription2
