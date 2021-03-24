package service;

import database.DBConnectionFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import service.client.ClientService;
import service.client.ClientServiceIMP;

import java.sql.Connection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class ClientServiceTest {

    private static ClientService clientService;
    private static ClientRepository clientRepository;

    @BeforeClass
    public static void setup() {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
        clientService = new ClientServiceIMP(clientRepository);
    }

    @Before
    public void cleanUp() {
        clientRepository.removeAll();
    }

    @Test
    public void create() {
        assertTrue(clientService.create("test", "Cluj-Napoca", "34234342").hasErrors());
    }

    @Test
    public void findAll() throws Exception {
        clientService.create("test","Romania", "112233");
        assertEquals(1, clientService.findAll().size());
    }
}