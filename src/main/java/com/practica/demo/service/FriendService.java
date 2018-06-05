package com.practica.demo.service;

import com.practica.demo.db.dao.BlockRepository;
import com.practica.demo.db.dao.FriendRepository;
import com.practica.demo.db.dao.UserRepository;
import com.practica.demo.db.entity.Friend;
import com.practica.demo.db.entity.User;
import com.practica.demo.model.FriendModel;
import com.practica.demo.model.UserModel;
import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueAlreadyInUseException;
import com.practica.demo.model.exception.ValueNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.List;

@Service
public class FriendService {

    @Autowired
    SessionService sessionService;

    @Autowired
    MyUserService myUserService;

    @Autowired
    UserManagementService userManagementService;

    @Autowired
    FriendService friendService;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    UserRepository userRepository;


    public void addFriendRequest(Integer userId, Integer friendId) throws InvalidArgumentsException, AuthenticationException, ValueNotFoundException, AuthorizationException, ValueAlreadyInUseException {
        if(userId==null || friendId == null)
        {
            throw new InvalidArgumentsException("Invalid arguments");
        }
        if(userId.equals(sessionService.getSessionModel().getUserId())){
            throw new AuthorizationException("You cannot send this friend request");
        }
        UserModel userModel=userManagementService.getUserById(sessionService.getSessionModel().getUserId());
        if(userModel==null){
            throw new ValueNotFoundException("User not found");
        }
        if(userModel.getId()!=userId)
        {
            throw new AuthenticationException("You are not authorized to send friend request");
        }
        UserModel friendModel = userManagementService.getUserById(friendId);
        if(friendModel==null)
        {
            throw new ValueNotFoundException("Friend does not exist");
        }

        if(blockRepository.getBlockByIds(userId,friendId)!=null || blockRepository.getBlockByIds(friendId,userId)!=null){
            //userId has friendId on block || friendId has userId on block
            throw new AuthorizationException("User is on block");
        }
        if(friendRepository.findFriendRequest(userId, friendId)!= null || friendRepository.findFriendRequest(friendId,userId)!= null){
            throw new ValueAlreadyInUseException("There is already a friend request in");
        }
        Friend friend = new Friend(userRepository.findUserById(userId),friendId,new Date(),false);
        friendRepository.save(friend);
    }

    public void acceptFriendRequest(Integer friendId, Boolean approved) throws InvalidArgumentsException, ValueNotFoundException {

        if(friendId==null ||approved==null)
        {
            throw new InvalidArgumentsException("Invalid id's");
        }

        Friend friend=friendRepository.findFriendRequest(friendId,sessionService.getSessionModel().getUserId());
        if(friend==null)
        {
            throw new ValueNotFoundException("No friend request found");
        }
        if(approved==true){
            friendRepository.updateFriendRequestStatus(sessionService.getSessionModel().getUserId());
        }
        if(approved==false)
        {
            friendRepository.delete(friend);
        }

    }

    public List<FriendModel> getFriendRequestsOfLoggedUser(Integer userId) throws InvalidArgumentsException {
        if(userId==null)
        {
            throw new InvalidArgumentsException("Invalid user id");
        }
        return friendRepository.getFriendRequestsOfLoggedUser(userId);
    }

    public void deleteFriendRequest(Integer friendId) throws InvalidArgumentsException, ValueNotFoundException {
        if(friendId==null){
            throw new InvalidArgumentsException("Invalid friend id");
        }
        Friend friend1=friendRepository.findByUserIdAndFriendId(sessionService.getSessionModel().getUserId(),friendId);
        Friend friend2=friendRepository.findByUserIdAndFriendId(friendId,sessionService.getSessionModel().getUserId());
        if(friend1==null && friend2==null){
                throw new ValueNotFoundException("There is no such friendship");
        }
        if(friend1==null){
            friendRepository.delete(friend2);
        }
        if(friend2==null){
            friendRepository.delete(friend1);
        }
    }
}
