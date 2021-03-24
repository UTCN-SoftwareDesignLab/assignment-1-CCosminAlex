package service.account;

import model.Account;
import model.validation.Notification;

import java.sql.Date;
import java.util.List;

public interface AccountService {
    boolean remove(int id);

    boolean update(int id, String type, int balance, Date date, int idClient);

    Notification<Boolean> create(long cardNo, String type, int balance, Date date, int idClient);

    Account findById(int id);

    List<Account> findAll();

    void removeAll();
}