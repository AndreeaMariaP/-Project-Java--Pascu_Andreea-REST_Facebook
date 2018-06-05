package com.practica.demo.db.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(schema = "public", name = "blocked_accounts")
public class BlockedAccounts {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "number_of_attempts")
    private Integer NumberOfAttempts;

    @Column(name = "end_date")
    private Date endDate;

    public BlockedAccounts() {
    }

    public BlockedAccounts(String username, Integer numberOfAttempts, Date endDate) {
        this.username = username;
        this.NumberOfAttempts = numberOfAttempts;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumberOfAttempts() {
        return NumberOfAttempts;
    }

    public void setNumberOfAttempts(Integer numberOfAttempts) {
        NumberOfAttempts = numberOfAttempts;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
