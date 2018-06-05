package com.practica.demo.db.entity;

import javafx.geometry.Pos;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "user", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name="birth_date")
    private Date birth_date;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @OneToMany(mappedBy = "user")
    private List<Media> uploads;

    @OneToMany(mappedBy = "user")
    private List<Block> blocks;

    @OneToMany(mappedBy = "user")
    private List<Friend> friends;

    public User() {
    }

    public User( String username, String firstName, String lastName, String password, String email, Date birth_date) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.birth_date=birth_date;
    }

    public User(User user)
    {
        this.id=user.getId();
        this.username=user.getUsername();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.password=user.getPassword();
        this.email=user.getEmail();
        this.birth_date=user.getBirth_date();

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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public List<Media> getUploads() {
        return uploads;
    }

    public void setUploads(List<Media> uploads) {
        this.uploads = uploads;
    }
}
