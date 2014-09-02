package com.walmart.core.repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.walmart.core.domain.Item;
import java.util.*;
import javax.annotation.Resource;
 
public class ItemsDbRepository implements ItemsRepository {
    @Resource
    private ItemsJpaRepository repository  ;

    public ItemsDbRepository() {
    }

    @Transactional
    @Override
    public synchronized Item save(Item item) {
        Item itemOut = repository.save(item);
        return itemOut;
    }

    @Transactional(readOnly = true)
    @Override
    public Item findById(Long id) {
        return repository.findOne(id);
    }

    /**
     * This setter method should be used only by unit tests.
     * @param repository
     */
    protected void setItemsJpaRepository(ItemsJpaRepository repository) {
        this.repository = repository;
    }

}
