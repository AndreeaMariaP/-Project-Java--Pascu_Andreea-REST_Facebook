package com.practica.demo.model;


import java.util.Date;

public class FriendModel {

    private Integer id;
    private Integer userId;
    private Integer friendId;
    private Date requestDate;
    private boolean confirmed=false;

    public FriendModel(Integer id, Integer userId, Integer friendId, Date requestDate, boolean confirmed) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
        this.requestDate = requestDate;
        this.confirmed = confirmed;
    }

    public FriendModel() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
