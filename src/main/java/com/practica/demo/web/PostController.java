package com.practica.demo.web;

import com.practica.demo.model.CommentModel;
import com.practica.demo.model.NewPostModel;
import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import com.practica.demo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping("/my-user/posts")
    public void addNewPost(@RequestBody NewPostModel postModel) throws InvalidArgumentsException, AuthorizationException, ValueNotFoundException {
            postService.addNewPost(postModel);

    }

    @PutMapping("/my-user/posts/{postId}")
    public void updatePost(@RequestBody NewPostModel postModel, @PathVariable("postId") Integer postId) throws ValueNotFoundException, InvalidArgumentsException {

           postService.updatePost(postId, postModel);

    }


    @DeleteMapping("/my-user/posts/{postId}")
    public void deletePost(@PathVariable("postId")Integer postId) throws InvalidArgumentsException, AuthorizationException, ValueNotFoundException {
        postService.deletePost(postId);
    }

    @PostMapping("/posts/{postId}/likes")
    public void addLike(@PathVariable("postId") Integer postId) throws ValueNotFoundException, InvalidArgumentsException {

            postService.addLike(postId);
    }

    @GetMapping("/posts/{postId}/comments")
    @ResponseBody
    public ResponseEntity<List<CommentModel>> getCommentsByPostId(@PathVariable("postId") Integer postId){

        List<CommentModel> commentModelList=null;
        try{
            commentModelList = postService.getCommentsByPostId(postId);
        }
        catch(InvalidArgumentsException ex){


            return new ResponseEntity(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(commentModelList, HttpStatus.OK);
    }



}
