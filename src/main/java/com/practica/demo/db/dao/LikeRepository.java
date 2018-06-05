package com.practica.demo.db.dao;

import com.practica.demo.db.entity.Like;
import com.practica.demo.model.LikeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Integer> {

    //returnez like pe baza userId si postId din Post object
    @Query("SELECT l FROM Like l WHERE l.post.id=:id AND l.userId=:userId")
    Like getLikeFromPost(@Param("userId") Integer userId, @Param("id")Integer id);

    @Query(" SELECT new com.practica.demo.model.LikeModel(l.likeId, l.userId, l.post.id) FROM Like l where l.post.id = :id")
    List<LikeModel> getLikesByPostId(@Param("id")Integer id);
}
