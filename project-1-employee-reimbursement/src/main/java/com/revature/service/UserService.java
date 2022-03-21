package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.dto.LoginDTO;
import com.revature.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.FailedLoginException;
import java.sql.SQLException;

public class UserService {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public UserService(UserDao mockDao) {
        this.userDao = mockDao;
    }

    public User login(LoginDTO dto) throws SQLException, FailedLoginException {
        User user = userDao.getUserByUsernameAndPassword(dto);

        if (user == null) {
            logger.warn("Failed login attempt.\nUsername: " + dto.getUsername());
            throw new FailedLoginException("Invalid username and\\or password was provided.");
        }
        logger.info("Successful login.\nUsername: " + dto.getUsername());
        return user;
    }
}
