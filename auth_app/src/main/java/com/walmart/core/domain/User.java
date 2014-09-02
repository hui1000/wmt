package com.walmart.core.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.beans.BeanUtils;
import java.util.*;
import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue()
    private Long id;

    private String userName;
    private String password;

    private String authToken ;
    private Date authTokenCreatedAt;

    public User() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
    public String getAuthToken() {
        return authToken;
    }

    public void setAuthTokenCreatedAt(Date authTokenCreatedAt) {
        this.authTokenCreatedAt = authTokenCreatedAt;
    }
    public Date getAuthTokenCreatedAt() {
        return authTokenCreatedAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
}
