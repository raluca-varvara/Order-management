package dao.exceptions;

public class DuplicateEntryException extends Exception{
    public DuplicateEntryException() {
        super("ID-ul deja exista");
    }
}
