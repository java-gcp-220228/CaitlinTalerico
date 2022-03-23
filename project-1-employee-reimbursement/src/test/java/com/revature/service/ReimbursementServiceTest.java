package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.dto.UpdateReimbursementDTO;
import com.revature.exception.*;
import com.revature.model.Reimbursement;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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


//    @Test
//    public void testAddValidReimbursement() throws IOException, SQLException, SizeLimitExceededException, InvalidFileTypeException {
//        Reimbursement expected = new Reimbursement();
//        expected.setReceiptUrl("www.fake.com");
//        UploadImageUtility spy = spy(new UploadImageUtility());
//
//        when(UploadImageUtility.uploadImage(any(UploadedFile.class))).thenReturn("www.fake.com");
//        when(reimbursementDao.addReimbursement(any(AddReimbursementDTO.class), any(String.class))).thenReturn(expected);
//
//
//        Reimbursement actual = reimbursementService.addReimbursement(new AddReimbursementDTO());
//        Assertions.assertEquals(expected, actual);
//    }

    @Test
    public void getReimbursementByValidId() throws SQLException, ReimbursementDoesNotExist {
        Reimbursement expected = new Reimbursement(1, 100.20, "02/10/2022", null, "Beach Dya Expense", "www.mock.com", "Michael", "Scott", "email@email.com", 0, "food", "pending" );
        when(reimbursementDao.getReimbursementById(eq(1))).thenReturn(expected);
        Reimbursement actual = reimbursementService.getReimbursementById("1");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getReimbursementByInvalidId() {

        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            reimbursementService.getReimbursementById("abc");
        });
    }

    @Test
    public void getReimbursementThatDoesNotExist() throws SQLException {
        when(reimbursementDao.getReimbursementById(anyInt())).thenReturn(null);

        Assertions.assertThrows(ReimbursementDoesNotExist.class, ()->{
            reimbursementService.getReimbursementById("1");
        });
    }

    @Test
    public void getAllReumbursementsForAUser() throws SQLException {
        List<ResponseReimbursementDTO> mockReimbursements = new ArrayList<>();
        for (int i =0; i < 5; i++) {
            mockReimbursements.add(new ResponseReimbursementDTO());
        }

        when(reimbursementDao.getReimbursementsByUser(anyInt())).thenReturn(mockReimbursements);

        List<ResponseReimbursementDTO> actualReimbursements = reimbursementService.getReimbursementsByUser("1");

        Assertions.assertEquals(mockReimbursements, actualReimbursements);
        Assertions.assertEquals(mockReimbursements.size(), actualReimbursements.size());
        Assertions.assertEquals(mockReimbursements.get(2), actualReimbursements.get(2));

    }

    @Test
    public void getAllReimbursementsForAnInvalidUser() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            reimbursementService.getReimbursementsByUser("abc");
        });
    }

    @Test
    public void getReimbursementsByValidUserAndValidStatus() throws SQLException, InvalidQueryParamProvided {
        List<ResponseReimbursementDTO> mockReimbursements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ResponseReimbursementDTO reimbursement = new ResponseReimbursementDTO();
            reimbursement.setStatus("Pending");
            mockReimbursements.add(reimbursement);
        }

        when(reimbursementDao.getReimbursementsByUserAndStatus(1, "Pending")).thenReturn(mockReimbursements);

        List<ResponseReimbursementDTO> actualReimbursements = reimbursementService.getReimbursementsByUserAndStatus("1", "Pending");

        Assertions.assertEquals(mockReimbursements, actualReimbursements);
        Assertions.assertEquals("Pending", actualReimbursements.get(0).getStatus());
        Assertions.assertEquals(mockReimbursements.size(), actualReimbursements.size());

    }

    @Test
    public void getReimbursementsByValidUserAndValidStatusWithOddCasing() throws SQLException, InvalidQueryParamProvided {
        List<ResponseReimbursementDTO> mockReimbursements = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ResponseReimbursementDTO reimbursement = new ResponseReimbursementDTO();
            reimbursement.setStatus("Approved");
            mockReimbursements.add(reimbursement);
        }

        when(reimbursementDao.getReimbursementsByUserAndStatus(1, "Approved")).thenReturn(mockReimbursements);

        List<ResponseReimbursementDTO> actualReimbursements = reimbursementService.getReimbursementsByUserAndStatus("1", "ApPrOveD");

        Assertions.assertEquals(mockReimbursements, actualReimbursements);
        Assertions.assertEquals("Approved", actualReimbursements.get(0).getStatus());
        Assertions.assertEquals(mockReimbursements.size(), actualReimbursements.size());

    }

    @Test
    public void getReimbursementsByValidUserAndInvalidStatus() {

        Assertions.assertThrows(InvalidQueryParamProvided.class, ()->{
            reimbursementService.getReimbursementsByUserAndStatus("1", "Pend");
        });
    }

    @Test
    public void getReimbursementsByInvalidUserAndValidStatus() {

        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            reimbursementService.getReimbursementsByUserAndStatus("abc", "Approved");
        });
    }

    @Test
    public void getReimbursementsByValidDepartment() throws SQLException, InvalidQueryParamProvided {
        List<ResponseReimbursementDTO> mockReimbursements = new ArrayList<>();
        for (int i =0; i<10; i++) {
            mockReimbursements.add(new ResponseReimbursementDTO());
        }

        when(reimbursementDao.getReimbursementsByDepartment("Finance")).thenReturn(mockReimbursements);

        List<ResponseReimbursementDTO> actualReimbursements = reimbursementService.getReimbursementsByDepartment("Finance");

        Assertions.assertEquals(mockReimbursements, actualReimbursements);
        Assertions.assertEquals(mockReimbursements.size(), actualReimbursements.size());
        Assertions.assertEquals(mockReimbursements.get(0), actualReimbursements.get(0));
    }


    @Test
    public void getReimbursementsByInvalidDepartment(){
        Assertions.assertThrows(InvalidQueryParamProvided.class, ()->{
            reimbursementService.getReimbursementsByDepartment("Party Planning");
        });
    }

    @Test
    public void getAllReimbursementsByValidStatus() throws SQLException, InvalidQueryParamProvided {
        List<ResponseReimbursementDTO> mockReimbursements = new ArrayList<>();
        for(int i =0; i < 5; i++) {
            ResponseReimbursementDTO reimbursement = new ResponseReimbursementDTO();
            reimbursement.setStatus("Rejected");
            mockReimbursements.add(reimbursement);
        }

        when(reimbursementDao.getAllReimbursementsByStatus("Rejected")).thenReturn(mockReimbursements);

        List<ResponseReimbursementDTO> actualReimbursements = reimbursementService.getAllReimbursementsByStatus("Rejected");

        Assertions.assertEquals(mockReimbursements, actualReimbursements);
        Assertions.assertEquals(mockReimbursements.get(0).getStatus(), actualReimbursements.get(0).getStatus());

    }

    @Test
    public void getAllReimbursementsByInvalidStatus() {
        Assertions.assertThrows(InvalidQueryParamProvided.class, ()->{
            reimbursementService.getAllReimbursementsByStatus("Finalized");
        });
    }

    @Test
    public void getAllReimbursementsByValidStatusAndDepartment() throws SQLException, InvalidQueryParamProvided {
        List<ResponseReimbursementDTO> mockReimbursements = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ResponseReimbursementDTO reimbursement = new ResponseReimbursementDTO();
            reimbursement.setStatus("Approved");
            mockReimbursements.add(reimbursement);
        }

        when(reimbursementDao.getAllReimbursementsByStatusAndDepartment("Approved", "Finance")).thenReturn(mockReimbursements);

        List<ResponseReimbursementDTO> actualReimbursements = reimbursementService.getAllReimbursementsByStatusAndDepartment("Approved", "Finance");

        Assertions.assertEquals(mockReimbursements, actualReimbursements);
        Assertions.assertEquals(mockReimbursements.get(0).getStatus(), actualReimbursements.get(0).getStatus());

    }

    @Test
    public void getAllReimbursementsByInvalidStatusAndValidDepartment() {
        Assertions.assertThrows(InvalidQueryParamProvided.class, ()->{
            reimbursementService.getAllReimbursementsByStatusAndDepartment("Final", "Finance");
        });
    }

    @Test
    public void getAllReimbursementsByValidStatusAndInvalidDepartment() {
        Assertions.assertThrows(InvalidQueryParamProvided.class, ()->{
            reimbursementService.getAllReimbursementsByStatusAndDepartment("Approved", "Pizza");
        });
    }

    @Test
    public void getAllReimbursements() throws SQLException {
        List<ResponseReimbursementDTO> mockReimbursements = new ArrayList<>();
        for (int i =0; i < 5; i++) {
            mockReimbursements.add(new ResponseReimbursementDTO());
        }

        when(reimbursementDao.getAllReimbursements()).thenReturn(mockReimbursements);

        List<ResponseReimbursementDTO> actualReimbursements = reimbursementService.getAllReimbursements();

        Assertions.assertEquals(mockReimbursements, actualReimbursements);
    }

    @Test
    public void editUnresolvedReimbursementUser() throws SQLException, ReimbursementAlreadyResolved, ReimbursementDoesNotExist {
        Reimbursement mockReimbursement = new Reimbursement();
        mockReimbursement.setStatus("Pending");
        when(reimbursementDao.getReimbursementById(anyInt())).thenReturn(mockReimbursement);
        when(reimbursementDao.editUnresolvedReimbursement(anyInt(), any(UpdateReimbursementDTO.class))).thenReturn(new Reimbursement());

        Reimbursement expectedReimbursement = new Reimbursement();
        Reimbursement actualReimbursement = reimbursementService.editUnresolvedReimbursement("1", new UpdateReimbursementDTO());

        Assertions.assertEquals(expectedReimbursement, actualReimbursement);
    }

    @Test
    public void editUnresolvedReimbursementInvalidReimbursementId() {
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            reimbursementService.editUnresolvedReimbursement("abc", new UpdateReimbursementDTO());
        });
    }

    @Test
    public void attemptToEditResolvedReimbursement() throws SQLException {
        Reimbursement mockReimbursement = new Reimbursement();
        mockReimbursement.setStatus("Approved");

        when(reimbursementDao.getReimbursementById(anyInt())).thenReturn(mockReimbursement);

        Assertions.assertThrows(ReimbursementAlreadyResolved.class, ()->{
            reimbursementService.editUnresolvedReimbursement("1", new UpdateReimbursementDTO());
        });
    }

    @Test
    public void updateUnresolvedReimbursementStatus() throws SQLException, ReimbursementAlreadyResolved, ReimbursementDoesNotExist, InvalidQueryParamProvided {
        Reimbursement mockReimbursement = new Reimbursement();
        mockReimbursement.setStatus("Pending");

        when(reimbursementDao.getReimbursementById(anyInt())).thenReturn(mockReimbursement);
        when(reimbursementDao.updateReimbursementStatus(anyInt(), anyInt())).thenReturn(true);

        Assertions.assertTrue(reimbursementService.updateReimbursementStatus("1", "101"));
    }

    @Test
    public void failToUpdateResolvedReimbursementStatus() throws SQLException {
        Reimbursement mockReimbursement = new Reimbursement();
        mockReimbursement.setStatus("Rejected");

        when(reimbursementDao.getReimbursementById(anyInt())).thenReturn(mockReimbursement);


        Assertions.assertThrows(ReimbursementAlreadyResolved.class, ()->{
            reimbursementService.updateReimbursementStatus("1", "1");
        });
    }

    @Test
    public void failToUpdateNonexistentReimbursementStatus() throws SQLException {
        when(reimbursementDao.getReimbursementById(anyInt())).thenReturn(null);
        Assertions.assertThrows(ReimbursementDoesNotExist.class, ()->{
            reimbursementService.updateReimbursementStatus("1", "101");
        });
    }

    @Test
    public void updateReimbursementStatusWithInvalidStatusCode() throws SQLException {
        Reimbursement mockReimbursement = new Reimbursement();
        mockReimbursement.setStatus("Pending");
        when(reimbursementDao.getReimbursementById(anyInt())).thenReturn(mockReimbursement);


        Assertions.assertThrows(InvalidQueryParamProvided.class, ()->{
            reimbursementService.updateReimbursementStatus("1", "1");
        });
    }

    @Test
    public void deleteUnresolvedReimbursement() throws SQLException, ReimbursementAlreadyResolved, ReimbursementDoesNotExist {
        Reimbursement mockReimbursement = new Reimbursement();
        mockReimbursement.setStatus("Pending");

        when(reimbursementDao.getReimbursementById(anyInt())).thenReturn(mockReimbursement);
        when(reimbursementDao.deleteUnresolvedReimbursement(anyInt())).thenReturn(true);

        Assertions.assertTrue(reimbursementService.deleteUnresolvedReimbursement("1"));
    }

    @Test
    public void deleteResolvedReimbursement() throws SQLException {
        Reimbursement mockReimbursement = new Reimbursement();
        mockReimbursement.setStatus("Approved");

        when(reimbursementDao.getReimbursementById(anyInt())).thenReturn(mockReimbursement);

        Assertions.assertThrows(ReimbursementAlreadyResolved.class, ()->{
            reimbursementService.deleteUnresolvedReimbursement("1");
        });
    }

    @Test
    public void attemptToDeleteNonExistentReimbursement() throws SQLException {
        when(reimbursementDao.getReimbursementById(anyInt())).thenReturn(null);

        Assertions.assertThrows(ReimbursementDoesNotExist.class, ()->{
            reimbursementService.deleteUnresolvedReimbursement("1");
        });

    }

}

