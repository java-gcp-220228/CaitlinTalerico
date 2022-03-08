package com.revature.utlity;

import org.postgresql.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    // Private Constructor, unable to use ConnectionUtility anywhere
    private ConnectionUtility() {

    }

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());

        // Credentials to establish connection
        String url = System.getenv("db_url");
        String username = System.getenv("bank_username");
        //String password = System.getenv("db_password");
        String password = "03571995Ct!";

        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }
}
