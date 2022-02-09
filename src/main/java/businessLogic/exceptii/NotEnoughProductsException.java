package businessLogic.exceptii;

public class NotEnoughProductsException extends Exception{
    public NotEnoughProductsException() {
        super("Nu sunt destule produse pentru a se efectua comanda");
    }
}
