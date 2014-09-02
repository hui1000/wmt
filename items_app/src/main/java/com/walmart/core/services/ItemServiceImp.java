package com.walmart.core.services;

import com.walmart.core.domain.Item;
import com.walmart.core.domain.Store;
import com.walmart.rest.domain.RestItem;
import com.walmart.rest.domain.RestItemsInfo;

import com.walmart.core.repository.ItemsRepository;
import com.walmart.core.repository.StoresRepository;

import java.util.*;

public class ItemServiceImp implements ItemService {

  private final ItemsRepository itemsRepository;
  private final StoresRepository storesRepository;

  public ItemServiceImp(final ItemsRepository itemsRepository, 
                        final StoresRepository storesRepository) {
    this.itemsRepository = itemsRepository;
    this.storesRepository = storesRepository;
  }

  @Override
  public RestItemsInfo updateItems(RestItemsInfo restItemsInfoIn) {

    RestItemsInfo restItemsInfoOut = new RestItemsInfo();
    List<RestItem> restItemsIn = restItemsInfoIn.getItems();
    List<RestItem> restItemsOut = new ArrayList();
    List<String> errorMessages= new ArrayList<String>();

    for(RestItem restItem: restItemsIn) {
        Item itemIn =  restItem.toItem();
        Long itemId  = itemIn.getId();
        boolean validRequest = true;

        List<Long> storeIds = restItem.getStoreIds();

        if (storeIds != null) {
            // find the valid store IDs from DB.
            List<Long> storeIdsInDB = storesRepository.findByStoreIn(storeIds);
            for (Long storeId: storeIds) {
                if (storeIdsInDB!=null && storeIdsInDB.contains(storeId)) {
                    System.out.println(" Valid Store ID :"  + storeId);
                } else {
                    errorMessages.add("ITEM_ID:" + itemId
                            + " INVALID_STORE_ID:" + storeId);
                    restItemsInfoOut.setErrorMessages(errorMessages);
                    validRequest = false;
                }
            }
        }

        if (itemId != null){
            Item item2 = itemsRepository.findById(itemId);
            if (item2 == null) {
                errorMessages.add("INVALID_ITEM_ID:" + itemId);
                restItemsInfoOut.setErrorMessages(errorMessages);
                validRequest = false;
            }
        }

        // if both itemId and storeIds are valid ID, do save.
        if (validRequest) { 
            Item itemOut = itemsRepository.save(itemIn);
            restItemsOut.add(RestItem.fromItem(itemOut));
        }
    }
    restItemsInfoOut.setItems(restItemsOut);
    restItemsInfoOut.setErrorMessages(errorMessages);

    return restItemsInfoOut;
  }

  @Override
  public RestItem requestItemDetails(Long id) {

    Item item = itemsRepository.findById(id);

    RestItem restItem = null; 
    if (item != null){
        restItem = RestItem.fromItem(item);
    }
    return restItem;
  }

}
