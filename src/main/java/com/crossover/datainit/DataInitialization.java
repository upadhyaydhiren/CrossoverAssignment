package com.crossover.datainit;

import com.crossover.constant.Role;
import com.crossover.models.Book;
import com.crossover.models.Publisher;
import com.crossover.models.User;
import com.crossover.service.BookService;
import com.crossover.service.PublisherService;
import com.crossover.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * This is data initialization class
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/2016
 * @see org.springframework.context.ApplicationListener
 * @see ApplicationReadyEvent
 */

@SuppressWarnings("ALL")
@Component
public class DataInitialization implements ApplicationListener<ApplicationReadyEvent> {


    private final Logger logger = LoggerFactory.getLogger(DataInitialization.class);

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    private void initData()
    {
        try
        {
            ClassLoader classLoader = getClass().getClassLoader();
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(classLoader.getResource("data/Book.json").getFile());
            List<Object> objects= (List<Object>) objectMapper.readValue(file, List.class);
            Set<Publisher> publishers= new HashSet<>();
            List<Book>books = new ArrayList<>();
            for (Object object: objects)
            {

                Publisher publisher = new Publisher();
                Book book = new Book();
                if (object instanceof Map) {
                    Map<String,Object>booksMap = (Map<String,Object>)object;
                    for (Map.Entry<String, Object> entry : booksMap.entrySet()) {
                        if (entry.getKey().equals("Publisher"))
                        {
                            publisher.setPublisherName(String.valueOf(entry.getValue()));
                            book.setPublisherName(publisher.getPublisherName());
                        }
                        else if (entry.getKey().equals("Title"))
                        {
                            book.setTitle(String.valueOf(entry.getValue()));
                        }
                        else if (entry.getKey().equals("Description"))
                        {
                            book.setDescription(String.valueOf(entry.getValue()));
                        }
                        else if (entry.getKey().equals("Authors"))
                        {
                            List<String>authors = (List<String>)entry.getValue();
                            book.setAuthors(authors);
                        }
                    }
                }
                publishers.add(publisher);
                books.add(book);
            }
            publisherService.saveOrUpdate(publishers.stream().collect(Collectors.toList()));
            bookService.saveOrUpdate(books);
        }
        catch (Exception e)
        {
            logger.error("Error in data intialization", e);
        }
    }
    private void initUser()
    {

        User user = new User();
        user.setFirstName("Dhiren");
        user.setLastName("Upadhyay");
        user.setUserName("admin");
        user.setPassword("admin");
        user.setUniversityName("DAIICT");
        List<Role>roles = new ArrayList<>();
        roles.add(Role.USER);
        user.setRoles(roles);
        userService.saveOrUpdate(user);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (userService.getAll().isEmpty()) {
            initUser();
            initData();
        }
    }
}
