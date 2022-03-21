package com.revature.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

public class Reimbursement {
    private int id;
    private double amount;
    private String submitTimestamp;
    private Date resolveTimestamp;
    private String description;
    private String receiptUrl;
    private User author;
    private User resolver;
    private ReimbStatus status;
    private ReimbType type;

    public Reimbursement() {

    }

    public Reimbursement(int id, double amount, String submitTimestamp, Date resolveTimestamp, String description, String receiptUrl,
                         User author, User resolver, ReimbStatus status, ReimbType type) {
        this.id = id;
        this.amount = amount;
        this.submitTimestamp = submitTimestamp;
        this.resolveTimestamp = resolveTimestamp;
        this.description = description;
        this.receiptUrl = receiptUrl;
        this.author = author;
        this.resolver = resolver;
        this.status = status;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSubmitTimestamp() {
        return submitTimestamp;
    }

    public void setSubmitTimestamp(String submitTimestamp) {
        this.submitTimestamp = submitTimestamp;
    }

    public Date getResolveTimestamp() {
        return resolveTimestamp;
    }

    public void setResolveTimestamp(Date resolveTimestamp) {
        this.resolveTimestamp = resolveTimestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReceiptUrl() {
        return receiptUrl;
    }

    public void setReceiptUrl(String receiptUrl) {
        this.receiptUrl = receiptUrl;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getResolver() {
        return resolver;
    }

    public void setResolver(User resolver) {
        this.resolver = resolver;
    }

    public ReimbStatus getStatus() {
        return status;
    }

    public void setStatus(ReimbStatus status) {
        this.status = status;
    }

    public ReimbType getType() {
        return type;
    }

    public void setType(ReimbType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reimbursement that = (Reimbursement) o;
        return id == that.id && Double.compare(that.amount, amount) == 0 && Objects.equals(submitTimestamp, that.submitTimestamp) && Objects.equals(resolveTimestamp, that.resolveTimestamp) && Objects.equals(description, that.description) && Objects.equals(receiptUrl, that.receiptUrl) && Objects.equals(author, that.author) && Objects.equals(resolver, that.resolver) && Objects.equals(status, that.status) && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, submitTimestamp, resolveTimestamp, description, receiptUrl, author, resolver, status, type);
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", amount=" + amount +
                ", submitTimestamp='" + submitTimestamp + '\'' +
                ", resolveTimestamp='" + resolveTimestamp + '\'' +
                ", description='" + description + '\'' +
                ", receiptUrl='" + receiptUrl + '\'' +
                ", author=" + author +
                ", resolver=" + resolver +
                ", status=" + status +
                ", type=" + type +
                '}';
    }
}
