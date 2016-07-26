package com.crossover.service;

import com.crossover.models.Book;

import java.util.List;

/**
 * This is sub interface of {@link GenericService} for {@link Book}
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see GenericService
 * @see BookServiceImpl
 */


public interface BookService extends GenericService<Book, String> {

    List<Book> findByPublisherName(String publisherName);
}
