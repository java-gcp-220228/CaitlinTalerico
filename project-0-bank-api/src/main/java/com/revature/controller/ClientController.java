package com.revature.controller;


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

    private final Handler getAllClients = (ctx) -> {
        List<Client> clients = clientService.getAllClients();

        ctx.status(200);
        ctx.json(clients);
    };

    private final Handler getClientById = (ctx) -> {
        String id = ctx.pathParam("clientId");
        Client client = clientService.getClientByID(id);
        ctx.status(200);
        ctx.json(client);
    };

    private final Handler addClient = (ctx) -> {
        System.out.println(ctx.body());
        Client clientToAdd = ctx.bodyAsClass(Client.class);

        Client newClient = clientService.addClient(clientToAdd);
        ctx.status(201);
        ctx.json(newClient);

    };

    private final Handler updateClient = (ctx) -> {
        Client clientToEdit = ctx.bodyAsClass(Client.class);

        Client editedClient = clientService.updateClient(ctx.pathParam("clientId"), clientToEdit);

        ctx.status(200);
        ctx.json(editedClient);
    };

    private final Handler deleteClient = (ctx) -> {
        String id = ctx.pathParam("clientId");

        ResponseBody responseBody = ResponseBody.deleteClient(clientService.deleteClient(id));
        ctx.status(200);
        ctx.json(responseBody);


    };

    private final Handler notValidRequest = (ctx) -> {
        ctx.status(405);
        ctx.result(ctx.method() + " requests are not allowed for path: " + ctx.path());
    };

    private final Handler receiveOptions = (ctx) -> {
        ctx.status(200);
        ctx.result("Allowed: GET, POST, PUT, DELETE");
    };


    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients", getAllClients);
        app.get("/clients/{clientId}", getClientById);
        app.post("/clients", addClient);
        app.put("/clients/{clientId}", updateClient);
        app.delete("/clients/{clientId}", deleteClient);
        app.put("/clients", notValidRequest);
        app.patch("/*", notValidRequest);
        app.head("/*", notValidRequest);
        app.options("/*", receiveOptions);


    }
}
