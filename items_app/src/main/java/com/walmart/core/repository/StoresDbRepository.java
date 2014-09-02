package com.walmart.core.repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.walmart.core.domain.Store;
import java.util.*;
import javax.annotation.Resource;
 
public class StoresDbRepository implements StoresRepository {
    @Resource
    private StoresJpaRepository repository  ;

    public StoresDbRepository() {
    }

    @Transactional
    @Override
    public synchronized Store save(Store item) {
        repository.save(item);
        return item;
    }

    @Transactional(readOnly = true)
    @Override
    public Store findById(Long id) {
        return repository.findOne(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Long> findByStoreIn(List<Long> store_ids) {
        return repository.findByStoreIn(store_ids);
    }
    /**
     * This setter method should be used only by unit tests.
     * @param repository
     */
    protected void setStoresJpaRepository(StoresJpaRepository repository) {
        this.repository = repository;
    }

}
