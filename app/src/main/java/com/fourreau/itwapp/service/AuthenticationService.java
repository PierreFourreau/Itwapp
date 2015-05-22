package com.fourreau.itwapp.service;

import com.fourreau.itwapp.model.User;

/**
 * Created by Pierre on 15/04/2015.
 */
public interface AuthenticationService {
    public Boolean login(String email, String password);
}
