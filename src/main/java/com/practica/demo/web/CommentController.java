package com.practica.demo.web;

import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import com.practica.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Path;

@RestController
@RequestMapping("/rest")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public void addNewComment(@RequestBody @Valid String commentMessage, @PathVariable("postId")Integer postId) throws ValueNotFoundException, InvalidArgumentsException {
        commentService.addNewComment(postId,commentMessage);
    }

    @PutMapping("/post/{postId}/comments/{commentId}")
    public void editComment(@PathVariable("postId")Integer postId, @PathVariable("commentId")Integer commentId,
                            @RequestBody @Valid String commentMessage) throws InvalidArgumentsException, AuthorizationException, ValueNotFoundException {
        commentService.editExistingComment(commentId,postId,commentMessage);
    }

    @DeleteMapping("/post/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable("postId")Integer postId, @PathVariable("commentId")Integer commentId) throws InvalidArgumentsException, AuthorizationException, ValueNotFoundException {
        commentService.deleteComment(commentId,postId);
    }
}
