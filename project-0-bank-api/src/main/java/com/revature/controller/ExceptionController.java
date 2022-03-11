package com.revature.controller;

import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.ClientNotFoundException;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionController implements Controller{

    // Set up for logger messages
    public static Logger logger = LoggerFactory.getLogger(ExceptionController.class);


    private ExceptionHandler clientNotFound = (e, ctx) -> {
        logger.warn("User attempted to retrieve a client not in the database.\n" +
                "Exception message: " + e.getMessage());
        ctx.status(404);
        ctx.json(e.getMessage());
    };
    private ExceptionHandler accountNotFound = (e, ctx) -> {
        logger.warn("User attempted to retrieve an account not in the database.\n" +
                "Exception message: " + e.getMessage());
        ctx.status(404);
        ctx.json(e.getMessage());
    };

    private ExceptionHandler badArgument = (e, ctx) -> {
        logger.warn("User input a bad argument.\n" +
                "Exception message: " + e.getMessage());
        ctx.status(400);
        ctx.json(e.getMessage());
    };

    private ExceptionHandler noDataProvidedClient = (e, ctx) -> {
        logger.warn("User did not provide proper request body.\n" +
                "Exception message: " + e.getMessage());
        ctx.status(400);

    };


    @Override
    public void mapEndpoints(Javalin app) {
        app.exception(ClientNotFoundException.class, clientNotFound);
        app.exception(AccountNotFoundException.class, accountNotFound);
        app.exception(IllegalArgumentException.class, badArgument);
    }
}
