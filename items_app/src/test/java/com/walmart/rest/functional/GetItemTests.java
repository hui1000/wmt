package com.walmart.rest.functional;

import com.walmart.rest.domain.RestItem;
import com.walmart.rest.domain.RestItemsInfo;
import com.walmart.rest.domain.RestItemsFixture;
import com.walmart.rest.domain.AuthResult;
import com.walmart.core.domain.Store;

import org.junit.Test;
import org.springframework.http.*;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.walmart.rest.domain.RestItem;
import com.walmart.rest.functional.AuthUtil;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;
import static junit.framework.TestCase.assertTrue;

public class GetItemTests {

    private String testUrl = "http://localhost:7340/services/items";

    @Test
    public void testGetItemById() {
        HttpHeaders headers = AuthUtil.getAuthTokenHeaders();
        RestTemplate template = new RestTemplate();

        HttpEntity<String> requestEntity = new HttpEntity<String>(
                            RestItemsFixture.newItemJSON(),headers);
        ResponseEntity<RestItemsInfo> entity = template.postForEntity(
                                 testUrl, requestEntity, RestItemsInfo.class);
        RestItemsInfo itemsInfo = entity.getBody();
        List<RestItem> items = itemsInfo.getItems();

        Long itemId = null;
        for (RestItem item: items) {
            System.out.println("The Item ID is " + item.getId());
            assertNotNull(item.getId());
            itemId = item.getId();
        }

        // now let's do a get on this valid item.
        requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<RestItem> responseEntity = template.exchange(testUrl+"/{id}", 
                    HttpMethod.GET, requestEntity, RestItem.class, itemId);
        RestItem getItem = responseEntity.getBody(); 

        System.out.println("The Item ID is " + getItem.getId());
        assertEquals(itemId, getItem.getId());
        
        System.out.println("The Item description is " + getItem.getDescription());
        assertEquals(getItem.getDescription(), RestItemsFixture.NEW_ITEM_DESC);
        assertEquals(getItem.getLocale(), RestItemsFixture.NEW_ITEM_LOCALE);
        assertEquals(getItem.getPrice(), RestItemsFixture.NEW_ITEM_PRICE);
        assertEquals(getItem.getStoreIds(), RestItemsFixture.NEW_ITEM_STOREIDS);
    }
        
    @Test
    public void testGetNonExistingItem() {
        HttpHeaders headers = AuthUtil.getAuthTokenHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        RestTemplate template = new RestTemplate();
        Long itemId = Long.valueOf(1000);
        // pass the token in the header 
        try {
            ResponseEntity<RestItem> getItem = template.exchange(testUrl+"/{id}", 
                    HttpMethod.GET, entity, RestItem.class, itemId);
        } catch (HttpClientErrorException ex) {
            System.out.println("=== http code = " + ex.getStatusCode());
            assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        }
    }

    @Test
    public void testMissingAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        RestTemplate template = new RestTemplate();
        Long itemId = Long.valueOf(10);
        // pass the token in the header 
        try {
            ResponseEntity<RestItem> getItem = template.exchange(testUrl+"/{id}", 
                    HttpMethod.GET, entity, RestItem.class, itemId);
        } catch (HttpClientErrorException ex) {
            System.out.println("=== http code = " + ex.getStatusCode());
            assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        }
    }

    @Test
    public void testIncorrectAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        // put an invalid token here. 
        headers.set("X-Auth-Token", "34f5a7cc-a692-437b-a4bf-8c7b73ddf60e|wmt_user1");
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        RestTemplate template = new RestTemplate();
        Long itemId = Long.valueOf(10);
        // pass the token in the header 
        try {
            ResponseEntity<RestItem> getItem = template.exchange(testUrl+"/{id}", 
                    HttpMethod.GET, entity, RestItem.class, itemId);
        } catch (HttpClientErrorException ex) {
            System.out.println("=== http code = " + ex.getStatusCode());
            assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        }
    }


}


