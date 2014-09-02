package com.walmart.core.services;

import com.walmart.rest.domain.LoginInfo;
import com.walmart.rest.domain.AuthResult;

import java.util.*;

public interface AuthService {
    public AuthResult getAuthResult(LoginInfo loginInfo); 

}
