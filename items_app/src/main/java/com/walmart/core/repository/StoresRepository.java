package com.walmart.core.repository;

import com.walmart.core.domain.Store;
 
import java.util.List;

public interface StoresRepository {

  Store save(Store store);
  Store findById(Long id);

  List<Long> findByStoreIn(List<Long> store_ids);
}
