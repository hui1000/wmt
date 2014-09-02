package com.walmart.rest.controller;

import com.walmart.core.services.ItemService;
import com.walmart.rest.domain.RestItem;
import com.walmart.rest.domain.RestItemsInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;
import com.walmart.core.services.AuthService2;

@Controller
@RequestMapping("/services/items")
public class ItemCommandsController {
    private static Logger LOG = LoggerFactory.getLogger(ItemCommandsController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private AuthService2 authService2;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestItemsInfo> updateItems(
            @RequestHeader(value = "X-Auth-Token", required=false) String authToken,
            @RequestBody RestItemsInfo restItemsInfo,
            UriComponentsBuilder builder

        ) {
        HttpHeaders headers = new HttpHeaders();
        if (!authService2.isAuthTokenOk(authToken)){
            return new ResponseEntity<RestItemsInfo>(null, headers, HttpStatus.UNAUTHORIZED);
        }

        RestItemsInfo newRestItemsInfo = itemService.updateItems(restItemsInfo);


        // if server found error for the request. 
        if (newRestItemsInfo.getErrorMessages().size() >0) {
            return new ResponseEntity<RestItemsInfo>(newRestItemsInfo, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<RestItemsInfo>(newRestItemsInfo, headers, HttpStatus.OK);
        }
    }

}
