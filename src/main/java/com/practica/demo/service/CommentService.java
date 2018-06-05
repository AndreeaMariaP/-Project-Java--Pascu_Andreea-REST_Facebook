package com.practica.demo.service;

import com.practica.demo.db.dao.CommentRepository;
import com.practica.demo.db.dao.PostRepository;
import com.practica.demo.db.dao.UserRepository;
import com.practica.demo.db.entity.Comment;
import com.practica.demo.db.entity.Post;
import com.practica.demo.db.entity.User;
import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private SessionService sessionService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Value("${max.comment.length}")
    private int maxCommentLength;
    public void addNewComment(Integer postId, String commentMessage) throws InvalidArgumentsException, ValueNotFoundException {

        if(postId==null) {
            throw new InvalidArgumentsException("Invalid post id");
        }
        if(commentMessage.length()>maxCommentLength) {
            throw new InvalidArgumentsException("Comment is too large");
        }
        Post post=postRepository.getPostByPostId(postId);
        if(post==null)
        {
            throw new ValueNotFoundException("Post not found");
        }
        Comment comment=new Comment(sessionService.getSessionModel().getUserId(),commentMessage,post);
        commentRepository.save(comment);
    }

    public void editExistingComment(Integer commentId, Integer postId, String commentMessage) throws InvalidArgumentsException, ValueNotFoundException, AuthorizationException {
        if(commentId==null || postId==null)
        {
            throw new InvalidArgumentsException("Invalid comment id or post id");
        }
        Comment comment= commentRepository.findCommentByCommentId(commentId);
        if(comment==null)
        {
            throw new ValueNotFoundException("No comment found");
        }
        //validate comment for ownership
        if(comment.getUserId().equals(sessionService.getSessionModel().getUserId()))
        {
            throw new AuthorizationException("You are not authorized to edit this comment");
        }
        if(commentMessage.length()>maxCommentLength)
        {
            throw new InvalidArgumentsException("Comment is too large");
        }
        commentRepository.updateCommentMessage(commentId, commentMessage);

    }

    public void deleteComment(Integer commentId, Integer postId) throws InvalidArgumentsException, ValueNotFoundException, AuthorizationException {
        if(commentId==null || postId==null)
        {
            throw new InvalidArgumentsException("Invalid comment id or post id");
        }
        Comment comment= commentRepository.findCommentByCommentId(commentId);
        if(comment==null)
        {
            throw new ValueNotFoundException("No comment found");
        }
        //validate comment for ownership
        Post post=postRepository.getPostByPostId(postId);
        if(post==null){
            throw new ValueNotFoundException("Post not found");
        }
            if( !(post.getUser().getId().equals(sessionService.getSessionModel().getUserId() ))|| !(comment.getUserId().equals(sessionService.getSessionModel().getUserId()))){
                throw new AuthorizationException("You are not authorized to edit this comment");
            }

        commentRepository.delete(comment);
    }
}
