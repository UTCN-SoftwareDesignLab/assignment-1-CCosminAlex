package model;

import java.sql.Date;

public class Account {

    private int id;
    private String type;
    private int balance;
    private Date creation_date;
    private int client_id;

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}