package com.revature.model;

import java.util.Objects;

public class Reimbursement {

    private int reimbId;
    private double reimbAmount;
    private String reimbSubmitted;
    private String reimbResolved;
    private String reimbDescription;
    private String reimbReceiptUrl;
    private int reimbAuthor;
    private int reimbResolver;
    private int reimbStatus;
    private int reimbType;

    public Reimbursement(){}

    public Reimbursement(int reimbId, double reimbAmount, String reimbSubmitted, String reimbResolved,
                         String reimbDescription, String reimbReceiptUrl, int reimbAuthor,
                         int reimbResolver, int reimbStatus, int reimbType) {
        this.reimbId = reimbId;
        this.reimbAmount = reimbAmount;
        this.reimbSubmitted = reimbSubmitted;
        this.reimbResolved = reimbResolved;
        this.reimbDescription = reimbDescription;
        this.reimbReceiptUrl = reimbReceiptUrl;
        this.reimbAuthor = reimbAuthor;
        this.reimbResolver = reimbResolver;
        this.reimbStatus = reimbStatus;
        this.reimbType = reimbType;
    }

    public int getReimbId() {
        return reimbId;
    }

    public void setReimbId(int reimbId) {
        this.reimbId = reimbId;
    }

    public double getReimbAmount() {
        return reimbAmount;
    }

    public void setReimbAmount(double reimbAmount) {
        this.reimbAmount = reimbAmount;
    }

    public String getReimbSubmitted() {
        return reimbSubmitted;
    }

    public void setReimbSubmitted(String reimbSubmitted) {
        this.reimbSubmitted = reimbSubmitted;
    }

    public String getReimbResolved() {
        return reimbResolved;
    }

    public void setReimbResolved(String reimbResolved) {
        this.reimbResolved = reimbResolved;
    }

    public String getReimbDescription() {
        return reimbDescription;
    }

    public void setReimbDescription(String reimbDescription) {
        this.reimbDescription = reimbDescription;
    }

    public String getReimbReceiptUrl() {
        return reimbReceiptUrl;
    }

    public void setReimbReceiptUrl(String reimbReceiptUrl) {
        this.reimbReceiptUrl = reimbReceiptUrl;
    }

    public int getReimbAuthor() {
        return reimbAuthor;
    }

    public void setReimbAuthor(int reimbAuthor) {
        this.reimbAuthor = reimbAuthor;
    }

    public int getReimbResolver() {
        return reimbResolver;
    }

    public void setReimbResolver(int reimbResolver) {
        this.reimbResolver = reimbResolver;
    }

    public int getReimbStatus() {
        return reimbStatus;
    }

    public void setReimbStatus(int reimbStatus) {
        this.reimbStatus = reimbStatus;
    }

    public int getReimbType() {
        return reimbType;
    }

    public void setReimbType(int reimbType) {
        this.reimbType = reimbType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return reimbId == that.reimbId && Double.compare(that.reimbAmount, reimbAmount) == 0 && reimbAuthor == that.reimbAuthor && reimbResolver == that.reimbResolver && reimbStatus == that.reimbStatus && reimbType == that.reimbType && Objects.equals(reimbSubmitted, that.reimbSubmitted) && Objects.equals(reimbResolved, that.reimbResolved) && Objects.equals(reimbDescription, that.reimbDescription) && Objects.equals(reimbReceiptUrl, that.reimbReceiptUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reimbId, reimbAmount, reimbSubmitted, reimbResolved, reimbDescription, reimbReceiptUrl, reimbAuthor, reimbResolver, reimbStatus, reimbType);
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbId=" + reimbId +
                ", reimbAmount=" + reimbAmount +
                ", reimbSubmitted='" + reimbSubmitted + '\'' +
                ", reimbResolved='" + reimbResolved + '\'' +
                ", reimbDescription='" + reimbDescription + '\'' +
                ", reimbReceiptUrl='" + reimbReceiptUrl + '\'' +
                ", reimbAuthor=" + reimbAuthor +
                ", reimbResolver=" + reimbResolver +
                ", reimbStatus=" + reimbStatus +
                ", reimbType=" + reimbType +
                '}';
    }
}
