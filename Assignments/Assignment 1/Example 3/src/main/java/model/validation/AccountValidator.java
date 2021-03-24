package model.validation;

import model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountValidator  {

    private final Account account;
    private final List<String> errors;

    public AccountValidator(Account account) {
        this.account = account;
        errors = new ArrayList<>();
    }

    private void validateType(String type) {
        if(!type.equals("Credit") && !type.equals("Debit"))
            errors.add("Account must be of type 'Credit' or 'Debit'");
    }

    private void validateAmount(int balance) {
        if(balance < 0)
            errors.add("Your account needs to have a positive balance at all times");
    }

    public boolean validate() {

        validateType(account.getType());
        validateAmount(account.getBalance());
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return errors;
    }
}