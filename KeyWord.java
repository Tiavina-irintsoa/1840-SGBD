package keywords;
import datacontainer.*;
import java.util.Vector;
public class KeyWord {
    String intitule;
    KeyWord next;

    public KeyWord(String intitule){
        this.intitule=intitule;
    }
    public void setNext(KeyWord next){
        this.next=next;
    }
    public KeyWord next(){
        return next;
    } 
    public  String getIntitule(){
        return this.intitule;
    }
    public Relation execute(Relation res, Execution exec,Vector<String> args) throws Exception{
        return res;
    }
    public int nearest(String[] mots, Vector<KeyWord> liste, int locate, String not){
        for(int i=locate+1;i<mots.length;i++){
            System.out.println("suite"+mots[i]);
            for(int iliste=0;iliste<liste.size();iliste++){
                if(not.compareToIgnoreCase(liste.get(iliste).getIntitule())!=0){
                    if(mots[i].compareToIgnoreCase(liste.get(iliste).getIntitule())==0){
                        return i;
                    }
                }
                
            }
        }
        return mots.length;
    }
    
}
