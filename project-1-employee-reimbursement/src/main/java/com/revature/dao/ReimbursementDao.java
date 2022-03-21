package com.revature.dao;

import com.revature.dto.AddReimbursementDTO;
import com.revature.model.*;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReimbursementDao {
    // With each reimbursement need:
        // id
        // amount
        // type
        // author -- names from users table
        // status -- associated with status type table
        // who resolved -- names from users table
        // description
        // image of receipt
        // type of reimbursement -- associated with reimb type table


    // TODO 1. getAllReimbursements
    // TODO 2. getAllReimbursements for a user
    // TODO 3. getAllReimbursements of certain status
    // TODO 4. getAllReimbursements of certain department
    // TODO 5. getAllReimbursements of certain department and user
    // TODO 6. getAllReimbursements of certain department, user, and status
    // TODO 7. getSingleReimbursement

    // TODO 9. updateReimbursement status --patch
    // TODO 10. updateReimbursement details --put
    // TODO 11. deleteReimbursement


    // Create
    public Reimbursement addReimbursement(AddReimbursementDTO addReimbursementDTO, String receiptUrl) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {
            con.setAutoCommit(false);
            String sql = "insert into reimbursements (reimb_amount, reimb_submitted, reimb_description, reimb_receipt, reimb_author, reimb_type_id) " +
                    "values " +
                    "(?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setDouble(1, addReimbursementDTO.getReimbAmount());//Amount
            pstmt.setTimestamp(2, addReimbursementDTO.getReimbSubmitted()); //Timestamp
            pstmt.setString(3, addReimbursementDTO.getReimbDescription()); //Description
            pstmt.setString(4, receiptUrl); //ReceiptURL
            pstmt.setInt(5, addReimbursementDTO.getReimbAuthor()); //AuthorID
            pstmt.setInt(6, addReimbursementDTO.getReimbType()); //TypeID

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            rs.next();
            int reimbId = rs.getInt(1);

            String sqlSelect = "SELECT * " +
                                "FROM tickets t " +
                                "WHERE  t.reimb_id = ?";

            PreparedStatement pstmtSelect = con.prepareStatement(sqlSelect);
            pstmtSelect.setInt(1, reimbId);

            ResultSet rsNewReimb = pstmtSelect.executeQuery();
            rsNewReimb.next();

            int id = rsNewReimb.getInt("reimb_id");
            double amount = rsNewReimb.getDouble("reimb_amount");
            String date = new Date(rsNewReimb.getTimestamp("reimb_submitted").getTime()).toString();
            String description = rsNewReimb.getString("reimb_description");
            String url = rsNewReimb.getString("reimb_receipt");
            String firstName = rsNewReimb.getString("first_name");
            String lastName = rsNewReimb.getString("last_name");
            String email = rsNewReimb.getString("user_email");
            String type = rsNewReimb.getString("reimb_type");
            String status = rsNewReimb.getString("reimb_status");



            Reimbursement reimbursement = new Reimbursement(id, amount, date, null, description, url, firstName, lastName, email, 0, status, type);
            con.commit();
            return reimbursement;

        }
    }

    // R
    public Reimbursement getReimbursementById(int reimbId) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT *" +
                    "FROM tickets t " +
                    "WHERE t.reimb_id = ?";
            PreparedStatement pstmtSelect = con.prepareStatement(sql);
            pstmtSelect.setInt(1, reimbId);

            ResultSet rs = pstmtSelect.executeQuery();
            rs.next();

            int id = rs.getInt("reimb_id");
            double amount = rs.getDouble("reimb_amount");
            String date = new Date(rs.getTimestamp("reimb_submitted").getTime()).toString();
            String description = rs.getString("reimb_description");
            String url = rs.getString("reimb_receipt");
            String firstName = rs.getString("first_name");
            String lastName = rs.getString("last_name");
            String email = rs.getString("user_email");
            String type = rs.getString("reimb_type");
            String status = rs.getString("reimb_status");


            Reimbursement reimbursement = new Reimbursement(id, amount, date, null, description, url, firstName, lastName, email, 0, status, type);
            con.commit();
            return reimbursement;
        }
    }
    public List<Reimbursement> getReimbursementsByUser(int userId) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()){
            String sql = "SELECT * " +
                    "FROM employees e " +
                    "WHERE e.user_id = ?";
            PreparedStatement pstmtSelect = con.prepareStatement(sql);
            pstmtSelect.setInt(1, userId);

            ResultSet rsEmail = pstmtSelect.executeQuery();
            rsEmail.next();

            // Get user email for second query
            String email = rsEmail.getString("user_email");


            String sqlSelect = "SELECT * " +
                    "FROM tickets t " +
                    "WHERE t.user_email = ?";
            PreparedStatement pstmt = con.prepareStatement(sqlSelect);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            rs.next();

            List<Reimbursement> reimbursements = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("reimb_id");
                double amount = rs.getDouble("reimb_amount");
                String date = new Date(rs.getTimestamp("reimb_submitted").getTime()).toString();
                String description = rs.getString("reimb_description");
                String url = rs.getString("reimb_receipt");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String type = rs.getString("reimb_type");
                String status = rs.getString("reimb_status");

                Reimbursement reimbursement = new Reimbursement(id, amount, date, null, description, url, firstName, lastName, email, 0, status, type);
                reimbursements.add(reimbursement);
            }
            return reimbursements;
            }
        }
}
