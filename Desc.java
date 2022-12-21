package keywords;
public class Desc extends KeyWord{
    public Desc(){
        super("desc");
        super.setNext(new From());
    }

}
