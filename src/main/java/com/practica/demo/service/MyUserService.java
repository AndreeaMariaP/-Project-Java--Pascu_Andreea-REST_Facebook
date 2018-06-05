package com.practica.demo.service;

import com.practica.demo.db.dao.*;
import com.practica.demo.db.entity.BlockedAccounts;
import com.practica.demo.db.entity.Friend;
import com.practica.demo.db.entity.User;
import com.practica.demo.model.SessionModel;
import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.LoginModel;
import com.practica.demo.model.PostModel;
import com.practica.demo.model.UserModel;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class MyUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private BlockedAccountsRepository blockedAccountsRepository;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    private MailService mailService;

//    @Autowired
//    private SmsService smsService;

    public void login(LoginModel loginModel) throws AuthorizationException {
        User user = userRepository.findByEmail(loginModel.getEmail());
        UserModel userModel = userManagementService.getUserById(user.getId());
        BlockedAccounts blockedUser=blockedAccountsRepository.findByUsername(userModel.getUsername());
        if(user == null || userModel==null){
            throw new AuthorizationException("Invalid credentials");
        }
                if(!(user.getPassword().equals(loginModel.getPassword()))) {
                    //block account for 60 min if the number of trials is 3
                    if (blockedUser != null) {
                        //increase the number of trials
                        if (blockedUser.getNumberOfAttempts() < 3) {
                            blockedAccountsRepository.updateNumberOfAttempts(blockedUser.getUsername());
                            blockedAccountsRepository.updateEndDate(blockedUser.getUsername());
                        }
                        if (blockedUser.getNumberOfAttempts() == 3) {
                            mailService.send(user.getEmail(), "BLOCKED ACCOUNT", "Your account is blocked until" + blockedUser.getEndDate().toString());
                        }
                    }
                    if (blockedUser == null) {
                        blockedUser = new BlockedAccounts();
                        blockedUser.setId(532);
                        //insert user into blocked accounts
                        blockedUser.setUsername(user.getUsername());
                        blockedUser.setEndDate(new Date());
                        blockedUser.setNumberOfAttempts(1);
                        blockedAccountsRepository.save(blockedUser);
                    }
                }



        //if account is blocked
        if(blockedAccountsRepository.findByUsername(user.getUsername())!=null){
                    //e blocat
            //verific data
            if(blockedAccountsRepository.findByUsername(user.getUsername()).getEndDate().compareTo(new Date())>0){
                //sterg intrarea din blocked user
                blockedAccountsRepository.delete(blockedAccountsRepository.findByUsername(user.getUsername()));
            }
            else{
                throw new AuthorizationException("This user is blocked");
            }
        }
             SessionModel sessionModel = new SessionModel(user.getId(),user.getFirstName(),user.getLastName(),user.getEmail());
            sessionService.setLoggedUser(sessionModel);


   }

    public UserModel getLoggedUser() {
        return userManagementService.getUserById(sessionService.getSessionModel().getUserId());
    }

    public List<PostModel> getMyPosts() throws AuthorizationException {
        //get posts plus comments plus likes
        Integer userId = sessionService.getSessionModel().getUserId();
        if(userId==null){
            throw new AuthorizationException("You are not authorized");
        }
        List<PostModel> myPosts = postRepository.getPostsByUserId(userId);
        for (PostModel p:myPosts) {
            p.setComments(commentRepository.getCommentsByPostId(p.getId()));
            p.setLikes(likeRepository.getLikesByPostId(p.getId()));
        }
        return myPosts;
    }


    public List<PostModel> getPaginatedPosts(Integer pageNumber, Integer pageSize) throws ValueNotFoundException {
        Integer userId = sessionService.getSessionModel().getUserId();
        List<PostModel> myPosts = postRepository.getPostsByUserId(userId);
        List<PostModel> paginatedPosts=new ArrayList<>();

        int index=pageNumber*pageSize;
        if(myPosts.get(index)!=null)
        {
            do{
                paginatedPosts.add(myPosts.get(index));
                index++;
            }while(index<myPosts.size() && index<(pageNumber+1)*pageSize);
        }
        if(paginatedPosts.size()==0){
            throw new ValueNotFoundException("No posts found");
        }
        return paginatedPosts;
    }

    public List<PostModel> getPostsByUserId(Integer userId) throws InvalidArgumentsException, ValueNotFoundException {
        if(userId==null)
        {
            throw new InvalidArgumentsException("Invalid user id");
        }
        User user = userRepository.findUserById(userId);
        if(user==null){
            throw new ValueNotFoundException("There is no such user");
        }


        //these are all posts
        List<PostModel> userPosts = postRepository.getPostsByUserId(userId);
        //apply filter
        //user is in friend list of loginuser
        //look in Friends for a match btw userId and loginID
        Friend f1 =friendRepository.findByUserIdAndFriendId(userId,sessionService.getSessionModel().getUserId());
        Friend f2 = friendRepository.findByUserIdAndFriendId(sessionService.getSessionModel().getUserId(),userId);
        if(f1==null && f2==null){

            //not friends at all
            List<PostModel> toRemove = new ArrayList<>();
            for (PostModel p : userPosts) {
                if (p.getStringVisibility().equals("PRIVATE")) {
                    toRemove.add(p);
                }
            }
            userPosts.removeAll(toRemove);

        }
        if(blockRepository.getBlockByIds(sessionService.getSessionModel().getUserId(),userId)!=null ||
                blockRepository.getBlockByIds(userId,sessionService.getSessionModel().getUserId())!=null){
            //logged user has userId on block || userId has logged user on block
            userPosts.clear();
        }

        return userPosts;
    }
}
