package com.walmart.rest.functional; 

import org.springframework.web.client.RestTemplate;
import com.walmart.rest.domain.AuthResult;
import org.springframework.http.*;
import java.util.Arrays;

public class AuthUtil {

   public static HttpHeaders getAuthTokenHeaders() {
        String authUrl = "http://localhost:8003/services/security/authToken";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        RestTemplate template = new RestTemplate();
        String body = "{\"userName\": \"wmt_user1\", \"password\": \"wmt_password1\"}";
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);

        ResponseEntity<AuthResult> entity = template.postForEntity(
                                authUrl, requestEntity, AuthResult.class);

        AuthResult authResult = entity.getBody();
        String authToken = authResult.getAuthToken();
        System.out.println("--- authToken = " + authResult.getAuthToken());

        headers.set("X-Auth-Token", authToken);
        return headers;
    }
}
