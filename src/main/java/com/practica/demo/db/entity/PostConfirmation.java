package com.practica.demo.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "post_confirmation", schema = "public")
public class PostConfirmation {

    @Id
    @Column(name="userId")
    private Integer userId;

    @Column(name="first_post_date")
    private Date dateOfFirstPost;

    @Column(name="trials")
    private Integer trials;

    public PostConfirmation() {
    }

    public PostConfirmation(Integer userId, Date dateOfFirstPost, Integer numberOfTrials) {
        this.userId = userId;
        this.dateOfFirstPost = dateOfFirstPost;
        this.trials = numberOfTrials;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDateOfFirstPost() {
        return dateOfFirstPost;
    }

    public void setDateOfFirstPost(Date dateOfFirstPost) {
        this.dateOfFirstPost = dateOfFirstPost;
    }

    public Integer getTrials() {
        return trials;
    }

    public void setTrials(Integer numberOfTrials) {
        this.trials = numberOfTrials;
    }
}
