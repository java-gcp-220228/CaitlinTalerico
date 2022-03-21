package com.revature.dao;

import com.revature.dto.LoginDTO;
import com.revature.model.User;
import com.revature.model.UserRole;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public UserDao(){}

    public User getUserByUsernameAndPassword(LoginDTO dto) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "select u.user_id, u.username, u.first_name, u.last_name, u.user_email, ur.user_role_id, ur.user_role " +
                    "from users u " +
                    "inner join user_roles ur " +
                    "on ur.user_role_id = u.user_role_id " +
                    "where u.username = ? and u.user_password = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, dto.getUsername());
            pstmt.setString(2, dto.getPassword());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) { //login successful
                int userId = rs.getInt("user_id");
                String usName = rs.getString("username");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("user_email");
                int userRoleId = rs.getInt("user_role_id");
                String role = rs.getString("user_role");

                UserRole userRole = new UserRole(userRoleId, role);
                return new User(userId, usName, firstName, lastName, email, userRole);
            }


            return null;
        }
    }
}
