package com.fourreau.itwapp.service.impl;

import com.fourreau.itwapp.model.User;
import com.fourreau.itwapp.service.AuthenticationService;

import javax.inject.Inject;

import io.itwapp.Itwapp;

/**
 * Created by Pierre on 15/04/2015.
 */
public class AuthenticationServiceImpl implements AuthenticationService{

    @Inject
    public AuthenticationServiceImpl() {}

    @Override
    public User login(String email, String password) {
        User user = new User();




        //        user = Interview.login(email, password);

        return user;
    }

}
