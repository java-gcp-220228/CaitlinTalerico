package com.revature.model;

import java.util.Objects;

public class ReimbStatus {
    private int id;
    private String status;

    public ReimbStatus() {}
     public ReimbStatus(int id, String status) {
        this.id = id;
        this.status = status;
     }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReimbStatus that = (ReimbStatus) o;
        return id == that.id && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, status);
    }

    @Override
    public String toString() {
        return "ReimbStatus{" +
                "id=" + id +
                ", status='" + status + '\'' +
                '}';
    }
}
