package com.walmart.core.services;

import com.walmart.rest.domain.RestItem;
import com.walmart.rest.domain.RestItemsInfo;

public interface ItemService {

  public RestItem requestItemDetails(Long id);
  public RestItemsInfo updateItems(RestItemsInfo restItemsInfo);

}
