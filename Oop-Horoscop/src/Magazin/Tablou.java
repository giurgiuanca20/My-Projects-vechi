package Magazin;

import database.Connect;

public class Tablou {
    int idTablou;
    String numeTablou;
    String zodia;
    String culoare;
    int idCos;
    Connect q=new Connect("lant");

    public Tablou(String numeTablou, String zodia, String culoare) {
        this.numeTablou = numeTablou;
        this.zodia = zodia;
        this.culoare = culoare;
    }

    public int adaugaTablou(int k){
        idTablou=q.ultimul_index("tablou")+1;
        idCos=k;
        int zod=q.indexInFunctieDeZodie(zodia);
        int cul=q.indexInFunctieDeCuloare(culoare);
        q.addTablou(idTablou,numeTablou,zod,cul,idCos);
        return idTablou;
    }
}
