package com.walmart.core.services;

import com.walmart.rest.domain.LoginInfo;
import com.walmart.rest.domain.AuthResult;
import com.walmart.core.domain.User;
import com.walmart.core.repository.UsersRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

public class AuthServiceImp implements AuthService {

    private final UsersRepository usersRepository;
    public AuthServiceImp(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public AuthResult getAuthResult(LoginInfo loginInfo) {

        AuthResult authResult = new AuthResult(); 
        String userName = null;
        String password = null;
        if ((userName = loginInfo.getUserName()) == null || userName == "") {
            authResult.setStatus(AuthResult.STATUS_NO_USERNAME);
        }else if ((password = loginInfo.getPassword()) == null || password == "") {
            authResult.setStatus(AuthResult.STATUS_NO_PASSWORD);
        }else { 
            User u = usersRepository.findByUserName(userName);
            String inputCryptedPassword = DigestUtils.sha1Hex(password);
            if (u == null){
                authResult.setStatus(AuthResult.STATUS_USER_NOT_FOUND);
            }else if (inputCryptedPassword.equals(u.getPassword())){
                authResult.setStatus(AuthResult.STATUS_OK);
                if (u.getAuthToken() == null || 
                    u.getAuthTokenCreatedAt() == null || 
                    ((new Date()).getTime() - u.getAuthTokenCreatedAt().getTime()) > 60 * 60 * 1000  // 1h
                   )
                {
                    u.setAuthTokenCreatedAt(new Date());
                    String cleanToken = UUID.randomUUID().toString() + "|" + u.getUserName() ;

                    String authToken = cleanToken; 

                    u.setAuthToken(authToken); 

                    usersRepository.save(u);
                }
                authResult.setAuthToken(u.getAuthToken());
            }else{
                authResult.setStatus(AuthResult.STATUS_WRONG_PASSWORD);
            }
            System.out.println("inputCryptedPassword = " + inputCryptedPassword);
        }

        return authResult;
    }

}
