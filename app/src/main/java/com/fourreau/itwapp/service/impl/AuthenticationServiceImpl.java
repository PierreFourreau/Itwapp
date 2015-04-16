package com.fourreau.itwapp.service.impl;

import com.fourreau.itwapp.model.User;
import com.fourreau.itwapp.service.AuthenticationService;

/**
 * Created by Pierre on 15/04/2015.
 */
public class AuthenticationServiceImpl implements AuthenticationService{

    @Override
    public User login(String email, String password) {
        User user = new User();

//        Itwapp.apiKey = "3bf8bce4b8b0ac18a0a4669ec58ed03f";
//        Itwapp.secretKey = "450425436db428e7d04288d592c1e771c82f9747";


//        user = Interview.login(email, password);
        return user;
    }

}
