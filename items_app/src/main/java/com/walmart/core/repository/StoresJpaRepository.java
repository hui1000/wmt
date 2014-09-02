package com.walmart.core.repository;

import java.util.List;
import com.walmart.core.domain.Store;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;


public interface StoresJpaRepository extends CrudRepository<Store, Long> {
      @Query("select s.id from Store s where s.id in ?1")
      List<Long> findByStoreIn(List<Long> store_ids);
}
