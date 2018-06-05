package com.practica.demo.web;

import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.LoginModel;
import com.practica.demo.model.PostModel;
import com.practica.demo.model.UserModel;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import com.practica.demo.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class MyUserController {

    @Autowired
    private MyUserService myUserService;

    @PostMapping("/login")
    public void login(@RequestBody LoginModel loginModel) throws AuthorizationException {
        myUserService.login(loginModel);
    }

    @GetMapping("/my-user")
    public UserModel getLoggedUser(){

        UserModel userModel = myUserService.getLoggedUser();
            return userModel;

    }

    @GetMapping("/my-user/posts")
    public List<PostModel> getMyPosts() throws AuthorizationException {
        List<PostModel> list=null;
            list= myUserService.getMyPosts();
        return list;
    }

    @GetMapping("/my-user/paginated-posts")
    public List<PostModel> getPaginatedPosts(@RequestParam("pageNumber")Integer pageNumber, @RequestParam("pageSize")Integer pageSize)throws ValueNotFoundException {
        return myUserService.getPaginatedPosts(pageNumber,pageSize);
    }

    @GetMapping("/users/{userId}/posts")
    public List<PostModel> getPostsByUserId(@PathVariable("userId") Integer userId) throws ValueNotFoundException,InvalidArgumentsException {
            return myUserService.getPostsByUserId(userId);
    }

}
