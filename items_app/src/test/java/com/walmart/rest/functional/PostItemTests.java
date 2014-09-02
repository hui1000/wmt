package com.walmart.rest.functional;

import com.walmart.rest.domain.RestItem;
import com.walmart.rest.domain.RestItemsInfo;
import com.walmart.rest.domain.RestItemsFixture;
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

public class PostItemTests {

    private String testUrl = "http://localhost:7340/services/items";

    @Test
    public void testCreateOneNewItem() {
        HttpHeaders headers = AuthUtil.getAuthTokenHeaders(); 

        RestTemplate template = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<String>(
                        RestItemsFixture.newItemJSON(),headers);

        ResponseEntity<RestItemsInfo> entity = template.postForEntity(
                testUrl, requestEntity, RestItemsInfo.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        RestItemsInfo itemsInfo = entity.getBody();
        List<RestItem> items = itemsInfo.getItems();

        assertEquals(items.size(), 1);
        for (RestItem item: items) {
            System.out.println("The Item ID is " + item.getId());
            assertNotNull(item.getId()); 
            assertEquals(item.getDescription(), RestItemsFixture.NEW_ITEM_DESC);
            assertEquals(item.getStoreIds(), RestItemsFixture.NEW_ITEM_STOREIDS);
        }
    }
        
    @Test
    public void testAddMultipleNewItems() {
        HttpHeaders headers = AuthUtil.getAuthTokenHeaders(); 

        RestTemplate template = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<String>(
                        RestItemsFixture.newMultipleItemsJSON(),headers);

        ResponseEntity<RestItemsInfo> entity = template.postForEntity(
                testUrl, requestEntity, RestItemsInfo.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        RestItemsInfo itemsInfo = entity.getBody();
        List<RestItem> items = itemsInfo.getItems();

        // check if we got the exact numbers of new items from the service. 
        assertEquals(items.size(), RestItemsFixture.NEW_MULTI_ITEMS_NUM);

        int i = 0;
        for (RestItem item: items) {
            System.out.println("The Item ID is " + item.getId());
            //we should get the new Ids
            assertNotNull(item.getId()); 
            assertEquals(item.getDescription(), RestItemsFixture.NEW_MULTI_ITEMS_DESC + i);
            assertEquals(item.getStoreIds(), RestItemsFixture.NEW_ITEM_STOREIDS);
            i++;
        }
    }

    @Test
    public void testUpdateExistingItem() {
        HttpHeaders headers = AuthUtil.getAuthTokenHeaders(); 

        RestTemplate template = new RestTemplate();
        // we should have a preloaded data for item id 1. 
        int id = 1;

        HttpEntity<String> requestEntity = new HttpEntity<String>(
                        RestItemsFixture.updateItemJSON(id),headers);

        ResponseEntity<RestItemsInfo> entity = template.postForEntity(
                testUrl, requestEntity, RestItemsInfo.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        RestItemsInfo itemsInfo = entity.getBody();
        List<RestItem> items = itemsInfo.getItems();

        for (RestItem item: items) {
            System.out.println("The Item ID is " + item.getId());
            assertEquals(item.getId(), Long.valueOf(id)); 
            System.out.println("The Item description is " + item.getDescription());
            assertEquals(item.getDescription(), RestItemsFixture.UPDATE_ITEM_DESC + id);
            assertEquals(item.getPrice(), RestItemsFixture.UPDATE_ITEM_PRICE);
            assertEquals(item.getLocale(), RestItemsFixture.UPDATE_ITEM_LOCALE);
            assertEquals(item.getStoreIds(), RestItemsFixture.UPDATE_ITEM_STOREIDS);
        }
    }

    @Test
    public void testUpdateNonExistingItem() {
        HttpHeaders headers = AuthUtil.getAuthTokenHeaders(); 

        RestTemplate template = new RestTemplate();
        int nonexisting_id = 1000;

        HttpEntity<String> requestEntity = new HttpEntity<String>(
                        RestItemsFixture.updateItemJSON(nonexisting_id),headers);

        ResponseEntity<RestItemsInfo> entity = template.postForEntity(
                testUrl, requestEntity, RestItemsInfo.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        // we expect the error message from server.
        RestItemsInfo itemsInfo = entity.getBody();
        List<String> errorMsgs = itemsInfo.getErrorMessages();
        System.out.println("=== error message is " + errorMsgs);
        assertTrue(errorMsgs.size()>0);
        assertTrue(errorMsgs.contains("INVALID_ITEM_ID:"+nonexisting_id));
    }

    @Test
    public void testUpdateMultipleNewItems() {
        HttpHeaders headers = AuthUtil.getAuthTokenHeaders(); 

        RestTemplate template = new RestTemplate();
        // let's create some multiple new items first. 

        HttpEntity<String> requestEntity = new HttpEntity<String>(
                        RestItemsFixture.newMultipleItemsJSON(),headers);

        ResponseEntity<RestItemsInfo> entity = template.postForEntity(
                testUrl, requestEntity, RestItemsInfo.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        RestItemsInfo itemsInfo = entity.getBody();
        List<RestItem> items = itemsInfo.getItems();
        
        List<Long> item_ids = new ArrayList<Long>(); 
        for (RestItem item: items) {
            Long item_id = item.getId();
            item_ids.add(item_id);
        }


        requestEntity = new HttpEntity<String>(
                        RestItemsFixture.updateMultipleItemsJSON(item_ids),headers);

        entity = template.postForEntity(
                testUrl, requestEntity, RestItemsInfo.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        itemsInfo = entity.getBody();
        items = itemsInfo.getItems();

        // check if we got the exact numbers of new items from the service. 
        assertEquals(items.size(), RestItemsFixture.UPDATE_MULTI_ITEMS_NUM);

        for (int i=0; i< items.size(); i++) {
            RestItem item = items.get(i);
            System.out.println("The Item ID is " + item.getId());
            assertEquals(item.getDescription(), RestItemsFixture.UPDATE_ITEM_DESC);
            assertEquals(item.getStoreIds(), RestItemsFixture.UPDATE_ITEM_STOREIDS);
            assertEquals(item.getPrice(), RestItemsFixture.UPDATE_ITEM_PRICE);
            assertEquals(item.getLocale(), RestItemsFixture.UPDATE_ITEM_LOCALE);
        }
    }

    @Test
    public void testMissingAuthHeaders() 
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> requestEntity = new HttpEntity<String>(
                        RestItemsFixture.newMultipleItemsJSON(),headers);


        RestTemplate template = new RestTemplate();
        Long itemId = Long.valueOf(10);
        // pass the token in the header 
        try {
            ResponseEntity<RestItemsInfo> entity = template.postForEntity(
                   testUrl, requestEntity, RestItemsInfo.class);
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
        HttpEntity<String> requestEntity = new HttpEntity<String>(
                        RestItemsFixture.newMultipleItemsJSON(),headers);

        RestTemplate template = new RestTemplate();
        Long itemId = Long.valueOf(10);
        // pass the wrong token in the header 
        try {
            ResponseEntity<RestItemsInfo> entity = template.postForEntity(
                   testUrl, requestEntity, RestItemsInfo.class);
        } catch (HttpClientErrorException ex) {
            System.out.println("=== http code = " + ex.getStatusCode());
            assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        }
    }
}


