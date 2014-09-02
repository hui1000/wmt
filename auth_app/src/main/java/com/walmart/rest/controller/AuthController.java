package com.walmart.rest.controller;


import com.walmart.rest.domain.LoginInfo;
import com.walmart.rest.domain.AuthResult;
import com.walmart.core.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/services/security/authToken")
public class AuthController {

    @Autowired
    private AuthService authService;
    private static Logger LOG = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AuthResult> getAuthResult(@RequestBody LoginInfo loginInfo, UriComponentsBuilder builder) {
        System.out.println("loginInfo=" + loginInfo.getUserName()) ;
        AuthResult authResult = authService.getAuthResult(loginInfo);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<AuthResult>(authResult, headers, HttpStatus.OK);
    }

}
