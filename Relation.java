package datacontainer;
import java.util.*;
public class Relation {
    Object[][] content;
    String[] nomColonnes;
    String nom;
//Constructeur
    public Relation(String[] nomCol, Object[][] cont, String nom){
        this.content=cont;
        this.nomColonnes=nomCol;
        this.nom=nom;
    }
    public String[] getNomColonnes() {
        return nomColonnes;
    }
    public ArrayList<String> getListeColonnes(){
        ArrayList<String> res=new ArrayList<String>();
        for(int i=0;i<nomColonnes.length;i++){
            res.add(nomColonnes[i]);
        }
        return res;
    }
    public Vector<Vector<String>> toVector(){
        Vector<Vector<String>> all=new Vector<Vector<String>>();
        Vector<String> nomCols=new Vector<String>();
        for(int i=0;i<nomColonnes.length;i++){
            nomCols.add(nomColonnes[i]);
        }
        int inull;
        Vector v;
        all.add(nomCols);
        for(int row=0;row<content.length;row++){
            inull=0;
            v=new Vector<String>();
            for(int col=0;col<content[row].length;col++){
                System.out.println((String) content[row][col]);
                v.add((String) content[row][col]);
                if(content[row][col]==null){
                    inull++;
                }
            }
            if(inull==nomColonnes.length-1){
                System.out.println("inull<nomColonnes.length");
                all.add(v);
            }
        }   
        return all;
    }
    public Relation update(Relation condition,String colonne,String newValue) throws Exception{
        int col=getColIndex(colonne);
        for(int i=0;i<this.content.length;i++){
            for(int j=0;j<condition.content.length;j++){
                if(Arrays.equals(content[i], condition.content[j])==true){
                    content[i][col]=newValue;
                    break;
                }
            }
        }
        return new Relation(this.nomColonnes,content,nom);
    }
    public Relation(String nomtable,String nombdd) throws Exception{
        this.nom=nomtable;
        Fichier f=new Fichier("database/"+nombdd+"/"+nomtable);
        Vector<String> filecontent=f.read("donnees");
        setContent(filecontent);

        filecontent=f.read("nomcolonnes");
        setNomColonnes(filecontent);
    }
    public void setNomColonnes(Vector<String> nomcols){
        int nbColonnes=0;
        if(nomcols.size()>0){
            nbColonnes=nomcols.get(0).split("%%").length;
        }
        this.nomColonnes=new String[nbColonnes];
        System.out.println(nbColonnes);
        String[] splited=nomcols.get(0).split("%%");
        for(int i=0;i<nbColonnes;i++){
            this.nomColonnes[i]=splited[i];
        }
    }
    public void setContent(Vector<String> filecontent){
        int nblignes=filecontent.size();
        int nbcolonnes=0;
        if(nblignes>0){
            nbcolonnes=filecontent.get(0).split("%%").length;
        }
        String[] ligne=null;
        this.content=new Object[nblignes][nbcolonnes];
        for(int iligne=0;iligne<nblignes;iligne++){
            ligne=filecontent.get(iligne).split("%%");
            for(int icolonne=0;icolonne<nbcolonnes;icolonne++){
                
                content[iligne][icolonne]=ligne[icolonne];
            }
        }
    }
    public void setNomColonnes(){
        
    }
    public String getNom() {
        return nom;
    }
    public boolean isColonne(String nom){
        for(int i=0;i<nomColonnes.length;i++){
            if(nomColonnes[i].compareToIgnoreCase(nom)==0){
                return true;
            }
        }
        return false;
    }
    public boolean isColonnes(String[] noms) throws Exception{
        for(int i=0;i<noms.length;i++){
            if(isColonne(noms[i])==false){
                throw new Exception("nom de colonne inexistante");
            }
        }
        return true;
        
    }
    //Indice du colonnes dont le nom est col
    public int getColIndex(String col){
        for(int i=0;i<nomColonnes.length;i++){
            if(col.compareToIgnoreCase(nomColonnes[i])==0){
                System.out.println(i+nomColonnes[i]);
                return i;
            }
        }
        return -1;
    }
    //Tri fusion
    public Object[][] triFusion(int col){
        Object[][] sorted=content;
        int longueur=content.length;
        if(longueur>0){
            triFusion(sorted, 0,longueur-1,col);
        }
        return sorted;
    }
    public void triFusion(Object[][] sorted,int deb,int fin,int col){
        if(deb!=fin){   //rehefa 1 element sisa ny element anatinle tableau kely
            int milieu=(fin+deb)/2;
            triFusion(sorted,deb,milieu,col);
            triFusion(sorted,milieu+1,fin,col);
            fusion(sorted,deb,milieu,fin,col);
        }
    }
    public void fusion(Object[][] sorted,int deb1, int fin1, int fin2, int col){
        int deb2=fin1+1;
        Object[][] table1=new Object[fin1-deb1+1][nomColonnes.length];
        for(int i=deb1;i<=fin1;i++){
            table1[i-deb1]=sorted[i];
        }
        int compt1=deb1;
        int compt2=deb2;

        for(int itab=deb1;itab<=fin2;itab++){
            if(compt1==deb2){
                break;
            }
            else if(compt2==(fin2+1)){
                sorted[itab]=table1[compt1-deb1];
                compt1++;
            }
            else if(String.valueOf(table1[compt1-deb1][col]).compareTo(String.valueOf(content[compt2][col]))<0){
                sorted[itab]=table1[compt1-deb1];
                compt1++;
            }
            else{
                sorted[itab]=sorted[compt2];
                compt2++;
            }
        }
    }

