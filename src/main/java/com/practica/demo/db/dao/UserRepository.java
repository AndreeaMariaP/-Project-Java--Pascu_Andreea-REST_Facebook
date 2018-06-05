package com.practica.demo.db.dao;

import com.practica.demo.db.entity.User;
import com.practica.demo.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(" SELECT u FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);

    @Query(" SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query(" SELECT u FROM User u WHERE u.id = :id")
    User findUserById(@Param("id") Integer id);

    @Query(" SELECT new com.practica.demo.model.UserModel(u.id,u.email,u.birth_date) FROM User u")
    List<UserModel> getAllUsers();


}
