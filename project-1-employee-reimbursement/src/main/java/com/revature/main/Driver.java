package com.revature.main;

import com.revature.controller.AuthenticationController;
import com.revature.controller.Controller;
import com.revature.controller.ExceptionController;
import io.javalin.Javalin;

public class Driver {
    public static void main(String[] args) {
        Javalin app = Javalin.create((config) -> {
            config.enableCorsForAllOrigins();
        });

        map(app, new AuthenticationController(), new ExceptionController());

        app.start(8080);
    }

    private static void map(Javalin app, Controller... controllers) {
        for (Controller c : controllers) {
            c.mapEndpoints(app);
        }
    }
}