    //fusionner 2 objets
    public Object[] associateContent(Object[] b,int index){
        int nbcol=b.length+this.nomColonnes.length;
        Object[] temp=this.content[index];
        Object[] res=new Object[nbcol];
        int col=0;
        for(int i=0;i<nbcol;i++){
            res[i]=temp[col];
            col++;
            if(i==nomColonnes.length-1){
                temp=b;
                col=0;
            }
        }
        return res;
    }

    //fusionner nom colonnes
    public String[] associateColName(String[] acol){
        int nbCol=nomColonnes.length+acol.length;
        String[] nomCol=new String[nbCol];
        String[] temp=this.nomColonnes;
        int icol=0;
        for(int i=0;i<nbCol;i++){
            nomCol[i]=temp[icol];
            icol++;
            if(i==nomColonnes.length-1){
                temp=acol;
                icol=0;
            }
        }
        return nomCol;
    }

    //fusionner nom colonnes et enlever les doublons de colonnes
    public String[] colonnesJoin(String[] b, int bcol){
        int reslen=nomColonnes.length+b.length-1;
        String[] res=new String[reslen];
        b[bcol]=b[b.length-1];
        int icol=0;
        String[] temp=nomColonnes;

        for(int ires=0;ires<reslen;ires++){
            res[ires]=temp[icol];
            System.out.println("colonne"+res[ires]);
            icol++;
            if(ires==nomColonnes.length-1){
                temp=b;
                icol=0;
            }
        }
        return res;
    }
    //Fusionner 2 objets et enlever les doublons de colonnes
    public Object[] objectsJoin(int index, int bcol, Object[] b){
        int len=nomColonnes.length+b.length-1;
        Object[] res=new Object[len];
        b[bcol]=b[b.length-1];
        int icol=0;

        Object[] temp=content[index];
        for(int i=0;i<len;i++){
            res[i]=temp[icol];
            //System.out.println(String.valueOf(res[i]));
            icol++;
            if(i==content[index].length-1){
                temp=b;
                icol=0;
            }
        }
        return res;
        
    }
    public Relation unir(Relation autre){
        int len=content.length+autre.content.length;
        Object[][] res=new Object[len][nomColonnes.length];
        Object[][] temp=content;
        int icontent=0;
        for(int i=0;i<len;i++){
            res[i]=temp[icontent];
            icontent++;
            if(i==content.length-1){
                icontent=0;
                temp=autre.content;
            }
        }
        return new Relation(nomColonnes,res,null);

    }
    
