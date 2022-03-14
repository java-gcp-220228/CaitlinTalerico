package com.revature.controller;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.ClientNotFoundException;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;
import org.postgresql.util.PSQLException;
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

    private ExceptionHandler improperRequestBody = (e, ctx) -> {
        logger.warn("User did not provided unrecognized field or no body.\n" +
                "Exception message: " + e.getMessage());
        ctx.status(400);
        if (ctx.matchedPath().equals("/clients")) {
            ctx.json("Improper request body provided. Please use the template below to form your request.\n" +
                    "{\n" +
                    "   \"firstName\" : \"\",\n" +
                    "    \"lastName\" : \"\",\n" +
                    "    \"age\" : \"(Any age between 18 and 125)\"\n" +
                    "}");
        } else if (ctx.matchedPath().equals("/clients/{clientId}/accounts/{accountId}") && ctx.method().equals("PUT")){
            System.out.println(ctx.method());
            ctx.json("Improper request body provided. Please use the template below to form your request.\n" +
                    "{\n" +
                    "   \"clientId\" : \"(optional)\",\n" +
                    "   \"accountType\" : \"(Checking, Savings, MMA, CD)\",\n" +
                    "    \"balance\" : \"(optional)\"\n" +
                    "}");
        } else {
            ctx.json("Improper request body provided. Please use the template below to form your request.\n" +
                    "{\n" +
                    "   \"accountType\" : \"(Checking, Savings, MMA, CD)\",\n" +
                    "    \"balance\" : \"(optional)\"\n" +
                    "}");
        }
    };

    private ExceptionHandler SQLExceptionerror = (e, ctx) -> {
        logger.warn("User encountered a SQLExceptionError" + e.getMessage());
        ctx.status(400);
        ctx.json(e.getMessage());
    };


    @Override
    public void mapEndpoints(Javalin app) {
        app.exception(ClientNotFoundException.class, clientNotFound);
        app.exception(AccountNotFoundException.class, accountNotFound);
        app.exception(IllegalArgumentException.class, badArgument);
        app.exception(UnrecognizedPropertyException.class, improperRequestBody);
        app.exception(NullPointerException.class, improperRequestBody);
        app.exception(MismatchedInputException.class, improperRequestBody);
        app.exception(PSQLException.class, SQLExceptionerror);
    }
}
