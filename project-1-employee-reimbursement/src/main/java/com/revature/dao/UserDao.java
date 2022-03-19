package com.revature.dao;

import com.revature.model.User;
import com.revature.utility.ConnectionUtility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    public UserDao(){}

    public User getUserByUsernameAndPassword(String username, String password) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "select u.user_id, u.username, ur.user_role " +
                    "from users u " +
                    "inner join user_roles ur " +
                    "on ur.user_role_id = u.user_role_id " +
                    "where u.username = ? and u.user_password = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) { //login succesful
                int userId = rs.getInt("user_id");
                String usName = rs.getString("username");
                String role = rs.getString("user_role");

                return new User(userId, usName, role);
            }


            return null;
        }
    }
}
