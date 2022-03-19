package com.revature.service;

import com.revature.dao.UserDao;
import com.revature.model.User;
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
        when(mockUserDao.getUserByUsernameAndPassword("admin", "password")).thenReturn(
                new User(1, "admin", "administrator"));
        User expectedUser = new User(1, "admin", "administrator");

        User actualUser = userService.login("admin", "password");


        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testFailedLoginAttempt() throws SQLException {
        when(mockUserDao.getUserByUsernameAndPassword(anyString(), anyString())).thenReturn(null);

        Assertions.assertThrows(FailedLoginException.class, ()->{
            userService.login("fakename", "fakepassword");
        });
    }
}
