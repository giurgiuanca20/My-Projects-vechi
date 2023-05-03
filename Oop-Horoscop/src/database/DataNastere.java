package database;

public class DataNastere {
    private String zi;
    private String luna;
    private String an;
    public DataNastere(String zi, String luna, String an) {
        this.zi = zi;
        this.luna = luna;
        this.an = an;
    }
    public String getZi() {
        return zi;
    }
    public String getLuna() {
        return luna;
    }
    public String getAn() {
        return an;
    }
}
