package com.revature.service;

import com.revature.dao.ReimbursementDao;
import com.revature.dto.AddReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.utility.UploadImageUtility;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class ReimbursementService {


    private ReimbursementDao reimbursementDao;

    public ReimbursementService() {
        this.reimbursementDao = new ReimbursementDao();

    }
    public ReimbursementService(ReimbursementDao mockDao) {
        this.reimbursementDao = mockDao;
    }

    public Reimbursement addReimbursement(AddReimbursementDTO dto) throws SQLException, IOException {
        String url = UploadImageUtility.uploadImage(dto.getReimbReceiptImage());

        Reimbursement reimbursement = reimbursementDao.addReimbursement(dto, url);

        return reimbursement;
    }
}
