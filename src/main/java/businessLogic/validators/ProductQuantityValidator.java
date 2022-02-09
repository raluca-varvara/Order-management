package businessLogic.validators;

import businessLogic.exceptii.ProduseInsuficienteException;
import dataModels.Produs;

public class ProductQuantityValidator implements Validator<Produs> {
    /**
     * metoda pentru a valida cantitatea de produse
     * @param product - produsul de validat
     * @param i - cantitatea care trebuie sa fie in produs
     * @throws ProduseInsuficienteException - nu sunt destule produse
     */
    @Override
    public void validate(Produs product, int i) throws ProduseInsuficienteException {
        if(product.getCantitate()<=i)
            throw new ProduseInsuficienteException();
    }
}
