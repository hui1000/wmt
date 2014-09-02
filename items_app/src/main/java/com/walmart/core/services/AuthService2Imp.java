package com.walmart.core.services;

import com.walmart.core.domain.User;
import com.walmart.core.repository.UsersRepository;

import java.util.*;

public class AuthService2Imp implements AuthService2 {

    private final UsersRepository usersRepository;

    public AuthService2Imp(final UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean isAuthTokenOk(String authToken) {
        System.out.println("###### authToken=" + authToken);
        if (authToken == null || authToken.length() < 38){
            return false ;
        }
        
        String userName = authToken.substring(37);
        System.out.println("######## userName=" + userName); 
        User u = usersRepository.findByUserName(userName);
        if (u == null || u.getAuthToken() == null || 
            u.getAuthTokenCreatedAt() == null || !(u.getAuthToken().equals(authToken))  ||
            (new Date().getTime() - u.getAuthTokenCreatedAt().getTime()) > 1 * 60 *60 * 1000
           )
        {
            return false;
        } else {
            return true;
        }
    }

}
