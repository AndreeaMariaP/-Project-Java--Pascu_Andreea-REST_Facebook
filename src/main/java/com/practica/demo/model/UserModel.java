package com.practica.demo.model;

import com.practica.demo.db.entity.Post;

import java.util.Date;
import java.util.List;

public class UserModel {

    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Date birth_date;
    private List<PostModel> postModels;

    public UserModel(){

    }
    public UserModel(Integer id, String email, Date birth_date) {
        this.id = id;
        this.email = email;
        this.birth_date = birth_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public List<PostModel> getPostModels() {
        return postModels;
    }

    public void setPostModels(List<PostModel> postModels) {
        this.postModels = postModels;
    }

    public void addPostModelObjectToList(PostModel postModel){
        this.postModels.add(postModel);
    }

}
