package com.practica.demo.web;

import com.practica.demo.model.FriendModel;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import com.practica.demo.service.FriendService;
import com.practica.demo.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class FriendController {
    @Autowired
    FriendService friendService;

    @Autowired
    SessionService sessionService;

    @PostMapping("/my-user/friends")
    public void addFriendRequest(@RequestBody Integer friendId){
        try{
        friendService.addFriendRequest(sessionService.getSessionModel().getUserId(),friendId);}
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @PutMapping("/my-user/friends/{friendId}")
    public void acceptFriendRequest(@RequestBody Boolean approved, @PathVariable("friendId")Integer friendId) throws ValueNotFoundException, InvalidArgumentsException {
        friendService.acceptFriendRequest(friendId,approved);
    }

    @GetMapping("/my-user/friends")
    public List<FriendModel> getFriendRequestsOfLoggedUser() throws InvalidArgumentsException {
       return friendService.getFriendRequestsOfLoggedUser(sessionService.getSessionModel().getUserId());
    }

    @DeleteMapping("/my-user/friends/{friendId}")
    public void deleteFriendRequest(@PathVariable("friendId")Integer friendId) throws ValueNotFoundException, InvalidArgumentsException {
        friendService.deleteFriendRequest(friendId);
    }
}
