package com.revature.controller;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.model.Client;
import com.revature.response.ResponseBody;
import com.revature.service.ClientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import org.postgresql.util.PSQLException;


import java.util.List;

public class ClientController implements Controller{

    private ClientService clientService;

    public ClientController() {
        this.clientService = new ClientService();
    }

    private Handler getAllClients = (ctx) -> {
        List<Client> clients = clientService.getAllClients();

        ctx.status(200);
        ctx.json(clients);
    };

    private Handler getClientById = (ctx) -> {
        String id = ctx.pathParam("clientId");
        Client client = clientService.getClientByID(id);
        ctx.json(client);
    };

    private Handler addClient = (ctx) -> {
        System.out.println(ctx.body());
        try {
            Client clientToAdd = ctx.bodyAsClass(Client.class);

            Client newClient = clientService.addClient(clientToAdd);
            ctx.status(201);
            ctx.json(newClient);
        } catch (PSQLException e) {
            ctx.status(400);
            ctx.result("Invalid body sent. Body should be structure as below: \n" +
                    "{" +
                    "firstName: ''," +
                    "lastName: ''," +
                    "age: ''" +
                    "}");
        }

    };

    private Handler updateClient = (ctx) -> {
        Client clientToEdit = ctx.bodyAsClass(Client.class);

        Client editedClient = clientService.updateClient(ctx.pathParam("clientId"), clientToEdit);

        ctx.status(200);
        ctx.json(editedClient);
    };

    private Handler deleteClient = (ctx) -> {
        String id = ctx.pathParam("clientId");

        ResponseBody responseBody = ResponseBody.deleteClient(clientService.deleteClient(id));
        ctx.status(200);
        ctx.json(responseBody);


    };

    private Handler notValidMethod = (ctx) -> {
        ctx.status(405);
        ctx.result(ctx.method() + " requests are not allowed.");
    };


    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients", getAllClients);
        app.get("/clients/{clientId}", getClientById);
        app.post("/clients", addClient);
        app.put("/clients/{clientId}", updateClient);
        app.delete("/clients/{clientId}", deleteClient);
        app.patch("/*", notValidMethod);


    }
}
