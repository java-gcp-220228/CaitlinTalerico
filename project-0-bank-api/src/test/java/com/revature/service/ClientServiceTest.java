package com.revature.service;

import com.revature.dao.ClientDao;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientServiceTest {


    // Positive
    @Test
    public void test_getClientByID() throws SQLException, ClientNotFoundException {

        ClientDao mockDao = mock(ClientDao.class);

        when(mockDao.getClientById(eq(5))).thenReturn(
                new Client(5, "Test", "McTest", 24)
        );

        ClientService clientService = new ClientService(mockDao);

        Client actualClient = clientService.getClientByID("5");

        Client expectedClient = new Client(5, "Test", "McTest", 24);

        Assertions.assertEquals(expectedClient, actualClient);

    }


    // Negative
    @Test
    public void test_getClientById_IllegalArgument() throws ClientNotFoundException, SQLException {
        ClientDao mockdao = mock(ClientDao.class);

        ClientService clientService = new ClientService(mockdao);

        try {
            clientService.getClientByID("abc");
        } catch (IllegalArgumentException e) {
            String expectedMessage = "A valid number was not given for the client ID. \nInput: abc";
            String actualMessage = e.getMessage();

            Assertions.assertEquals(expectedMessage, actualMessage);
        }
    }

    // Negative
    @Test
    public void test_getClientByNonExistentId() throws SQLException {
        ClientDao mockDao = mock(ClientDao.class);

        ClientService clientService = new ClientService(mockDao);

        Assertions.assertThrows(ClientNotFoundException.class, () ->{
            clientService.getClientByID("10");
        });
    }

    // Positive
    @Test
    public void test_getAllClients() throws SQLException {
        ClientDao mockedObject = mock(ClientDao.class);
        List<Client> mockClients = new ArrayList<>();

        mockClients.add(new Client(1, "Peter", "Parker", 21));
        mockClients.add(new Client(2, "Bruce", "Banner", 43));
        mockClients.add(new Client(3, "Tony", "Stark", 46));
        mockClients.add(new Client(4, "Reed", "Richards", 36));

        when(mockedObject.getAllClients()).thenReturn(mockClients);

        ClientService clientService = new ClientService(mockedObject);

        List<Client> actualClients = clientService.getAllClients();

        // Assert that the expected clients equals the mocked dao
        Assertions.assertEquals(mockClients, actualClients);

    }


    // Positive
    @Test
    public void test_createClient() throws SQLException {
        ClientDao mockedObject = mock(ClientDao.class);
        Client mockClient = new Client(7, "Free", "Willy", 67);

        when(mockedObject.addClient(mockClient)).thenReturn(mockClient);

        ClientService clientService = new ClientService(mockedObject);

        Client actualClient = clientService.addClient(mockClient);


        Assertions.assertEquals(mockClient, actualClient);

    }

    // Negative
    @Test
    public void test_createClient_InvalidFirstName() throws SQLException {
        ClientDao mockedObject = mock(ClientDao.class);

        Client mockClient = new Client(1, "Abe4324&", "Exists", 24);

        when(mockedObject.addClient(mockClient)).thenReturn(mockClient);

        ClientService clientService = new ClientService(mockedObject);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.addClient(mockClient);
        });
    }
    // Negative
    @Test
    public void test_createClient_InvalidLastName() throws SQLException {
        ClientDao mockedObject = mock(ClientDao.class);

        Client mockClient = new Client(1, "Abe", "R3ad", 24);

        when(mockedObject.addClient(mockClient)).thenReturn(mockClient);

        ClientService clientService = new ClientService(mockedObject);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.addClient(mockClient);
        });
    }

    // Negative
    @Test
    public void test_createClient_InvalidAge() throws SQLException {
        ClientDao mockedObject = mock(ClientDao.class);

        Client mockClient = new Client(1, "Abe", "Exists", 17);

        when(mockedObject.addClient(mockClient)).thenReturn(mockClient);

        ClientService clientService = new ClientService(mockedObject);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            clientService.addClient(mockClient);
        });
    }

    // Positive
    @Test
    public void test_deleteClient() throws SQLException {
        ClientDao mockedObject = mock(ClientDao.class);

        when(mockedObject.deleteClientById(eq(1))).thenReturn(true);

        ClientService clientService = new ClientService(mockedObject);
        Assertions.assertTrue(clientService.deleteClient("1"));

    }

    // Negative
    @Test
    public void test_deleteClient_NotDeleted() throws SQLException {
        ClientDao mockedObject = mock(ClientDao.class);

        when(mockedObject.deleteClientById(eq(1))).thenReturn(false);

        ClientService clientService = new ClientService(mockedObject);
        Assertions.assertFalse(clientService.deleteClient("1"));

    }

    // Negative
    @Test
    public void test_deleteClient_Invalid() throws SQLException {
        ClientDao mockedObject = mock(ClientDao.class);

        when(mockedObject.deleteClientById(eq(1))).thenReturn(true);

        ClientService clientService = new ClientService(mockedObject);
        Assertions.assertThrows(IllegalArgumentException.class, () -> { clientService.deleteClient("abc");
        });
    }
}
