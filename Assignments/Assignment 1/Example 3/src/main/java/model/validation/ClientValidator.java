package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {

    private final Client client;
    private final List<String> errors;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate() {
        validateCNP(client.getCNP());

        return errors.isEmpty();
    }

    private void validateCNP(String CNP) {
        if(!CNP.matches("[0-9]+"))
            errors.add("CNP should contain only digits");
        if(CNP.length()!=9)
            errors.add("CNP should be 9 digits long");
    }

    public List<String> getErrors() {
        return errors;
    }
}