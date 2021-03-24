package service.client;

import model.Client;
import model.builder.ClientBuilder;
import model.validation.ClientValidator;
import model.validation.Notification;
import model.validation.Validator;
import repository.client.ClientRepository;

import java.util.List;

public class ClientServiceIMP implements ClientService {

    private ClientRepository clientRepository;

    public ClientServiceIMP(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean update(int id, String name, String address, String CNP) {
        Client c = new ClientBuilder()
                .setId(id)
                .setName(name)
                .setAddress(address)
                .setCNP(CNP)
                .build();

        return clientRepository.update(c);
    }

    @Override
    public Notification<Boolean> create(String name,  String address, String CNP) {
        Client c = new ClientBuilder()
                .setName(name)
                .setAddress(address)
                .setCNP(CNP)
                .build();

        ClientValidator clientValidator =  new ClientValidator(c);
        boolean clientValid = clientValidator.validate();
        Notification<Boolean> clientNotification = new Notification<>();

        if(!clientValid) {
            clientValidator.getErrors().forEach(clientNotification::addError);
            clientNotification.setResult(Boolean.FALSE);
        }
        else
            clientNotification.setResult(clientRepository.create(c));

        return clientNotification;
    }

    @Override
    public boolean remove(int id) {
        return clientRepository.remove(id);
    }

    @Override
    public Notification<Client> findById(int id) {
        return clientRepository.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public void removeAll() {
        clientRepository.removeAll();
    }
}