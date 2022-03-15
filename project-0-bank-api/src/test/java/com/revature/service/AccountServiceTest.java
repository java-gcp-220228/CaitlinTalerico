package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.ClientDao;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Account;
import com.revature.model.Client;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountServiceTest {




    @Mock
    ClientDao mockClientDao;

    @Mock
    AccountDao mockAccountDao;

    @InjectMocks
    AccountService accountService;



    private AutoCloseable closeable;
    @BeforeAll
    public void set_up() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public void close_mocks() throws Exception {
        closeable.close();
    }


    //Positive

    @Test
    public void test_updateAccount() throws SQLException, ClientNotFoundException, AccountNotFoundException {
        Account mockAccount = new Account("Savings", 100, 1, 0);
        // Expected to return this account
        Account expectedAccount = new Account("Savings", 100, 1, 1);

        // When method goes to verify account and client
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.getAccountByAccountID(1, 0)).thenReturn(new Account());

        // When method goes to update the account number (based on how many accounts already there
        when(mockAccountDao.getAllAccounts(anyInt())).thenReturn(new ArrayList<>());

        // Get the updated Account
        when(mockAccountDao.updateAccount(anyInt(), anyInt(), any(Account.class))).thenReturn(expectedAccount);


        when(mockAccountDao.getAccountByAccountID(1, 1)).thenReturn(null);
        Account actualAccount = accountService.updateAccount("1", "0", mockAccount);

        // Check that each of the values in the returned account are equal to expected values

        Assertions.assertEquals(expectedAccount.getAccountType(), actualAccount.getAccountType());
        Assertions.assertEquals(expectedAccount.getBalance(), actualAccount.getBalance());
        Assertions.assertEquals(expectedAccount.getClientId(), actualAccount.getClientId());
        Assertions.assertEquals(expectedAccount.getClientId(), actualAccount.getAccountNumber());

    }
    // Positive
    @Test
    public void test_partial_Update_Account() throws SQLException, ClientNotFoundException, AccountNotFoundException {
        Account mockAccount = new Account();
        mockAccount.setAccountType("Savings");


        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.getAccountByAccountID(anyInt(), anyInt())).thenReturn(new Account());


        when(mockAccountDao.updateAccount(any(Account.class), anyInt())).thenReturn(new Account("Savings", 100, 2, 1));
        Account actualAccount = accountService.partialUpdateAccount("2", "1", mockAccount);

        // Check that each of the values in the returned account are equal to expected values
        Assertions.assertEquals(mockAccount.getAccountType(), actualAccount.getAccountType());
        Assertions.assertEquals(100, actualAccount.getBalance());
        Assertions.assertEquals(2, actualAccount.getClientId());
        Assertions.assertEquals(1, actualAccount.getAccountNumber());

    }

    // Positive
    @Test
    public void test_partial_Update_Account_Change_Balance() throws SQLException, ClientNotFoundException, AccountNotFoundException {
        Account mockAccount = new Account();
        mockAccount.setAccountType("Savings");
        mockAccount.setBalance(200.00);

        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.getAccountByAccountID(anyInt(), anyInt())).thenReturn(new Account());


        when(mockAccountDao.updateAccount(any(Account.class), anyInt())).thenReturn(new Account("Savings", 100, 2, 1));
        Account actualAccount = accountService.partialUpdateAccountChangeBalance("2", "1", mockAccount);

        // Check that each of the values in the returned account are equal to expected values
        Assertions.assertEquals(mockAccount.getAccountType(), actualAccount.getAccountType());
        Assertions.assertEquals(100, actualAccount.getBalance());
        Assertions.assertEquals(2, actualAccount.getClientId());
        Assertions.assertEquals(1, actualAccount.getAccountNumber());

    }
    // Positive
    @Test
    public void test_getAccountByID() throws SQLException, ClientNotFoundException, AccountNotFoundException {

        when(mockAccountDao.getAccountByAccountID(eq(1), eq(1))).thenReturn(
                new Account("Savings", 100.10, 1, 1)
        );
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        Account actualAccount = accountService.getAccountByID("1", "1");

        Account expectedAccount = new Account("Savings", 100.10, 1, 1);

        Assertions.assertEquals(expectedAccount, actualAccount);

    }

    // Negative
    @Test
    public void test_getAccountByID_AccountNotFoundException() throws SQLException {

        when(mockAccountDao.getAccountByAccountID(eq(1), eq(1))).thenReturn(null);
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());

        Assertions.assertThrows(AccountNotFoundException.class, ()-> {
            accountService.getAccountByID("1", "1");
        });

    }

    // Negative
    @Test
    public void test_getAccountByID_NoClientExistsException() throws SQLException {

        when(mockClientDao.getClientById(anyInt())).thenReturn(null);

        Assertions.assertThrows(ClientNotFoundException.class, () -> {
            accountService.getAccountByID("1", "1");
        });

    }

    // Positive
    @Test
    public void test_getAllAccounts() throws SQLException {
        List<Account> mockAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mockAccounts.add(new Account());
        }
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.getAllAccounts(eq(1))).thenReturn(mockAccounts);

        List<Account> actualAccounts = accountService.getAllAccounts("1");

        Assertions.assertEquals(mockAccounts.size(), actualAccounts.size());
    }

    // Positive
    @Test
    public void test_getAllAccountsLessThanValue() throws SQLException, ClientNotFoundException {
        List<Account> mockAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mockAccounts.add(new Account());
        }
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.getAccountsLessThan(eq(1), eq(100.00))).thenReturn(mockAccounts);

        List<Account> actualAccounts = accountService.getAllAccountsLessThan("1", "100.00");

        Assertions.assertEquals(mockAccounts.size(), actualAccounts.size());
    }

    // Positive
    @Test
    public void test_getAllAccountsGreaterThanValue() throws SQLException, ClientNotFoundException {
        List<Account> mockAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mockAccounts.add(new Account());
        }
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.getAccountsGreaterThan(eq(1), eq(100.00))).thenReturn(mockAccounts);

        List<Account> actualAccounts = accountService.getAllAccountsGreaterThan("1", "100.00");

        Assertions.assertEquals(mockAccounts.size(), actualAccounts.size());
    }

    // Positive
    @Test
    public void test_getAllAccountsBetween() throws SQLException, ClientNotFoundException {
        List<Account> mockAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mockAccounts.add(new Account());
        }
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.getAccountsBetween(eq(1), eq(100.00), eq(500.00))).thenReturn(mockAccounts);

        List<Account> actualAccounts = accountService.getAllAccounts("1", "100.00", "500.00");

        Assertions.assertEquals(mockAccounts.size(), actualAccounts.size());
    }

    // Positive
    @Test
    public void test_getAllAccountsByType() throws SQLException, ClientNotFoundException {
        List<Account> mockAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mockAccounts.add(new Account());
        }
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.getAccountsByType(eq(1), eq("Savings"))).thenReturn(mockAccounts);

        List<Account> actualAccounts = accountService.getAllAccountsByType("1", "Savings");

        Assertions.assertEquals(mockAccounts.size(), actualAccounts.size());
    }

    // Negative
    @Test
    public void test_getAllAccountsLessThanInvalidBalance() throws SQLException, ClientNotFoundException {
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());


        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            accountService.getAllAccountsGreaterThan("1", "abc");
        });
    }

    // Negative
    @Test
    public void test_getAllAccountsLessThanInvalidClientId() throws SQLException, ClientNotFoundException {
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            accountService.getAllAccountsGreaterThan("abc", "100");
        });
    }

    // Negative
    @Test
    public void test_getAllAccounts_NoAccounts_ReturnEmptyList() throws SQLException {
        List<Account> mockAccounts = new ArrayList<>();
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.getAllAccounts(eq(1))).thenReturn(mockAccounts);

        List<Account> actualAccounts = accountService.getAllAccounts("1");

        Assertions.assertEquals(0, actualAccounts.size());
    }





    //Negative
    @Test
    public void test_updateAccount_Account_Does_Not_Exist() throws SQLException, ClientNotFoundException, AccountNotFoundException {
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.getAccountByAccountID(anyInt(), anyInt())).thenReturn(null);

        Assertions.assertThrows(AccountNotFoundException.class, ()->{
            accountService.updateAccount("1", "1", new Account("Savings"));
        });

    }
    //Negative
    @Test
    public void test_updateAccount_Client_Does_Not_Exist() throws SQLException, ClientNotFoundException, AccountNotFoundException {
        when(mockClientDao.getClientById(anyInt())).thenReturn(null);

        Assertions.assertThrows(ClientNotFoundException.class, ()->{
            accountService.updateAccount("1", "1", new Account("Savings"));
        });

    }

    // Positive
    @Test
    public void test_createAccountSavings() throws SQLException, ClientNotFoundException {
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());

        when(mockAccountDao.createAccount(any(Account.class))).thenReturn(new Account("Savings", 200, 1, 0));

        Account expected = new Account("Savings", 200,1,0);
        Account actual = accountService.createAccount("0", new Account("Savings", 200));

        Assertions.assertEquals(expected, actual);

    }

    // Positive
    @Test
    public void test_createAccount_LowerCaseSpelling() throws SQLException, ClientNotFoundException {
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());

        when(mockAccountDao.createAccount(any(Account.class))).thenReturn(new Account("Checking", 200, 1, 0));

        Account expected = new Account("Checking", 200,1,0);
        Account actual = accountService.createAccount("0", new Account("checking", 200));

        Assertions.assertEquals(expected, actual);

    }
    // Positive
    @Test
    public void test_createAccount_MMA() throws SQLException, ClientNotFoundException {
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());

        when(mockAccountDao.createAccount(any(Account.class))).thenReturn(new Account("MMA", 200, 1, 0));

        Account expected = new Account("MMA", 200,1,0);
        Account actual = accountService.createAccount("0", new Account("mMa", 200));

        Assertions.assertEquals(expected, actual);

    }
    // Positive
    @Test
    public void test_createAccount_CD() throws SQLException, ClientNotFoundException {
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());

        when(mockAccountDao.createAccount(any(Account.class))).thenReturn(new Account("CD", 200, 1, 0));

        Account expected = new Account("CD", 200,1,0);
        Account actual = accountService.createAccount("0", new Account("cd", 200));

        Assertions.assertEquals(expected, actual);

    }

    //Negative
    @Test
    public void test_createAccount_Invalid_Account_Type() throws SQLException, ClientNotFoundException {
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());

        Assertions.assertThrows(IllegalArgumentException.class, ()->
                accountService.createAccount("1", new Account("None")));

    }


    @Test
    public void test_deleteAccount_true() throws SQLException, ClientNotFoundException, AccountNotFoundException {
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.deleteAccountById(anyInt(), anyInt())).thenReturn(true);
        when(mockAccountDao.getAccountByAccountID(1, 1)).thenReturn(new Account());
        when(mockAccountDao.getAccountByAccountID(1, 2)).thenReturn(null);

        Assertions.assertEquals(true, accountService.deleteAccount("1", "1"));

    }

    @Test
    public void test_deleteAccount_false() throws SQLException, ClientNotFoundException, AccountNotFoundException {
        when(mockClientDao.getClientById(anyInt())).thenReturn(new Client());
        when(mockAccountDao.deleteAccountById(anyInt(), anyInt())).thenReturn(false);
        when(mockAccountDao.getAccountByAccountID(1, 1)).thenReturn(new Account());

        Assertions.assertEquals(false, accountService.deleteAccount("1", "1"));

    }
}
