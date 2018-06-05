package com.practica.demo.db.dao;

import com.practica.demo.db.entity.Post;
import com.practica.demo.model.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    @Query(" SELECT new com.practica.demo.model.PostModel(p.id, p.message,p.creationDate, p.visibility,p.user.id) FROM Post p where p.user.id = :userId")
    List<PostModel> getPostsByUserId(@Param("userId") Integer userId);

    @Query("SELECT p FROM Post p where p.id=:id")
    Post getPostByPostId(@Param("id") Integer id);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Post p SET p.message=:message, p.visibility=:visibility where p.id=:id")
    void updatePost(@Param("id") Integer id, @Param("message")String message, @Param("visibility")String visibility);

}
