package com.practica.demo.db.dao;

import com.practica.demo.db.entity.BlockedAccounts;
import com.practica.demo.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


public interface BlockedAccountsRepository extends JpaRepository<BlockedAccounts, Integer> {

    @Query(" SELECT u FROM BlockedAccounts u WHERE u.username = :username")
    BlockedAccounts findByUsername(@Param("username") String username);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(" UPDATE BlockedAccounts SET number_of_attempts=number_of_attempts+1 where username=:username")
    void updateNumberOfAttempts(@Param("username") String username);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(" UPDATE BlockedAccounts SET endDate=SYSDATE+60 where username=:username")
    void updateEndDate(String username);
}
