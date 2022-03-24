package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.dto.LoginDTO;
import com.revature.model.User;
import com.revature.model.UserRole;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.security.auth.login.FailedLoginException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {



    @Mock
    UserDao mockUserDao;


    @InjectMocks
    UserService userService;



    private LoginDTO dto = new LoginDTO();
    private AutoCloseable closeable;
    @BeforeAll
    public void set_up() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public void close_mocks() throws Exception {
        closeable.close();
    }

    @Test
    public void testSuccessfulLoginAttempt() throws SQLException, FailedLoginException {
        dto.setUsername("admin");
        dto.setPassword("pass");

        UserRole userRole = new UserRole(1, "administrator");

        when(mockUserDao.getUserByUsernameAndPassword(dto)).thenReturn(
                new User(1, "admin", "Jane", "Doe", "email@test.com", userRole));
        User expectedUser = new User(1, "admin", "Jane", "Doe", "email@test.com", userRole);

        User actualUser = userService.login(dto);


        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testFailedLoginAttempt() throws SQLException {
        when(mockUserDao.getUserByUsernameAndPassword(dto)).thenReturn(null);

        Assertions.assertThrows(FailedLoginException.class, ()->{
            userService.login(dto);
        });
    }
}
