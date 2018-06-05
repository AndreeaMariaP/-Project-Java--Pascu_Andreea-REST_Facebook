package com.practica.demo.db.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "user_confirmation", schema = "public")
public class UserConfirmation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="user_id")
    private Integer userId;

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

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @Column(name="email_attempts")
    private int emailAttempts;

    public UserConfirmation() {
    }

    public UserConfirmation(Integer userId, String username, String firstName, String lastName, String password, String email, Date birth_date, String confirmationCode, int emailAttempts) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.birth_date = birth_date;
        this.confirmationCode = confirmationCode;
        this.emailAttempts=emailAttempts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public int getEmailAttempts() {
        return emailAttempts;
    }

    public void setEmailAttempts(int emailAttempts) {
        this.emailAttempts = emailAttempts;
    }

    public static String generateConfirmationCode()
    {
        String codeGenerated="";
        String codeGenerator="0123456789";
        Random rnd=new Random();
        for(int i=0;i<10;i++)
        {
            codeGenerated+=codeGenerator.charAt(rnd.nextInt(9));
        }
        return codeGenerated;
    }
}
