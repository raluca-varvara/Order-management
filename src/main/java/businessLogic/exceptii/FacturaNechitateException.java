package businessLogic.exceptii;

public class FacturaNechitateException extends Exception{
    public FacturaNechitateException() {
        super("Inainte trebuie sa va achitati factura");
    }
}
