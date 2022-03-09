package com.revature.dao;

import com.revature.model.Client;
import com.revature.utlity.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {
    // C-CREATE

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

    // R-READ
    public Client getClientById(int id) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT * FROM clients WHERE id =  ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, id);

            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs);

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");

                return new Client(id, firstName, lastName, age);
            }
        }
        return null;
    }

    public List<Client> getAllClients() throws SQLException {

        List<Client> clients = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT * FROM clients";
            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                int clientId = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int age = rs.getInt("age");

                clients.add(new Client(clientId, firstName, lastName, age));
            }
            return clients;
        }
    }




    // U-UPDATE

    public Client updateClient(Client client) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {
            String sql = "UPDATE clients SET first_name = ?, last_name = ?, age = ? WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setInt(3, client.getAge());
            pstmt.setInt(4, client.getId());

            pstmt.executeUpdate();
        }

        return client;
    }

    // D-DELETE
    public boolean deleteClientById(int id) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "DELETE FROM clients WHERE id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, id);

            int numberOfRecordsDelete = pstmt.executeUpdate();

            if (numberOfRecordsDelete == 1) {
                return true;
            }
        }
        return false;

    }
}
