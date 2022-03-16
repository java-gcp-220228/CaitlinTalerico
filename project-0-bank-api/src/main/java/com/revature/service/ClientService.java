package com.revature.service;

import com.revature.dao.ClientDao;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class ClientService {

    public static Logger logger = LoggerFactory.getLogger(ClientService.class);

    private ClientDao clientDao;

    public ClientService() {
        this.clientDao = new ClientDao();
    }

    // Mock dao set-up for testing
    public ClientService(ClientDao mockDao) {
        this.clientDao = mockDao;
    }
    public Client getClientByID(String id) throws SQLException, ClientNotFoundException {
        try {
            int clientId = Integer.parseInt(id);

            Client c = clientDao.getClientById(clientId);

            if (c == null) {
                throw new ClientNotFoundException("A client with the client ID " + clientId + " does not exist.");
            }

            return c;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A valid number was not given for the client ID. \nInput: " + id);
        }
    }

    public List<Client> getAllClients() throws SQLException {

        return clientDao.getAllClients();
    }

    public Client addClient(Client client) throws SQLException {
        validateClientInformation(client);

        Client addedClient = clientDao.addClient(client);
        return addedClient;

    }

    public Client updateClient(String id, Client client) throws SQLException, ClientNotFoundException {
        validateClientInformation(client);
        try {
            int clientId = Integer.parseInt(id);
            client.setId(clientId);
            if (clientDao.getClientById(clientId) == null) {
                throw new ClientNotFoundException("Client with id " + id + " does not exist. Unable to edit this client.");
            }
            return clientDao.updateClient(client);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("A valid number was not given for the client ID. \nInput: " + id);
        }
    }

    public boolean deleteClient(String id) throws SQLException, ClientNotFoundException {
        try {
            int clientId = Integer.parseInt(id);

            if (clientDao.getClientById(clientId) == null) {
                throw new ClientNotFoundException("Client with id " + id + " does not exits. Unable to delete client.");
            }

            return clientDao.deleteClientById(clientId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A valid number was not given for the client ID. \nInput: " + id);
        }

    }

    public void validateClientInformation(Client client) {
        client.setFirstName(client.getFirstName().trim());
        client.setLastName(client.getLastName().trim());

        if (!client.getFirstName().matches("[a-zA-Z]+('[a-zA-Z])?[a-zA-Z]*")) {
            throw new IllegalArgumentException("First name must only have alphabetical characters.\nInput: " + client.getFirstName());
        }

        if (!client.getLastName().matches("[a-zA-Z]+('[a-zA-Z])?[a-zA-Z]*")) {
            throw new IllegalArgumentException("Last name must only have alphabetical characters.\nInput: " + client.getLastName());
        }

        if (client.getAge() < 18) {
            throw new IllegalArgumentException("Unable to assign minors an account at this time. Account holder must be 18 or older. \nInput: " + client.getAge());
        }

    }

}
