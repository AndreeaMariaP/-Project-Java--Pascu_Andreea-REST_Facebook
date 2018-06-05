package com.practica.demo.db.dao;

import com.practica.demo.db.entity.PostConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
@Repository
public interface PostConfirmationRepository extends JpaRepository<PostConfirmation, Integer> {

    @Query(" Select trials FROM PostConfirmation  where userId=:userId")
     Integer getTrialsByUserId(@Param("userId") Integer userId);

    @Query(" SELECT u FROM PostConfirmation u WHERE u.userId = :userId")
    PostConfirmation findUserById(@Param("userId") Integer userId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PostConfirmation SET trials = trials + 1 where userId=:userId")
    void updateUserById(@Param("userId") Integer userId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE PostConfirmation SET trials = 1 , dateOfFirstPost = :first_post_date where userId =:userId")
    void updateDateAndResetUserById(@Param("userId") Integer userId,@Param("first_post_date") Date first_post_date);
}
