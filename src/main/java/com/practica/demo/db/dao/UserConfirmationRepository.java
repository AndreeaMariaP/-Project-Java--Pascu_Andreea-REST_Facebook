package com.practica.demo.db.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.practica.demo.db.entity.UserConfirmation;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface UserConfirmationRepository extends JpaRepository<UserConfirmation,Integer> {

    @Query(" SELECT u FROM UserConfirmation u WHERE u.email = :email")
    UserConfirmation findByEmail(@Param("email") String email);

    @Query(" SELECT u FROM UserConfirmation u WHERE u.id = :id")
    UserConfirmation findByUserId(@Param("id") Integer id);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(" UPDATE UserConfirmation SET confirmation_code=:confirmation_code WHERE id = :id")
    UserConfirmation updateConfirmationCode(@Param("id") Integer id,@Param("confirmation_code") String confirmation_code);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(" UPDATE UserConfirmation SET email_attempts=(email_attempts+1) WHERE id = :id")
    UserConfirmation updateEmailAttempts(@Param("id") Integer id);
}
