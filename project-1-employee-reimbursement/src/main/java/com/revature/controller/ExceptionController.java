package com.revature.controller;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.api.client.http.HttpResponseException;
import com.revature.exception.InvalidFileTypeException;
import io.javalin.Javalin;
import io.javalin.http.ExceptionHandler;

import io.javalin.http.UnauthorizedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.SizeLimitExceededException;
import javax.security.auth.login.FailedLoginException;
import java.io.IOException;

public class ExceptionController implements Controller{

    Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    private ExceptionHandler<FailedLoginException> failedLogin = ((exception, ctx) -> {
        logger.warn(exception.getLocalizedMessage());
        ctx.status(400);
        ctx.json(exception.getMessage());
    });

    private ExceptionHandler<IllegalArgumentException> invalidIntegerParam = ((exception, ctx) -> {
        logger.warn(exception.getLocalizedMessage());
        ctx.status(400);
        ctx.json(exception.getMessage());
    });

    private ExceptionHandler<UnauthorizedResponse> unauthorizedUser = ((exception, ctx) -> {
        logger.warn(exception.getLocalizedMessage());
        ctx.status(401);
        ctx.json(exception.getMessage());
    });

    private ExceptionHandler<MismatchedInputException> invalidOrIncompleteBodySent = ((exception, ctx) -> {
        logger.warn(exception.getLocalizedMessage());
       ctx.status(418);
       ctx.json(exception.getMessage());
    });

    private ExceptionHandler<SizeLimitExceededException> fileSizeTooLarge = ((exception, ctx) -> {
        logger.warn(exception.getMessage());
        ctx.status(413);
        ctx.json(exception.getMessage());
    });

    private ExceptionHandler<InvalidFileTypeException> invalidFileType = ((exception, ctx) -> {
        logger.warn(exception.getLocalizedMessage());
        ctx.status(400);
        ctx.json(exception.getLocalizedMessage());
    });


    @Override
    public void mapEndpoints(Javalin app) {
        app.exception(FailedLoginException.class, failedLogin);
        app.exception(IllegalArgumentException.class, invalidIntegerParam);
        app.exception(UnauthorizedResponse.class, unauthorizedUser);
        app.exception(MismatchedInputException.class, invalidOrIncompleteBodySent);
        app.exception(SizeLimitExceededException.class, fileSizeTooLarge);
        app.exception(InvalidFileTypeException.class, invalidFileType);
    }
}
