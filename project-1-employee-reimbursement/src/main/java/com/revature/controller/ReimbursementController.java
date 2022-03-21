package com.revature.controller;

import com.revature.exception.InvalidFileTypeException;
import com.revature.dto.AddReimbursementDTO;
import com.revature.model.Reimbursement;
import com.revature.service.JWTService;
import com.revature.service.ReimbursementService;
import com.revature.utility.UploadImageUtility;
import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.http.UploadedFile;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import javax.naming.SizeLimitExceededException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class ReimbursementController implements Controller{

    private JWTService jwtService;
    private ReimbursementService reimbursementService;

    public ReimbursementController() {
        this.jwtService = new JWTService();
        this.reimbursementService = new ReimbursementService();
    }

    // /users/{user_id}/reimbursements
    private Handler addReimbursement = (ctx) -> {
        String jwt = ctx.header("Authorization").split(" ")[1];

        // All roles should be able to add a reimbursement,
        // Just check that they have a valid token
        Jws<Claims> token = this.jwtService.parseJwt(jwt);

        String userId = ctx.pathParam("user_id");
        if(!(""+token.getBody().get("user_id")).equals(userId)) {
            throw new UnauthorizedResponse("You cannot add a reimbursement request for anyone but yourself.");
        }
        UploadedFile uploadedImage = ctx.uploadedFiles().get(0);


        AddReimbursementDTO dto = new AddReimbursementDTO();
        dto.setReimbAmount(Double.parseDouble(ctx.formParam("amount")));
        dto.setReimbAuthor(Integer.parseInt(ctx.formParam("author")));
        dto.setReimbDescription(ctx.formParam("description"));
        dto.setReimbType(Integer.parseInt(ctx.formParam("type")));
        dto.setReimbReceiptImage(uploadedImage);
        Reimbursement newReimbursement = reimbursementService.addReimbursement(dto);
        ctx.status(200);
        ctx.json(newReimbursement);

    };



    @Override
    public void mapEndpoints(Javalin app) {
        //C
        app.post("/users/{user_id}/reimbursements", addReimbursement);
        //R
//        app.get("/users/{user_id}/reimbursements", getAllReimbursementsForUser); //?status=
//        app.get("/reimbursements", getAllReimbursements); //?role=  ?status=
//        app.get("/users/{user_id}/reimbursements/{reimb_id}", getSingleReimbursement);
        //U-Partial
        //D
    }
}