    public Relation union(Relation b){
        int len=content.length+b.content.length;
        Object[][] rescontent=new Object[len][nomColonnes.length];
        int ires=0;
        int doublon;
        Object[][] temp=unir(b).triFusion(0);
        int i=0;
        
        while(i<temp.length){
            doublon=i+1;
            if(doublon<temp.length){
                while(Arrays.equals(temp[doublon],temp[i])){
                    if(doublon==temp.length-1){
                        break;
                    }
                    doublon++;
                }
            }
            rescontent[ires]=temp[i];
            ires++;
            i=doublon;
        }
        return new Relation(nomColonnes,rescontent,"a union b");
}
    public int[] getCommonCol(Relation b) throws Exception{
        int[] colsindex=new int[2];
        try{
            for(int i=0;i<this.nomColonnes.length;i++){
                System.out.println("colonnes"+nomColonnes[i]+" seen");
                System.out.println("yes");
                System.out.println("length"+b.nomColonnes.length);
                for(int ib=0;ib<b.nomColonnes.length;ib++){
                    System.out.println(" sy colonnes"+b.nomColonnes[ib]);
                    if(nomColonnes[i].compareToIgnoreCase(b.nomColonnes[ib])==0){
                        colsindex[0]=i;
                        colsindex[1]=ib;
                        System.out.println("nahita colonnes mitovy"+i+","+ib);
                        return colsindex;
                    }
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        throw new Exception("Les relations "+this.nom+" et "+b.nom+" n 'ont aucune colonne en commun");
    }

    //join
    public Relation join(Relation b)throws Exception{
        System.out.println("nididtra join");
        System.out.println(b.getNom()+" nom de l;autre base");
        int[] commoncol=getCommonCol(b);
        
        int acol=commoncol[0];
        int bcol=commoncol[1];

        Object[][] acontent=triFusion(acol);
        Object[][] bcontent=b.triFusion(bcol);
        int ires=0;
        String[] colnames=colonnesJoin(b.nomColonnes, bcol);
        int reslen=b.content.length*content.length;
        Object[][] rescontent=new Object[reslen][colnames.length];
        int comptb=0;
        for(int compta=0;compta<content.length;compta++){
            if(comptb<bcontent.length){ 
                while(String.valueOf(acontent[compta][acol]).compareToIgnoreCase(String.valueOf(bcontent[comptb][bcol]))==0){
                        rescontent[ires]=objectsJoin(compta, bcol, bcontent[comptb]);
                        ires++;
                        comptb++;
                        if(comptb>=bcontent.length){
                            break;
                        }
                    }
                }
            }
           
        return new Relation(colnames,rescontent,"a join b");
    }

    //intersection
    public Relation intersection(Relation b){
        Vector v=new Vector();
        if(content[0].length!=b.content[0].length){
            System.out.println("Les relations n'ont pas le meme nombre de colonnes");
            return new Relation (new String[0], new Object[0][0],null);
        }

        int col=0;
        Object[][] asort=this.triFusion(col);
        Object[][] bsort=b.triFusion(col);

        int len=0;
        int ib=0;
        for(int i=0;i<asort.length;i++){
            if(Arrays.equals(asort[i], bsort[ib])){
                v.add(asort[i]);
                ib++;
                len++;
            }
        }

        Object[][] resultcontent=new Object[len][this.content[0].length];
        for(int i=0;i<len;i++){
            resultcontent[i]=((Object[]) v.get(i));
        }
        return new Relation(nomColonnes, resultcontent,"a intersection b");
    }
    public boolean contains(Object[][] b, Object[] element){
        for(int i=0;i<b.length;i++){
            if(Arrays.equals(b[i], element)==true){
                return true;
            }
        }
        return false;

    }
    //difference
    public Relation difference(Relation b){
        Vector v=new Vector();
        if(content[0].length!=b.content[0].length){
            System.out.println(content[0].length);
            System.out.println(b.content[0].length);
            System.out.println("Les relations n'ont pas le meme nombre de colonnes");
            return new Relation (new String[0], new Object[0][0],null);
        }

        int col=0;
        Object[][] asort=this.triFusion(col);
        Object[][] bsort=b.triFusion(col);
        for(int i=0;i<asort.length;i++){
            if(contains(bsort,asort[i])==false){
                v.add(asort[i]);
                System.out.println(String.valueOf(asort[i][col])+" ito");
            }
        }
                
        Object[][] resultcontent=new Object[v.size()][this.content[0].length];
        for(int i=0;i<v.size();i++){
            resultcontent[i]=((Object[]) v.get(i));
        }
        return new Relation(nomColonnes, resultcontent,"a difference b");
    }
    public static Vector setObj(Object[] obj){
        Vector v = new Vector();
        for (int i=0;i!=obj.length;i++){
            v.add(obj[i]);
        }
        return v;
    }
    public static Vector<Vector> toVector(Object[][] obj){
        Set<Vector> v = new HashSet<Vector>();
        for ( int i = 0 ; i != obj.length ; i++ ){
            v.add( setObj(obj[i]) );
        }
        Vector<Vector> valiny = new Vector<Vector>();
        Iterator iterator = v.iterator();
        while(iterator.hasNext()){
            valiny.add(  (Vector) iterator.next() );
        }
        return valiny;
    }
    public static Object[][] toArray( Vector<Vector> v ){
        Object[][] obj = new Object[ v.size() ][ v.get(0).size() ];
        for ( int i = 0 ; i != v.size() ; i++ ){
            obj[i] = v.get(i).toArray();
        }

        return obj;
    }
    public Relation distinct(String col){
        return new Relation(nomColonnes,toArray(toVector(content)),"distinct");
    }
    // division this/r1 , ing , rec
    public Relation division(Relation r1, String colonne1, String colonne2){
        System.out.println("Relation.division()");
        String[] ingrec=new String[2];
        ingrec[0]=colonne2;
        ingrec[1]=colonne1;
        String[] ing=new String[1];
        ing[0]=colonne1;
        String[] rec=new String[1];
        rec[0]=colonne2;

        Relation r2=r1.projection(ing);
        System.out.println("R2");
        r2.afficher();
        Relation r3=projection(rec).distinct(colonne2);
        System.out.println("r3");
        r3.afficher();
        Relation r4=r3.ProduitCartesien(r2);
        System.out.println("r4");
        r4.afficher();
        Relation proj=this.projection(ingrec);
        System.out.println("r5");
        Relation r5=r4.difference(proj);
        r5.afficher();
        Relation r6=r5.projection(rec);
        System.out.println("r6");
        r6.afficher();
        Relation r7=r3.difference(r6);
        return r7;
    }
    //projection
    public Relation projection(String[] noms){
        if(noms==null){
            return this;
        }
        int[] index=new int[noms.length];
        for(int i=0;i<noms.length;i++){
            index[i]=getColIndex(noms[i]);
        }
        Object[][] resultcontent=new Object[content.length][noms.length];
        for(int icontent=0;icontent<content.length;icontent++){
            for(int icol=0;icol<index.length;icol++){
                resultcontent[icontent][icol]=content[icontent][index[icol]];
            }            
        }
        return new Relation(noms, resultcontent,"projection a");
    }

    //produit cartesien
    public Relation ProduitCartesien(Relation a){
        int nbcol=a.nomColonnes.length+this.nomColonnes.length;
        int nblignes=a.content.length*this.content.length;
        Object[][] resultcontent=new Object[nblignes][nbcol];
        String[] colname=associateColName(a.nomColonnes);

        int ires=0;
        for(int i=0;i<this.content.length;i++){
            for(int j=0;j<a.content.length;j++){
                resultcontent[ires]=associateContent(a.content[j], i);
                System.out.println(String.valueOf(a.content[j][0]));
                ires++;
            }   
        }
        return new Relation(colname, resultcontent,"a produit cartesien b");
    }
    
    public Object[][] getContent() {
        return content;
    }
    //selection
    public Relation selection(String nomCol, String seq, String compare){
        System.out.println("niditra");
        int icol=getColIndex(nomCol);
        Object[][] resultcontent=new Object[content.length][nomColonnes.length];
        int ires=0;
        for(int i=0;i<content.length;i++){
            if(compare.compareToIgnoreCase("like")==0){
                if(String.valueOf(content[i][icol]).contains(seq)){
                    resultcontent[ires]=content[i];
                    ires++;
                }
            }
            else{
                if(String.valueOf(content[i][icol]).compareTo(seq)==0){
                    resultcontent[ires]=content[i];
                    ires++;
                }
            }
        }
        return new Relation(nomColonnes,resultcontent,this.nom);
    }

    //afficher
    public void afficher(){
        for(int i=0;i<content.length;i++){
            System.out.println("object no"+i+" | ");
            for(int j=0;j<content[0].length;j++){
                System.out.println("      "+nomColonnes[j]+": "+String.valueOf(content[i][j]));
            }
        }
    }


}