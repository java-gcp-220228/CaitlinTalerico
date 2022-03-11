package com.revature.response;

import java.util.Objects;

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

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseBody that = (ResponseBody) o;
        return success == that.success && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, message);
    }
}
