package com.practica.demo.model;

public class MediaModel {
    private Integer id;
    private Integer userId;
    private String picturePath;

    public MediaModel() {
    }

    public MediaModel(Integer id, Integer userId, String picturePath) {
        this.id = id;
        this.userId = userId;
        this.picturePath = picturePath;
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

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
