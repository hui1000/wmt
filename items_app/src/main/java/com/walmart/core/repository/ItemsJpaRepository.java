package com.walmart.core.repository;

import java.util.List;
import com.walmart.core.domain.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemsJpaRepository extends CrudRepository<Item, Long> {
}
