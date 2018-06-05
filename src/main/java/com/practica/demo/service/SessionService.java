package com.practica.demo.service;

import com.practica.demo.db.dao.UserRepository;
import com.practica.demo.model.SessionModel;
import com.practica.demo.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS,value = "session")
public class SessionService implements Serializable {

    private SessionModel sessionModel;

    public SessionModel getSessionModel() {
        return sessionModel;
    }

    public void setLoggedUser(SessionModel sessionModel) {
        this.sessionModel = sessionModel;
    }

    public Boolean isLoggedIn(){
        return sessionModel.getUserId() != null;
    }



}