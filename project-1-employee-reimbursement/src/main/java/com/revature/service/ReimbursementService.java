package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.dto.ResponseReimbursementDTO;
import com.revature.dto.UpdateReimbursementDTO;
import com.revature.exception.InvalidFileTypeException;
import com.revature.exception.InvalidJsonBodyProvided;
import com.revature.exception.ReimbursementAlreadyResolved;
import com.revature.exception.ReimbursementDoesNotExist;
import com.revature.model.Reimbursement;
import com.revature.utility.UploadImageUtility;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.UploadedFile;

import javax.naming.SizeLimitExceededException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ReimbursementService {


    private final ReimbursementDao reimbursementDao;

    public ReimbursementService() {
        this.reimbursementDao = new ReimbursementDao();

    }
    public ReimbursementService(ReimbursementDao mockDao) {
        this.reimbursementDao = mockDao;
    }

    // C
    public ResponseReimbursementDTO addReimbursement(AddReimbursementDTO dto) throws SQLException, IOException, SizeLimitExceededException, InvalidFileTypeException, InvalidJsonBodyProvided {

        AddReimbursementDTO sanitizedDto = sanitizeNewReimbursement(dto);
        // Add timestamp
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        Timestamp submitted = Timestamp.valueOf(LocalDateTime.now());
        sanitizedDto.setReimbSubmitted(submitted);

        // Get URL for image
        UploadedFile uploadedImage = sanitizedDto.getReimbReceiptImage();
        if (uploadedImage.getSize() > 10000000) {
            throw new SizeLimitExceededException("File size exceeded limit of 10MB. Uploaded File Size: " + uploadedImage.getSize());
        }
        if (!uploadedImage.getContentType().equals("image/png") && !uploadedImage.getContentType().equals("image/jpg") && !uploadedImage.getContentType().equals("image/jpeg")) {
            throw new InvalidFileTypeException("Invalid file type for uploaded image. Accepted files: .png .jpg .jpeg");
        }

        String url = UploadImageUtility.uploadImage(uploadedImage);


        // Add the reimbursement

        return reimbursementDao.addReimbursement(sanitizedDto, url);
    }

    // R
    public Reimbursement getReimbursementById(String reimbId) throws SQLException, ReimbursementDoesNotExist {
        try {
            int rId = Integer.parseInt(reimbId);
            Reimbursement reimbursement = reimbursementDao.getReimbursementById(rId);
            if (reimbursement == null) {
                throw new ReimbursementDoesNotExist("A reimbursement with an id of " + rId + " does not exist at this time.");
            }
            return reimbursement;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid integer given for request.");
        }
    }

    public List<ResponseReimbursementDTO> getReimbursementsByUser(String userId) throws SQLException {
        try {
            int uId = Integer.parseInt(userId);
            return reimbursementDao.getReimbursementsByUser(uId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid integer given for request.");
        }
    }

    public List<ResponseReimbursementDTO> getReimbursementsByUserAndStatus(String userId, String currentStatus) throws SQLException, InvalidJsonBodyProvided {
        try {
            int uId = Integer.parseInt(userId);
            String status = validateStatus(currentStatus);
            return reimbursementDao.getReimbursementsByUserAndStatus(uId, status);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid integer given for the request.");
        }
    }

    public List<ResponseReimbursementDTO> getReimbursementsByDepartment(String department) throws SQLException, InvalidJsonBodyProvided {
        String dept = validateDepartment(department);
        return reimbursementDao.getReimbursementsByDepartment(dept);
    }
    public List<ResponseReimbursementDTO> getAllReimbursementsByStatus(String status) throws SQLException, InvalidJsonBodyProvided {
        String stat = validateStatus(status);
        return reimbursementDao.getAllReimbursementsByStatus(stat);
    }

    public List<ResponseReimbursementDTO> getAllReimbursementsByStatusAndDepartment(String status, String department) throws SQLException, InvalidJsonBodyProvided {
        String dept = validateDepartment(department);
        String stat = validateStatus(status);

        return reimbursementDao.getAllReimbursementsByStatusAndDepartment(stat, dept);
    }

    public List<ResponseReimbursementDTO> getAllReimbursements() throws SQLException {
        return reimbursementDao.getAllReimbursements();
    }

    public Reimbursement editUnresolvedReimbursement(String reimbId, UpdateReimbursementDTO dto) throws SQLException, ReimbursementAlreadyResolved {
        try {
            int rId = Integer.parseInt(reimbId);
            if (!(reimbursementDao.getReimbursementById(rId).getStatus().equals("Pending"))) {
                throw new ReimbursementAlreadyResolved("This reimbursement has already been approved or denied. Unable to make changes at this time.");
            }
            return reimbursementDao.editUnresolvedReimbursement(rId, dto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid integer for reimbursement id.");
        }
    }

    public boolean updateReimbursementStatus(String reimbId, String statId) throws SQLException, ReimbursementAlreadyResolved {
        try {
            int rId = Integer.parseInt(reimbId);
            int statusId = Integer.parseInt(statId);
            if(!(reimbursementDao.getReimbursementById(rId).getStatus().equals("Pending"))) {
                throw new ReimbursementAlreadyResolved("This reimbursement has already been approved or denied. Unable to make changes at this time.");
            }
            return reimbursementDao.updateReimbursementStatus(rId, statusId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid integer for reimbursement id.");
        }
    }

    public boolean deleteUnresolvedReimbursement(String reimbId) throws SQLException, ReimbursementAlreadyResolved {
        try {
            int rId = Integer.parseInt(reimbId);
            if(!(reimbursementDao.getReimbursementById(rId).getStatus().equals("Pending"))) {
                throw new ReimbursementAlreadyResolved("This reimbursement has already been approved or denied. Unable to make changes at this time.");
            }
            return reimbursementDao.deleteUnresolvedReimbursement(rId);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid integer for reimbursement id.");
        }
    }

    public String validateDepartment(String department) throws InvalidJsonBodyProvided {
        String dept = department.toLowerCase();
        if (!(dept.equals("management") || dept.equals("finance")  || dept.equals("hr") || dept.equals("it") || dept.equals("marketing") || dept.equals("sales") || dept.equals("quality assurance"))) {
            throw new InvalidJsonBodyProvided("Provided department is not a valid department. Input: " + department);
        }

        return dept.substring(0, 1).toUpperCase() + dept.substring(1);
    }

    public String validateStatus(String status) throws InvalidJsonBodyProvided {
        String stat = status.toLowerCase();
        if (!(stat.equals("pending") || stat.equals("approved") || stat.equals("rejected")))  {
            throw new InvalidJsonBodyProvided("Provided status is not a valid status. Input: " + status);
        }
        return stat.substring(0, 1).toUpperCase() + stat.substring(1);
    }

    public AddReimbursementDTO sanitizeNewReimbursement(AddReimbursementDTO dto) throws InvalidJsonBodyProvided {
        double amount = dto.getReimbAmount();
        String descrip = dto.getReimbDescription().trim();
        int type = dto.getReimbType();
        if (!(type == 10 || type == 20 || type == 30 || type == 40)) {
            throw new InvalidJsonBodyProvided("Invalid type id provided. Input: " + type);
        }
        if (amount < 0) {
            throw new InvalidJsonBodyProvided("Amount provided cannot be negative. Input: " + amount);
        }
        dto.setReimbDescription(descrip);
        return dto;
    }
}
