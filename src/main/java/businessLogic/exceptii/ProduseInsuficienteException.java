package businessLogic.exceptii;

public class ProduseInsuficienteException extends Exception{
    public ProduseInsuficienteException() {
        super("Nu sunt destule produse");
    }
}
