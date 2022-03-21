package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.exception.InvalidFileTypeException;
import com.revature.model.Reimbursement;
import com.revature.utility.UploadImageUtility;
import io.javalin.http.UploadedFile;

import javax.naming.SizeLimitExceededException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class ReimbursementService {


    private ReimbursementDao reimbursementDao;

    public ReimbursementService() {
        this.reimbursementDao = new ReimbursementDao();

    }
    public ReimbursementService(ReimbursementDao mockDao) {
        this.reimbursementDao = mockDao;
    }

    // C
    public Reimbursement addReimbursement(AddReimbursementDTO dto) throws SQLException, IOException, SizeLimitExceededException, InvalidFileTypeException {

        // Add timestamp
        TimeZone.setDefault(TimeZone.getTimeZone("EST"));
        Timestamp submitted = Timestamp.valueOf(LocalDateTime.now());
        dto.setReimbSubmitted(submitted);

        // Get URL for image
        UploadedFile uploadedImage = dto.getReimbReceiptImage();
        if (uploadedImage.getSize() > 10000000) {
            throw new SizeLimitExceededException("File size exceeded limit of 10MB. Uploaded File Size: " + uploadedImage.getSize());
        }
        if (!uploadedImage.getContentType().equals("image/png") && !uploadedImage.getContentType().equals("image/jpg") && !uploadedImage.getContentType().equals("image/jpeg")) {
            throw new InvalidFileTypeException("Invalid file type for uploaded image. Accepted files: .png .jpg .jpeg");
        }

        String url = UploadImageUtility.uploadImage(uploadedImage);


        // Add the reimbursement
        Reimbursement reimbursement = reimbursementDao.addReimbursement(dto, url);

        return reimbursement;
    }

    // R
    //public Reimbursement getSingleReimbursement()
}
