package com.practica.demo.model;

import java.util.Date;
import java.util.List;

enum Visibility{
    PUBLIC,
    PRIVATE
};

public class PostModel {

    private Integer id;
    private String postMessage;
    private Date creationDate;
    private Visibility visibility;
    private Integer userId;

    private List<CommentModel> comments;
    private List<LikeModel> likes;

    public PostModel() {
    }

    public PostModel(Integer id,String postMessage, Date creationDate, String visibility, Integer userId) {

        this.id=id;
        this.postMessage = postMessage;
        this.creationDate = creationDate;
        this.visibility = Visibility.valueOf(visibility);
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getVisibility() {
        return visibility.name();
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public String getStringVisibility(){ return visibility.name(); }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    public List<LikeModel> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeModel> likes) {
        this.likes = likes;
    }
}
