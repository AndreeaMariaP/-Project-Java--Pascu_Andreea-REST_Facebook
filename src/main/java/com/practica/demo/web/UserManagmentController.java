package com.practica.demo.web;

import com.practica.demo.db.entity.User;
import com.practica.demo.db.entity.UserConfirmation;
import com.practica.demo.model.ConfirmEmailModel;
import com.practica.demo.model.UserModel;
import com.practica.demo.model.exception.AuthorizationException;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueAlreadyInUseException;
import com.practica.demo.model.exception.ValueNotFoundException;
import com.practica.demo.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class UserManagmentController {

    @Autowired
    private UserManagementService userManagementService;

    @PostMapping("/signup")
    public void addUser(@RequestBody User user) throws ValueAlreadyInUseException,InvalidArgumentsException {
        userManagementService.addUser(user);
    }

    @PutMapping("/confirm-email")
    public void confirmEmail(@RequestBody @Valid ConfirmEmailModel user ) throws  ValueNotFoundException,ValueAlreadyInUseException,InvalidArgumentsException{
        userManagementService.confirmEmail(user);
    }

    @PutMapping("/resend-email")
    public void resendEmail(@RequestBody String email ) throws  InvalidArgumentsException, ValueNotFoundException, ValueAlreadyInUseException {
        userManagementService.resendEmail(email);
    }

    @GetMapping
    public List<UserModel> getAllUsers(){
        return userManagementService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserModel getUserById(@PathVariable("userId") Integer userId) {
        return userManagementService.getUserById(userId);
    }

}
