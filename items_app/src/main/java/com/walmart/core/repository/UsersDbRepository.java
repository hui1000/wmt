package com.walmart.core.repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.walmart.core.domain.User;
import java.util.*;
import javax.annotation.Resource;
 
public class UsersDbRepository implements UsersRepository {
    @Resource
    private UsersJpaRepository repository  ;

    public UsersDbRepository() {
    }

    @Transactional
    @Override
    public User save(User u) {
        return repository.save(u);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUserName(String userName) {
        return repository.findByUserName(userName);
    }

    /**
     * This setter method should be used only by unit tests.
     * @param repository
     */
    protected void setUsersJpaRepository(UsersJpaRepository repository) {
        this.repository = repository;
    }
}
