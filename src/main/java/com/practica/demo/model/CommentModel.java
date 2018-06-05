package com.practica.demo.model;

import com.practica.demo.db.dao.PostRepository;
import com.practica.demo.db.dao.UserRepository;
import com.practica.demo.db.entity.Post;
import com.practica.demo.db.entity.User;
import com.practica.demo.service.MyUserService;
import com.practica.demo.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;

public class CommentModel {

    private Integer commentId;
    private String commentMessage;
    private Integer userId;
    private Integer postId;

    public CommentModel() {
    }

    public CommentModel(Integer commentId, String commentMessage, Integer userId, Integer postId) {
        this.commentId = commentId;
        this.commentMessage = commentMessage;
        this.userId = userId;
        this.postId = postId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentMessage() {
        return commentMessage;
    }

    public void setCommentMessage(String commentMessage) {
        this.commentMessage = commentMessage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
