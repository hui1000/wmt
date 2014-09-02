package com.walmart.core.repository;

import java.util.List;
import com.walmart.core.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersJpaRepository extends CrudRepository<User, Long> {
    public User findByUserName(String userName) ;
}
