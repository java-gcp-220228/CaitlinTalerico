package com.revature.main;

import com.revature.controller.AccountController;
import com.revature.controller.ClientController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Driver {

    // Logger class
    public static Logger logger = LoggerFactory.getLogger("com.revature");
    public static void main(String[] args) {
        Javalin app = Javalin.create();

        app.before((ctx -> {
            logger.info(ctx.method() + " request received for " + ctx.path());
        }));
        mapControllers(app, new ClientController(), new AccountController(), new ExceptionController());
        app.start();
    }

    private static void mapControllers(Javalin app, Controller...controllers) {
        for (Controller c : controllers) {
            c.mapEndpoints(app);
        }
    }
}
