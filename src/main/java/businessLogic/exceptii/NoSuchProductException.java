package businessLogic.exceptii;

public class NoSuchProductException extends Exception{
    public NoSuchProductException() {
        super("Nu exista acest produs");
    }
}
