package com.walmart.core.repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import com.walmart.core.domain.User;
import java.util.*;
import javax.annotation.Resource;
 
public interface UsersRepository {
    public User save(User u) ;
    public User findByUserName(String userName) ;
}
