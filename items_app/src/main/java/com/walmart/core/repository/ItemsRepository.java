package com.walmart.core.repository;

import com.walmart.core.domain.Item;


import java.util.List;

public interface ItemsRepository {

  Item save(Item item);
  Item findById(Long id);

}
