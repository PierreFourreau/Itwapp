package com.fourreau.itwapp.service.impl;

import com.fourreau.itwapp.service.AuthenticationService;

import javax.inject.Inject;

import io.itwapp.Itwapp;
import io.itwapp.exception.APIException;
import io.itwapp.exception.UnauthorizedException;
import io.itwapp.rest.AccessToken;
import timber.log.Timber;

/**
 * Created by Pierre on 15/04/2015.
 */
public class AuthenticationServiceImpl implements AuthenticationService{

    @Inject
    public AuthenticationServiceImpl() {}

    @Override
    public Boolean login(String email, String password) {
//        User user = new User();

        try {
            AccessToken accessToken = Itwapp.Authenticate(email, password);
            Itwapp.apiKey = accessToken.getApiKey();
            Itwapp.secretKey = accessToken.getSecretKey();
            return true;
        }catch(UnauthorizedException | APIException e)   {
            Timber.e("AuthenticationService:login:"+e.toString());
            return false;
        }

        //        user = Interview.login(email, password);

//        return user;
    }

}
