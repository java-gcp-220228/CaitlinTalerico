package com.revature.dao;

import com.revature.dto.AddReimbursementDTO;
import com.revature.model.*;
import com.revature.utility.ConnectionUtility;

import java.sql.*;
import java.util.Date;

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
    // TODO 8. addNewReimbursement
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

            String sqlSelect = "select * " +
                    "from reimbursements r " +
                    "inner join users ur " +
                    "on r.reimb_author = ur.user_id " +
                    "inner join reimbursement_type rt " +
                    "on r.reimb_type_id = rt.reimb_type_id " +
                    "inner join reimbursement_status rs " +
                    "on r.reimb_status_id = rs.reimb_status_id " +
                    "inner join user_roles ur2 " +
                    "on ur.user_role_id = ur2.user_role_id " +
                    "WHERE r.reimb_id = ?";

            PreparedStatement pstmtSelect = con.prepareStatement(sqlSelect);
            pstmtSelect.setInt(1, reimbId);

            ResultSet rsNewReimb = pstmtSelect.executeQuery();
            rsNewReimb.next();

            int id = rsNewReimb.getInt("reimb_id");
            double amount = rsNewReimb.getDouble("reimb_amount");
            String date = new Date(rsNewReimb.getTimestamp("reimb_submitted").getTime()).toString();
            String description = rsNewReimb.getString("reimb_description");
            String url = rsNewReimb.getString("reimb_receipt");
            UserRole userRole = new UserRole(rsNewReimb.getInt("user_role_id"), rsNewReimb.getString("user_role"));
            User author = new User(rsNewReimb.getInt("user_id"), rsNewReimb.getString("username"), rsNewReimb.getString("first_name"), rsNewReimb.getString("last_name"), rsNewReimb.getString("user_email"), userRole);
            ReimbStatus status = new ReimbStatus(rsNewReimb.getInt("reimb_status_id"), rsNewReimb.getString("reimb_status"));
            ReimbType type = new ReimbType(rsNewReimb.getInt("reimb_type_id"), rsNewReimb.getString("reimb_type"));

            Reimbursement reimbursement = new Reimbursement(id, amount, date, null, description, url, author, null, status, type);
            con.commit();
            return reimbursement;

        }
    }
}
