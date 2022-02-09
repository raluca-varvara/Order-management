package businessLogic.exceptii;

public class NoSuchIdException extends Exception{
    public NoSuchIdException() {
        super("Nu exista acest Id");
    }
}
