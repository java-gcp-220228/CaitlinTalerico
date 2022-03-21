package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.exception.InvalidFileTypeException;
import com.revature.model.Reimbursement;
import com.revature.utility.UploadImageUtility;
import io.javalin.http.UploadedFile;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.naming.SizeLimitExceededException;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReimbursementServiceTest {

    @Mock
    ReimbursementDao reimbursementDao;


    @InjectMocks
    ReimbursementService reimbursementService;

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
    public void testAddValidReimbursement() throws IOException, SQLException, SizeLimitExceededException, InvalidFileTypeException {
        Reimbursement expected = new Reimbursement();
        expected.setReceiptUrl("www.fake.com");
        UploadImageUtility spy = spy(new UploadImageUtility());

        when(UploadImageUtility.uploadImage(any(UploadedFile.class))).thenReturn("www.fake.com");
        when(reimbursementDao.addReimbursement(any(AddReimbursementDTO.class), any(String.class))).thenReturn(expected);


        Reimbursement actual = reimbursementService.addReimbursement(new AddReimbursementDTO());
        Assertions.assertEquals(expected, actual);
    }
}
