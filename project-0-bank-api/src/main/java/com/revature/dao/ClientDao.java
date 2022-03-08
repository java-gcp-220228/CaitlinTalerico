package com.revature.dao;

import com.revature.model.Client;
import com.revature.utlity.ConnectionUtility;

import java.sql.*;
import java.util.List;

public class ClientDao {
    public Client addClient(Client client) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "INSERT INTO clients (first_name, last_name, age) VALUES (?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setInt(3, client.getAge());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            System.out.println(rs);
            rs.next();
            int generatedId = rs.getInt(1);

            return new Client(generatedId, client.getFirstName(), client.getLastName(), client.getAge());
        }

    }

    public Client updateClient(Client client) {
        return client;
    }

    public Client getClient() {
        return null;
    }

    public List<Client> getAllClients() {
        return null;
    }



    public void deleteClient() {

    }
}
