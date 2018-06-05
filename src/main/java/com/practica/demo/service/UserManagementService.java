package com.practica.demo.service;

import com.practica.demo.db.dao.UserConfirmationRepository;
import com.practica.demo.db.dao.UserRepository;
import com.practica.demo.db.entity.Post;
import com.practica.demo.db.entity.User;
import com.practica.demo.db.entity.UserConfirmation;
import com.practica.demo.model.ConfirmEmailModel;
import com.practica.demo.model.PostModel;
import com.practica.demo.model.UserModel;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueAlreadyInUseException;
import com.practica.demo.model.exception.ValueNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserManagementService {

    private static String passwordValidation="[0-9a-zA-Z]{8,}$";
    private static String emailValidation="^[a-zA-Z0-9_.]+@[a-zA-Z.]+?\\.[a-zA-Z]{2,3}$";
    private static String confirmationCodeValidation="^[0-9]{10,10}$";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserConfirmationRepository userConfirmationRepository;

    @Autowired
    private MailService mailService;

    public void addUser(User user) throws InvalidArgumentsException,ValueAlreadyInUseException {
        if(     user.getEmail()==null ||
                user.getBirth_date()==null ||
                user.getUsername()==null ||
                user.getFirstName()==null ||
                user.getLastName()==null ||
                user.getPassword()==null)
        {
            throw new InvalidArgumentsException("Please complete all fields");
        }
        if(!(user.getLastName().matches(new String("[A-Za-z]+$") ))){
            throw new InvalidArgumentsException("Lastname must contain only characters");
        }
        if(!(user.getFirstName().matches(new String("[A-Za-z]+$")))){
            throw new InvalidArgumentsException("Firstname must contain only characters");
        }
        if(!(user.getPassword().matches(this.passwordValidation))){
            throw new InvalidArgumentsException("Password too short");
        }
        //caut user cu provided username
        User dbUser = userRepository.findByUsername(user.getUsername());
        if(dbUser != null){
            throw new ValueAlreadyInUseException("Username already in use");
        }
        //caut user cu provided email
        if(user.getEmail().matches(this.emailValidation)){
            dbUser=userRepository.findByEmail(user.getEmail());
            if(dbUser!=null)
            {
                throw new ValueAlreadyInUseException("Email already in use");
            }
        }
        else{
            throw new InvalidArgumentsException("Invalid email");
        }

        UserConfirmation ucUser=userConfirmationRepository.findByEmail("cristi.rosu4@gmail.com");


        if(ucUser!=null)
        {
            // update the code and send email
            userConfirmationRepository.updateConfirmationCode(ucUser.getId(),UserConfirmation.generateConfirmationCode());

            mailService.send(ucUser.getEmail(),"CONFIRMATION CODE",ucUser.getConfirmationCode());
        }
        else{
            //else set code and send email
            UserConfirmation userConfirmation=new UserConfirmation(user.getId(),user.getUsername(),user.getFirstName(),user.getLastName(),user.getPassword(),user.getEmail(),user.getBirth_date(),UserConfirmation.generateConfirmationCode(),0);
            userConfirmationRepository.save(userConfirmation) ;
            mailService.send(userConfirmation.getEmail(),"CONFIRMATION CODE",userConfirmation.getConfirmationCode());
        }
    }

    public List<UserModel> getAllUsers() {
        List<User> users= userRepository.findAll();
        List<UserModel> userModels = new ArrayList<>();
        for(User u : users){
            UserModel userModel = new UserModel();
            BeanUtils.copyProperties(u,userModel);
            userModels.add(userModel);
        }
        return userModels;
    }

    public UserModel getUserById(Integer userId) {
        User user = userRepository.findUserById(userId);
        UserModel userModel = new UserModel();
        if(user != null){
            BeanUtils.copyProperties(user, userModel);
            List<Post> userPosts = user.getPosts();
            List<PostModel> postModelList = new LinkedList<>();
            for(Post userPost : userPosts){
                //get posts to postmodel
                PostModel postModel = new PostModel(userPost.getId(),userPost.getMessage(), userPost.getCreationDate(), userPost.getVisibility(), userPost.getUser().getId());
                postModelList.add(postModel);
            }
            userModel.setPostModels(postModelList);
        }
        return userModel;
    }

    public void confirmEmail(ConfirmEmailModel confirmUser) throws InvalidArgumentsException,ValueAlreadyInUseException,ValueNotFoundException{
        UserConfirmation temp=null;

        UserConfirmation user=userConfirmationRepository.findByEmail(confirmUser.getEmail());
        if(user==null){
            throw new ValueNotFoundException("User not found");
        }
        if(!(user.getEmail().matches(emailValidation)))
        {
            throw new InvalidArgumentsException("Invalid email");
        }
        if(!(user.getConfirmationCode().matches(confirmationCodeValidation)))
        {
            throw new InvalidArgumentsException("Invalid confirmation code");
        }
        temp=userConfirmationRepository.findByEmail(user.getEmail());
        if(temp!=null)
        {
            if(user.getConfirmationCode().equals(temp.getConfirmationCode()))
            {
                //add to users
                //convert temp to user
                User u=new User(temp.getUsername(),temp.getFirstName(),temp.getLastName(),temp.getPassword(),temp.getEmail(),temp.getBirth_date());
                userRepository.save(u);
                //delete from userConf
                userConfirmationRepository.delete(temp);
            }
            else{
                throw new InvalidArgumentsException("Invalid confirmation code");
            }
        }
        else
        {
            //if it is not in userConfirmation, check in the user table
            User userTemp=new User();
            userTemp=userRepository.findByEmail(user.getEmail());
            if(userTemp!=null)
            {
                throw new ValueAlreadyInUseException("Email already confirmed.");
            }
            else{
                throw new ValueNotFoundException("Email not found");
            }

        }
    }

    @Value("${max.email.attempts}")
    private int maxEmailAttempts;
    public void resendEmail(String email) throws InvalidArgumentsException, ValueNotFoundException, ValueAlreadyInUseException {

        if(!(email.matches(emailValidation)))
        {
            throw new InvalidArgumentsException("Invalid email");
        }
        UserConfirmation uc=userConfirmationRepository.findByEmail(email);
        if(uc != null && uc.getEmailAttempts()<maxEmailAttempts)
        {
            String s=UserConfirmation.generateConfirmationCode();
            mailService.send(email,"CONFIRMATION CODE", s);
            userConfirmationRepository.updateConfirmationCode(uc.getId(),s);
            userConfirmationRepository.updateEmailAttempts(uc.getId());
        } else {
            throw new ValueNotFoundException("Email not found or you reached the maximum number of attempts");
        }
        User u=userRepository.findByEmail(email);
        if(u!=null)
        {
            throw new ValueAlreadyInUseException("Email has been already validated");
        }
    }


}