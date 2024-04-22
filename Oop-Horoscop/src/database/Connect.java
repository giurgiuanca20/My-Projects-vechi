package database;
import Magazin.RowInTable;

import java.sql.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class Connect {
    public String url;
    public String user;
    public String password;
    public String tableName;
    private int id;
    private DataNastere struct=new DataNastere("","","");
    public RowInTable l1,l2,l3,t1,t2,t3;
    public int nrLant,nrTablou;
    public Connect(String tableName) {
        this.url ="jdbc:postgresql://localhost:5432/postgres";
        this.user ="postgres";
        this.password ="*********";
        this.tableName = tableName;
    }

    public int ultimul_index(String tabelName){
        String query = "SELECT * FROM "+tabelName;
        int i=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps= connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }

    public void add_cont(String nume, String prenume, String mail, String util, String pass){
        int i=ultimul_index("cont")+1;
        String sql = "INSERT INTO cont (id_cont,users,parola) VALUES (?,?,?)";
        try (   Connection connection = DriverManager.getConnection(url, user, password);
                         PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setInt(1,i );
            ps.setString(2, util);
            ps.setString(3,pass);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        sql = "INSERT INTO persoana (id_persoana,id_cont,nume_pers,prenume_pers,mail) VALUES (?,?,?,?,?)";
        try (   Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setInt(1, i);
            ps.setInt(2, i);
            ps.setString(3, nume);
            ps.setString(4,prenume);
            ps.setString(5,mail);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("e.getMessege()");
        }
    }

    public boolean cautare_cont(String util, String par) {
        String query = "SELECT * FROM cont";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("users").equals(util) && rs.getString("parola").equals(par)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public int cautaCos(String util, String par) {
        String query = "SELECT * FROM cos";
        int i=0;
        String id_c=Integer.toString(indexInFunctieDeCont(util,par));
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_cont").equals(id_c)) {
                    i=rs.getInt("id_cos");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    public int indexInFunctieDeCont(String util, String par) {
        String query = "SELECT * FROM cont";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("users").equals(util) && rs.getString("parola").equals(par)) {
                    id=rs.getInt("id_cont");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public int indexInFunctieDeZodie(String zodie) {
        String query = "SELECT * FROM zodie";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("nume_zodie").equals(zodie)) {
                    id=rs.getInt("id_zodie");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public int indexInFunctieDeDimensiune(String dimensiune) {
        String query = "SELECT * FROM pret_lant";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("marime").equals(dimensiune)) {
                    id=rs.getInt("id_pret");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public int indexInFunctieDeCuloare(String culoare) {
        String query = "SELECT * FROM culori";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("nume_culoare").equals(culoare)) {
                    id=rs.getInt("id_culori");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
    public DataNastere dataInFunctieDeId(int t) {
        String query = "SELECT * FROM data_nastere";
        String id_c=Integer.toString(t);
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_cont").equals(id_c)) {
                    struct=new DataNastere(rs.getString("zi"),rs.getString("luna"),rs.getString("an"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return struct;
    }
    public String getDimensiune(int t) {
        String query = "SELECT * FROM pret_lant";
        String id_c=Integer.toString(t);
        String rez=" ";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_pret").equals(id_c)) {
                    rez=rs.getString("marime");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rez;
    }
    public String getCuloare(int t) {
        String query = "SELECT * FROM culori";
        String id_c=Integer.toString(t);
        String rez=" ";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_culori").equals(id_c)) {
                    rez=rs.getString("nume_culoare");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rez;
    }
    public String getZodie(String t) {
        String query = "SELECT * FROM zodie";
        String rez=" ";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_zodie").equals(t)) {
                    rez=rs.getString("nume_zodie");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rez;
    }
    public String getMess(String t) {
        String query = "SELECT * FROM lant";
        String rez=" ";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_lant").equals(t)) {
                    rez=rs.getString("mesaj");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rez;
    }
    public String getNumeTablou(String t) {
        String query = "SELECT * FROM tablou ORDER BY id_tablou";
        String rez=" ";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_tablou").equals(t)) {
                    rez=rs.getString("nume_tablou");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rez;
    }
    public int getNrBuc(String t) {
        String query = "SELECT * FROM lant ORDER BY id_lant";
        int rez=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_lant").equals(t)) {
                    rez=rs.getInt("nr_buc");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rez;
    }
    public int getPret(int t, String b) {
        String query = "SELECT * FROM pret_lant";
        String id_c=Integer.toString(t);
        int rez=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_pret").equals(id_c)) {
                    rez=rs.getInt("pret");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        rez=getNrBuc(b)*rez;
        return rez;
    }
    public void salveazaDataNastere(String util,String par,int zi,int luna,int an){
        String sql = "INSERT INTO data_nastere (zi,luna,an,id_data,id_cont) VALUES (?,?,?,?,?)";
        int i_data=ultimul_index("data_nastere")+1;
        int i_cont=indexInFunctieDeCont(util,par);
        try ( Connection connection = DriverManager.getConnection(url, user, password);
                PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setInt(1,zi);
            ps.setInt(2,luna);
            ps.setInt(3,an);
            ps.setInt(4,i_data);
            ps.setInt(5,i_cont);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addCos(String util,String par){
        String sql = "INSERT INTO cos (id_cos,id_cont) VALUES (?,?)";
        int idCos=ultimul_index("cos")+1;
        int idCont=indexInFunctieDeCont(util,par);
        try ( Connection connection = DriverManager.getConnection(url, user, password);
              PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setInt(1,idCos);
            ps.setInt(2,idCont);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addLant(int idLant,int dimensiune,int culoare,String mesaj,int idCos,int nrBuc){
        String sql = "INSERT INTO lant (id_lant,id_dimensiune_lant,id_culoare,mesaj,id_cos,nr_buc) VALUES (?,?,?,?,?,?)";
        try ( Connection connection = DriverManager.getConnection(url, user, password);
              PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setInt(1,idLant);
            ps.setInt(2,dimensiune);
            ps.setInt(3,culoare);
            ps.setString(4,mesaj);
            ps.setInt(5,idCos);
            ps.setInt(6,nrBuc);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void addTablou(int idTablou,String nume,int zodie,int culoare,int idCos){
        String sql = "INSERT INTO tablou (id_tablou,nume_tablou,id_zodie,id_culoare,id_cos) VALUES (?,?,?,?,?)";
        try ( Connection connection = DriverManager.getConnection(url, user, password);
              PreparedStatement ps = connection.prepareStatement(sql);){
            ps.setInt(1,idTablou);
            ps.setString(2,nume);
            ps.setInt(3,zodie);
            ps.setInt(4,culoare);
            ps.setInt(5,idCos);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("addTablou");
            System.out.println(e.getMessage());
        }
    }
    public void stergeDataNastere(String util,String par){
        String sql = "DELETE FROM data_nastere WHERE id_cont=?";
        int i_cont=indexInFunctieDeCont(util,par);
        try ( Connection connection = DriverManager.getConnection(url, user, password);
              PreparedStatement ps = connection.prepareStatement(sql);){

            ps.setInt(1,i_cont);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int cautareDataExistenta(String util, String par) {
        String query = "SELECT * FROM cont";
        int i=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("users").equals(util) && rs.getString("parola").equals(par)) {
                    i=rs.getInt("id_cont");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    public int cautaLantInCos(int i_cos) {
        String query = "SELECT * FROM cos";
        String id_cos=Integer.toString(i_cos);
        int i=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_cos").equals(id_cos)) {
                    if(rs.getString("id_lant1")==null)
                        i= 1;
                    else if (rs.getString("id_lant2")==null) {
                        i= 2;
                    }else if (rs.getString("id_lant3")==null) {
                        i= 3;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return i;
    }
    public int cautaTablouInCos(int i_cos) {
        String query = "SELECT * FROM cos";
        String id_cos=Integer.toString(i_cos);
        int i=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_cos").equals(id_cos)) {
                    if(rs.getString("id_tablou1")==null)
                        i= 1;
                    else if (rs.getString("id_tablou2")==null) {
                        i= 2;
                    }else if (rs.getString("id_tablou3")==null) {
                        i= 3;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return i;
    }
    public void addLantCos(int idLant,int idCos){
        String sql1 = "UPDATE cos SET id_lant1 = ? WHERE id_cos=?";
        String sql2 = "UPDATE cos SET id_lant2 = ? WHERE id_cos=?";
        String sql3 = "UPDATE cos SET id_lant3 = ? WHERE id_cos=?";
        String sql=null;
        int k=cautaLantInCos(idCos);
        if(k==1)
            sql=sql1;
        else if (k==2) {
            sql=sql2;
        } else if (k==3) {
            sql=sql3;
        }
        if(k!=0) {
            try ( Connection connection = DriverManager.getConnection(url, user, password);
                  PreparedStatement ps = connection.prepareStatement(sql);){
                ps.setInt(1,idLant);
                ps.setInt(2,idCos);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("add lant cos");
                System.out.println(e.getMessage());
            }
        }
        else showMessageDialog(null,"Nu se mai pot adauga lanturi!");
    }
    public void addTablouCos(int idTablou,int idCos){

        String sql1 = "UPDATE cos SET id_tablou1 = ? WHERE id_cos=?";
        String sql2 = "UPDATE cos SET id_tablou2 = ? WHERE id_cos=?";
        String sql3 = "UPDATE cos SET id_tablou3 = ? WHERE id_cos=?";
        String sql=null;
        int k=cautaTablouInCos(idCos);
        if(k==1)
            sql=sql1;
        else if (k==2) {
            sql=sql2;
        } else if (k==3) {
            sql=sql3;
        }
        if(k!=0) {
            try ( Connection connection = DriverManager.getConnection(url, user, password);
                  PreparedStatement ps = connection.prepareStatement(sql);){
                ps.setInt(1,idTablou);
                ps.setInt(2,idCos);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("add tablou cos");
                System.out.println(e.getMessage());
            }
        }
        else showMessageDialog(null,"Nu se mai pot adauga tablouri!");
    }
    public void adaugaInTabelLant(int indexLant1,int indexLant2,int indexLant3) {
        String query = "SELECT * FROM lant";
        String idL1=Integer.toString(indexLant1);
        String idL2=Integer.toString(indexLant2);
        String idL3=Integer.toString(indexLant3);
        int i=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_lant").equals(idL1))
                    l1 = new RowInTable("lant", getDimensiune(rs.getInt("id_dimensiune_lant")), getCuloare(rs.getInt("id_culoare")), getMess(idL1), getNrBuc(idL1),getPret(rs.getInt("id_dimensiune_lant"),idL1));  ///getPret(rs.getInt("pret"),idL3)
                if (rs.getString("id_lant").equals(idL2))
                    l2 = new RowInTable("lant", getDimensiune(rs.getInt("id_dimensiune_lant")), getCuloare(rs.getInt("id_culoare")), getMess(idL2), getNrBuc(idL2), getPret(rs.getInt("id_dimensiune_lant"),idL2));
                if (rs.getString("id_lant").equals(idL3))
                    l3 = new RowInTable("lant", getDimensiune(rs.getInt("id_dimensiune_lant")), getCuloare(rs.getInt("id_culoare")), getMess(idL3), getNrBuc(idL3), getPret(rs.getInt("id_dimensiune_lant"),idL3));
            }
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void adaugaInTabelTablou(int indexTablou1,int indexTablou2,int indexTablou3) {
        String query = "SELECT * FROM tablou";
        String idT1=Integer.toString(indexTablou1);
        String idT2=Integer.toString(indexTablou2);
        String idT3=Integer.toString(indexTablou3);
        int i=0;
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_tablou").equals(idT1))
                    t1 = new RowInTable("tablou", getNumeTablou(rs.getString("id_tablou")), getCuloare(rs.getInt("id_culoare")), getZodie(idT1), 1, 300);
                if (rs.getString("id_tablou").equals(idT2))
                    t2 = new RowInTable("tablou", getNumeTablou(rs.getString("id_tablou")), getCuloare(rs.getInt("id_culoare")), getZodie(idT2), 1, 300);
                if (rs.getString("id_tablou").equals(idT3))
                    t3 = new RowInTable("tablou", getNumeTablou(rs.getString("id_tablou")), getCuloare(rs.getInt("id_culoare")), getZodie(idT3), 1, 300);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void returneazaCosul(String util,String par) {
        int index=0;
        String query = "SELECT * FROM cos";
        int idCos=cautaCos(util,par);
        String k=Integer.toString(indexInFunctieDeCont(util,par));
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_cont").equals(k)) {
                    nrLant=cautaLantInCos(idCos);
                    nrTablou=cautaTablouInCos(idCos);

                    switch (nrLant){
                        case 2:
                            adaugaInTabelLant(rs.getInt("id_lant1"),0,0);
                            break;
                        case 3:
                            adaugaInTabelLant(rs.getInt("id_lant1"),rs.getInt("id_lant2"),0);
                            break;
                        case 0:
                            adaugaInTabelLant(rs.getInt("id_lant1"),rs.getInt("id_lant2"),rs.getInt("id_lant3"));
                            break;
                    }
                    switch (nrTablou){
                        case 2:
                            adaugaInTabelTablou(rs.getInt("id_tablou1"),0,0);
                            break;
                        case 3:
                            adaugaInTabelTablou(rs.getInt("id_tablou1"),rs.getInt("id_tablou2"),0);
                            break;
                        case 0:
                            adaugaInTabelTablou(rs.getInt("id_tablou1"),rs.getInt("id_tablou2"),rs.getInt("id_tablou3"));
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public String numeInFuncteDeCont(String util,String par) {
        String query = "SELECT * FROM persoana";
        String numeComp=null;
        String k=Integer.toString(indexInFunctieDeCont(util,par));
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement ps = connection.createStatement();
        ) {
            ResultSet rs = ps.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("id_cont").equals(k)) {
                    numeComp=rs.getString("nume_pers")+" "+rs.getString("prenume_pers");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return numeComp;
    }

}
