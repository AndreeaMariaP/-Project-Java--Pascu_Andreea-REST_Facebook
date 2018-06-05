package com.practica.demo.db.dao;

import com.practica.demo.db.entity.Comment;
import com.practica.demo.model.CommentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment,Integer> {

    @Query("SELECT c FROM Comment c WHERE c.commentId=:commentId")
    Comment findCommentByCommentId(@Param("commentId")Integer commentId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Comment set commentMessage=:commentMessage where commentId=:commentId")
    void updateCommentMessage(@Param("commentId")Integer commentId,@Param("commentMessage") String commentMessage);

    @Query(" SELECT new com.practica.demo.model.CommentModel(c.commentId, c.commentMessage, c.userId, c.post.id) FROM Comment c where c.post.id = :id")
    List<CommentModel> getCommentsByPostId(@Param("id")Integer id);
}
