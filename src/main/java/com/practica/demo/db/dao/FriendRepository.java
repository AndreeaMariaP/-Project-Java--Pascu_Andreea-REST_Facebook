package com.practica.demo.db.dao;

import com.practica.demo.db.entity.Friend;
import com.practica.demo.model.FriendModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.QueryParam;
import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Integer> {

    @Query("SELECT f FROM Friend f where f.id=:friendId")
    Friend findByFriendId(@Param("friendId")Integer friendId);

    @Transactional
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query("UPDATE Friend f SET f.confirmed=true where f.friendId=:friendId")
    void updateFriendRequestStatus(@Param("friendId")Integer friendId);

    @Query(" SELECT new com.practica.demo.model.FriendModel(f.id, f.user.id, f.friendId, f.requestDate, f.confirmed) FROM Friend f where f.user.id= :userId OR f.friendId=:userId")
    List<FriendModel> getFriendRequestsOfLoggedUser(@Param("userId") Integer userId);

    @Query("SELECT f FROM Friend f where f.user.id=:userId AND f.friendId=:friendId AND f.confirmed=true")
    Friend findByUserIdAndFriendId(@Param("userId")Integer userId,@Param("friendId")Integer friendId);

    @Query("SELECT f FROM Friend f where f.user.id=:userId AND f.friendId=:friendId")
    Friend findFriendRequest(@Param("userId")Integer userId,@Param("friendId")Integer friendId);

    @Query("SELECT f.friendId FROM Friend f WHERE f.user.id=:userId AND f.confirmed=true")
    List<Integer> getAcceptedFriendRequests(@Param("userId")Integer userId);
}
