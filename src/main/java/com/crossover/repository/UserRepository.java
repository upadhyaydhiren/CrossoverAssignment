package com.crossover.repository;

import com.crossover.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * This is subinterface of mongo repository {@link MongoRepository} for {@link User}
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since 10/7/16
 * @see MongoRepository
 * @see com.crossover.service.UserServiceImpl
 */

@Repository
public interface UserRepository extends MongoRepository<User, String>
{
    User findByUserName(String userName);
}
