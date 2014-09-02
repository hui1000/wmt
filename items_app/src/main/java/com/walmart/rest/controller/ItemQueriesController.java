package com.walmart.rest.controller;

import com.walmart.core.services.ItemService;
import com.walmart.rest.domain.RestItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.walmart.core.services.AuthService2;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
@RequestMapping("/services/items")
public class ItemQueriesController {

    private static Logger LOG = LoggerFactory.getLogger(ItemQueriesController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private AuthService2 authService2;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<RestItem> viewItem(
            @RequestHeader(value = "X-Auth-Token", required=false) String authToken,
            @PathVariable Long id) {

        HttpHeaders headers = new HttpHeaders();
        if (!authService2.isAuthTokenOk(authToken)){
            System.out.println("**** Not Auth OK ***");
            return new ResponseEntity<RestItem>(null, headers, HttpStatus.UNAUTHORIZED);
        }

        RestItem item = itemService.requestItemDetails(id);

        if (item == null){
            System.out.println("item= null");
        }else{
            System.out.println("item=" + item.toString());
        }
        if (item == null) {
            return new ResponseEntity<RestItem>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<RestItem>(item, HttpStatus.OK);
    }
}
