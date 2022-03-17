package com.revature.utility;

import com.revature.utlity.ConnectionUtility;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class ConnectionUtilityTest {

    @Disabled
    @Test
    public void test_getConnection() throws SQLException {
        ConnectionUtility.getConnection();
        // Any exception that gets thrown will automatically fail test
    }

}
