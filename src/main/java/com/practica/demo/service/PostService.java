package com.practica.demo.service;

import com.practica.demo.db.dao.*;
import com.practica.demo.db.entity.Like;
import com.practica.demo.db.entity.Post;
import com.practica.demo.db.entity.PostConfirmation;
import com.practica.demo.model.CommentModel;
import com.practica.demo.model.LikeModel;
import com.practica.demo.model.NewPostModel;
import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class PostService {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostConfirmationRepository postConfirmationRepository;

    @Autowired
    private LikeRepository likeRepository;


    @Value("${max.post.attempts}")
    private int numberOfAttempts;
    @Value("${max.postMessage.length}")
    private int maxLength;
    public void addNewPost(NewPostModel newPostModel) throws InvalidArgumentsException,ValueNotFoundException ,AuthorizationException{
        //validations
        if (newPostModel.getPostMessage().length() > maxLength) {
            throw new InvalidArgumentsException("The post is too large");
        }
        if (!(newPostModel.getVisibility().equals("PUBLIC") || newPostModel.getVisibility().equals("PRIVATE"))) {
            throw new InvalidArgumentsException("Invalid post status");
        }
        if (newPostModel.getVisibility() == null) {
            throw new InvalidArgumentsException("Post status cannot be null");
        }
        if (sessionService.getSessionModel().getUserId() == null) {
            throw new InvalidArgumentsException("Invalid path");
        }
        if (userRepository.findUserById(sessionService.getSessionModel().getUserId()) == null || sessionService.getSessionModel().getUserId() == null) {
            throw new ValueNotFoundException("No user found");
        }

        //check if it hasn't inserted 20 posts in postConfirmation
        //if found in repository
        PostConfirmation postConf = postConfirmationRepository.findUserById(sessionService.getSessionModel().getUserId());
        if (postConf != null) {
            //IS found in repository
            if (postConfirmationRepository.getTrialsByUserId(sessionService.getSessionModel().getUserId()) < numberOfAttempts) {
                //increase it if the date is smaller than 24 h
                try {
                    if (getTimeDiff(postConf.getDateOfFirstPost(), new Date()) < 24) {
                        //update in database postConfRep
                        postConfirmationRepository.updateUserById(sessionService.getSessionModel().getUserId());
                    } else {
                        postConfirmationRepository.updateDateAndResetUserById(sessionService.getSessionModel().getUserId(), new Date());
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
            //if =20
            if (postConfirmationRepository.getTrialsByUserId(sessionService.getSessionModel().getUserId()) == numberOfAttempts) {
                //check h
                if (getTimeDiff(postConf.getDateOfFirstPost(), new Date()) < 24) {
                    throw new AuthorizationException("You have reached the total number of posts for today. Please, come back tomorrow. Thank you!");
                }
                if (getTimeDiff(postConf.getDateOfFirstPost(), new Date()) >= 24) {
                    try {
                        postConfirmationRepository.updateDateAndResetUserById(sessionService.getSessionModel().getUserId(), new Date());
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }

        }else {
            //not found in repository
            //add it
            //postConf e null
            postConf=new PostConfirmation(sessionService.getSessionModel().getUserId(),new Date(),1);
            postConfirmationRepository.save(postConf);
        }


        //insert a postModel variable into Post table after all validations

            Post postModel = new Post(newPostModel.getPostMessage(), new Date(), newPostModel.getVisibility(), userRepository.findUserById(sessionService.getSessionModel().getUserId()));
            postRepository.save(postModel);
    }

    public int getTimeDiff(Date dateOne, Date dateTwo) {
        Long diff ;
        long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
        diff =TimeUnit.MILLISECONDS.toHours(timeDiff);
        return (int)(long)diff;
    }


    public void updatePost(Integer postId, NewPostModel postModel) throws ValueNotFoundException,InvalidArgumentsException{

        //bring the post from the postRepository
        Post post=postRepository.getPostByPostId(postId);
        if(post==null || postModel==null)
        {
            throw new ValueNotFoundException("This post does not exist");
        }
        if (postModel.getPostMessage().length() > maxLength) {
            throw new InvalidArgumentsException("The post is too large");
        }
        if (!(postModel.getVisibility().equals("PUBLIC") || postModel.getVisibility().equals("PRIVATE"))) {
            throw new InvalidArgumentsException("Invalid post status");
        }
        if (postModel.getVisibility() == null) {
            throw new InvalidArgumentsException("Post status cannot be null");
        }
        if (sessionService.getSessionModel().getUserId() == null) {
            throw new InvalidArgumentsException("Invalid path");
        }
        if (userRepository.findUserById(sessionService.getSessionModel().getUserId()) == null || sessionService.getSessionModel().getUserId() == null) {
            throw new ValueNotFoundException("No user found");
        }

        //update post in database
        postRepository.updatePost(postId,postModel.getPostMessage(),postModel.getVisibility());
    }


    public void deletePost(Integer postId) throws InvalidArgumentsException, ValueNotFoundException,AuthorizationException{
        if(postId==null)
        {
            throw new InvalidArgumentsException("Invalid post id");
        }
        Post post=postRepository.getPostByPostId(postId);
        if(post==null){
            throw new ValueNotFoundException("There is no post with postId = "+postId);
        }
        if(post.getUser().getId().equals(sessionService.getSessionModel().getUserId()))
        {
            throw new AuthorizationException("You are not authorized for deleting this post");
        }

        postRepository.delete(post);

    }

    public void addLike(Integer postId) throws InvalidArgumentsException, ValueNotFoundException {
        Post post=postRepository.getPostByPostId(postId);
        if(postId==null)
        {
            throw new InvalidArgumentsException("Invalid post id");
        }
        if(post==null)
        {
            throw new ValueNotFoundException("Post not found");
        }

        Like like=likeRepository.getLikeFromPost(sessionService.getSessionModel().getUserId(), postId);
        if(like==null)
        {

           like = new Like(sessionService.getSessionModel().getUserId(),post);
            likeRepository.save(like);
        }
        else
        {
            likeRepository.delete(like);
        }
    }

    public List<CommentModel> getCommentsByPostId(Integer postId) throws InvalidArgumentsException {
        if(postId==null)
        {
            throw new InvalidArgumentsException("Invalid post id");
        }
        List<CommentModel> postComments = new ArrayList<>();
        postComments = commentRepository.getCommentsByPostId(postId);
        return postComments;
    }
}

