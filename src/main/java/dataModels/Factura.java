package dataModels;

import java.util.ArrayList;
import java.util.List;

/**
 * clasa care defineste o factura
 */
public class Factura {
    private int inProcesare = 0;
    private int idClient;
    private int pretTotal = 0;
    private List<Produs> produse = new ArrayList<Produs>();

    public int getInProcesare() {
        return inProcesare;
    }

    public void setInProcesare(int inProcesare) {
        this.inProcesare = inProcesare;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public List<Produs> getProduse() {
        return produse;
    }

    public int getPretTotal() {
        return pretTotal;
    }

    public void setPretTotal(int pretTotal) {
        this.pretTotal = pretTotal;
    }
}
