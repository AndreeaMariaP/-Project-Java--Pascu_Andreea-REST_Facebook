package com.practica.demo.service;

import com.practica.demo.db.dao.FriendRepository;
import com.practica.demo.db.dao.UserRepository;
import com.practica.demo.model.FriendModel;
import com.practica.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class NotificationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    MailService mailService;
    public void sendEmailNotifications(){
        Date date = new Date();
        List<UserModel> userList = userRepository.getAllUsers();
        for (UserModel userModel:userList) {
            if(userModel.getBirth_date().getDay()==date.getDay() && userModel.getBirth_date().getMonth()==date.getMonth()){
                //send email to all user friends
                //create a list with all user friends
                mailService.send(userModel.getEmail(),"Happy birthday!","Happy birthday "+userModel.getFirstName()+" "+userModel.getLastName()+"!");
                List<Integer> friendIds = friendRepository.getAcceptedFriendRequests(userModel.getId());
                for (Integer i:friendIds) {
                   mailService.send( userRepository.findUserById(i).getEmail(),"Birthday notification", "Wish "+ userModel.getUsername()+" happy birthday today!");
                }
            }
        }
    }
}
