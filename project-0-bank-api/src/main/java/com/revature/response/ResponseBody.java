package com.revature.response;

public class ResponseBody {

    private boolean success;
    private String message;

    private ResponseBody() {
    }

    public static ResponseBody deleteClient(boolean status) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setSuccess(status);
        if (status) {
            responseBody.setMessage("Client successfully deleted.");
        } else {
            responseBody.setMessage("Client could not be deleted at this time.");
        }

        return responseBody;
    }

    public static ResponseBody deleteAccount(boolean status) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setSuccess(status);
        if (status) {
            responseBody.setMessage("Account successfully deleted.");
        } else {
            responseBody.setMessage("Account could not be deleted at this time.");
        }

        return responseBody;
    }

    public void setSuccess(boolean status) {
        this.success = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
