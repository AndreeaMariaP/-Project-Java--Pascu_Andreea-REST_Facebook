package com.practica.demo.model;

import com.practica.demo.db.dao.PostRepository;
import com.practica.demo.db.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;

public class LikeModel {



    private Integer likeId;
    private Integer userId;
    private Integer postId;

    public LikeModel() {
    }

    public LikeModel(Integer likeId, Integer userId, Integer postId) {
        this.likeId = likeId;
        this.userId = userId;
        this.postId = postId;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getLikeId() {
        return likeId;
    }

    public void setLikeId(Integer likeId) {
        this.likeId = likeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
