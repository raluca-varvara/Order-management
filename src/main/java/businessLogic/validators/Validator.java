package businessLogic.validators;

public interface Validator<T> {
    public void validate(T t, int i) throws Exception;
}
