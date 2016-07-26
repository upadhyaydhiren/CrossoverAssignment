package com.crossover.repository;

import com.crossover.models.UserDemandsBook;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This is sub interface of Mongo repository {@link MongoRepository}  for {@link UserDemandsBook}
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since 10/7/16
 * @see MongoRepository
 * @see com.crossover.service.UserDemandsBookServiceImpl
 */

@Repository
public interface UserDemadsBookRepository extends MongoRepository<UserDemandsBook, String> {

    List<UserDemandsBook> findByUserName(String userName);

    UserDemandsBook findByPlacedDate(Long date);

    List<UserDemandsBook> findByPlacedDateBetween(Long from, Long to);
}
