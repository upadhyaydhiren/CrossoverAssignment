package com.crossover.repository;

import com.crossover.models.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This is subinterface of mongo repository {@link MongoRepository} for {@link Book}
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since 10/7/16
 * @see MongoRepository
 * @see com.crossover.service.BookServiceImpl
 */

@Repository
public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findByPublisherName(String publisherName);
}