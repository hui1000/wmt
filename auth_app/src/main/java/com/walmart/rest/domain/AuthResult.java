package com.walmart.rest.domain;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.UUID;

@XmlRootElement
public class AuthResult implements Serializable {
    final public static String STATUS_NO_USERNAME= "NO_USERNAME" ;
    final public static String STATUS_NO_PASSWORD = "NO_PASSWORD" ;
    final public static String STATUS_USER_NOT_FOUND = "USER_NOT_FOUND" ;
    final public static String STATUS_WRONG_PASSWORD = "WRONG_PASSWORD" ;

    final public static String STATUS_OK= "OK" ;

    private String status;
    private String authToken;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
