
package service.client;

import model.Client;
import model.dto.AccountDto;
import model.dto.ClientDto;
import model.validation.Notification;

import java.util.List;

public interface ClientService {
    boolean update(int id, String name, String address, String CNP);

    boolean update2(ClientDto clientDto);

    Notification<Boolean> create(String name,  String address, String CNP);

    Notification<Boolean> create2(ClientDto clientDto);

    boolean remove(int id);

    Notification<Client> findById(int id);

    List<Client> findAll();

    void removeAll();
}