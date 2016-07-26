package com.crossover.service;

import com.crossover.models.Book;
import com.crossover.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is implementation class of {@link BookService} and sub class of {@link GenericServiceImpl}
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see BookService
 * @see GenericServiceImpl
 */

@Service
public class BookServiceImpl extends GenericServiceImpl<Book, String> implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        super(bookRepository);
        this.bookRepository = bookRepository;
    }

    /**
     * This method return list of book based on publisher name
     *
     * @param publisherName {@link String}
     * @return list of Book {@link List}
     */
    @Override
    public List<Book> findByPublisherName(String publisherName) {
        return bookRepository.findByPublisherName(publisherName);
    }
}
