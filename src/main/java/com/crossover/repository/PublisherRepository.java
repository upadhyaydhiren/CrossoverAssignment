package com.crossover.repository;

import com.crossover.models.Publisher;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * This is subinterface of mongo repository {@link MongoRepository} for {@link Publisher}
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since 10/7/16
 * @see MongoRepository
 * @see com.crossover.service.PublisherServiceImpl
 */

@Repository
public interface PublisherRepository extends MongoRepository<Publisher, String> {

    Publisher findByPublisherName(String publisherName);
}
