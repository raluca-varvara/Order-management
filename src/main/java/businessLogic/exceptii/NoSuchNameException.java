package businessLogic.exceptii;

public class NoSuchNameException extends Exception{
    public NoSuchNameException() {
        super("Nu existe acest nume de utilizator");
    }
}
