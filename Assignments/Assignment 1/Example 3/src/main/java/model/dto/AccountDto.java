package model.dto;

import java.util.Date;

public class AccountDto {


    private int id;
    private String type;
    private int balance;
    private Date date;
    private int clientId;


    public AccountDto(int id, String type, int balance, Date date, int clientId) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.date = date;
        this.clientId = clientId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
