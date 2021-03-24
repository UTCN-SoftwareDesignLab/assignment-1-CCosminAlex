package model.builder;

import model.Account;

import java.sql.Date;
import java.time.LocalDate;

public class AccountBuilder {

    private Account account;

    public AccountBuilder() {
        account = new Account();
    }

    public AccountBuilder setId(int id) {
        account.setId(id);
        return this;
    }


    public AccountBuilder setType(String type) {
        account.setType(type);
        return this;
    }


    public AccountBuilder setIdClient(int idClient) {
        account.setClient_id(idClient);
        return this;
    }

    public  AccountBuilder setBalance(int balance){
        account.setBalance(balance);
        return this;
    }

    public Account build() {
        return account;
    }


    public AccountBuilder setDateOfCreation() {
        account.setCreation_date(Date.valueOf(LocalDate.now()));
        return this;
    }
}