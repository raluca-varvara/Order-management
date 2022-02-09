package dataModels;

/**
 * clasa care defineste un produs
 */
public class Produs {
    private int id;
    private String nume;
    private int pret;
    private int cantitate;

    public Produs() {
    }

    public Produs(int id, String nume, int pret, int cantitate) {
        this.id = id;
        this.pret = pret;
        this.nume = nume;
        this.cantitate = cantitate;
    }

    public int getCantitate() {
        return cantitate;
    }

    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public int getPret() {
        return pret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Produs{ " + nume +", pret=" + pret + ", cantitate="+cantitate+ "}\n";
    }
}
