package com.crossover.controller;

import com.crossover.constant.Role;
import com.crossover.models.Book;
import com.crossover.models.Publisher;
import com.crossover.models.User;
import com.crossover.models.UserDemandsBook;
import com.crossover.service.BookService;
import com.crossover.service.PublisherService;
import com.crossover.service.UserDemandsBookService;
import com.crossover.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This controller is serve all web services
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see @{@link RestController}
 */

@RestController
class WebServiceController {

    private final Logger logger = LoggerFactory.getLogger(WebServiceController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserDemandsBookService userDemandsBookService;

    @Autowired
    private BookService bookService;

    @Autowired
    private PublisherService publisherService;

    /**
     * This method handle user Registration process
     * @param user {@link User}
     * @return status using response entity {@link ResponseEntity}
     * @see ResponseEntity
     */
    @RequestMapping(value = "register",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@Validated @RequestBody User user)
    {
        try
        {
            List<Role>roles = new ArrayList<>();
            roles.add(Role.USER);
            user.setRoles(roles);
            userService.saveOrUpdate(user);
            logger.info("user sucessfully registered");
            return new ResponseEntity<>("You are registered successfully", HttpStatus.OK);
        }
        catch (Exception e)
        {
            logger.error("Error in user registration", e);
            return new ResponseEntity<>("Invalid data",HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method is provide response for previous demands request
     * @return java.util.List<UserDemandsBook> using {@link ResponseEntity}
     * @see Authentication
     * @see SecurityContextHolder
     */
    @RequestMapping(value = "previousdemands",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getPreviousDemandsBook()
    {
        try {
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                Map<String,Object>result = new HashMap<>();
                List<UserDemandsBook>userDemandsBooks = userDemandsBookService.findByUserName(userDetail.getUsername());
                User user = userService.findByUserName(userDetail.getUsername());
                logger.info("Previous demands book request");
                result.put("previousdemands",userDemandsBooks);
                result.put("user",user);
                return new ResponseEntity<>(result, HttpStatus.OK);
            } else {
                logger.error("Unauthorized access");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e)
        {
            logger.info("Error in previous demands book request");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method is return all intial book list {@link List<Book>} with User data {@link User}
     * @return Map
     */
    @RequestMapping(value = "getallbook", method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllBook()
    {
        try {
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                List<Book>books = bookService.getAll();
                User user = userService.findByUserName(userDetail.getUsername());
                Map<String,Object>result = new HashMap<>();
                List<Publisher>publishers = publisherService.getAll();
                List<UserDemandsBook>userDemandsBooks = userDemandsBookService.findByUserName(userDetail.getUsername());
                getValidBooks(books, userDemandsBooks);
                result.put("user",user);
                result.put("books",books.subList(1,11));
                result.put("publisherlist",publishers.stream().map(Publisher::getPublisherName).collect(Collectors.toList()));
                logger.info("Getting all book request");
                return new ResponseEntity<>(result,HttpStatus.OK);

            } else {
                logger.error("Unauthorized access");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e)
        {
            logger.info("Error in getting book request");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *This method handle add book demands {@link UserDemandsBook} request
     * @param userDemandsBook {@link UserDemandsBook}
     * @return status using {@link ResponseEntity}
     *
     */
    @RequestMapping(value = "addbookdemands", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addUserBookDemands(@RequestBody UserDemandsBook userDemandsBook)
    {
        try {
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                    userDemandsBook.setUserName(userDetail.getUsername());
                    userDemandsBook.setPlacedDate(System.currentTimeMillis());
                    userDemandsBookService.saveOrUpdate(userDemandsBook);
                    logger.info("add user book demands");
                    return new ResponseEntity<>("your request is successfully added", HttpStatus.OK);
                }
                else
                {
                    logger.error("Unauthorized access");
                    return new ResponseEntity<>("Unauthorized access", HttpStatus.UNAUTHORIZED);
                }
            }
        catch (Exception e)
        {
            logger.error("Error in book demand");
            return new ResponseEntity<>("Error in book demand", HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method is provide search result for searchbypublisher and return booklist {@link List<Book>}
     * @param publisherName {@link String}
     * @return Map
     */
    @RequestMapping(value = "searchbypublisher",method = RequestMethod.POST)
    public ResponseEntity<Map<String,Object>> searchBookByPublisherName(@RequestParam(value = "publisherName") String publisherName)
    {
        try {
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                List<Book>books = bookService.findByPublisherName(publisherName);
                User user = userService.findByUserName(userDetail.getUsername());
                List<Publisher>publishers = publisherService.getAll();
                List<UserDemandsBook>userDemandsBooks = userDemandsBookService.findByUserName(userDetail.getUsername());
                getValidBooks(books, userDemandsBooks);
                Map<String,Object>result = new HashMap<>();
                result.put("user",user);
                result.put("books",books);
                result.put("publisherlist",publishers.stream().map(Publisher::getPublisherName).collect(Collectors.toList()));
                logger.info("Getting all book request by publisher name");
                return new ResponseEntity<>(result,HttpStatus.OK);

            } else {
                logger.error("Unauthorized access");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e)
        {
            logger.info("Error in getting book request");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * This method is update existing demand book list and return updated demand list
     * @param demandsId {@link String}
     * @param bookId {@link String}
     * @return Map
     */
    @RequestMapping(value = "updatepreviousdemands/{demandsId}/{bookId}", method = RequestMethod.PUT)
    public ResponseEntity<Map<String,Object>> deletePreviousDemands(@PathVariable(value = "demandsId") String demandsId, @PathVariable(value = "bookId") String bookId)
    {
        try {
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                UserDemandsBook userDemandsBook = userDemandsBookService.get(demandsId);
                if (userDemandsBook.getBooks().size() == 1)
                    userDemandsBookService.remove(demandsId);
                else
                {
                    userDemandsBook.getBooks().removeIf(book -> book.getId().equals(bookId));
                    userDemandsBookService.saveOrUpdate(userDemandsBook);
                }
                User user = userService.findByUserName(userDetail.getUsername());
                List<UserDemandsBook>userDemandsBooks = userDemandsBookService.findByUserName(userDetail.getUsername());
                Map<String,Object>result = new HashMap<>();
                result.put("user",user);
                result.put("previousdemands",userDemandsBooks);
                logger.info("Getting all book request by publisher name");
                return new ResponseEntity<>(result,HttpStatus.OK);

            } else {
                logger.error("Unauthorized access");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e)
        {
            logger.info("Error in getting book request");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * This method is use for update user profile
     * @param user {@link User} using {@link RequestBody}
     * @return msg {@link String} using {@link ResponseEntity}
     */
    @RequestMapping(value = "updateprofile", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateProfile(@RequestBody User user)
    {
        try{
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (!(auth instanceof AnonymousAuthenticationToken)) {
                UserDetails userDetail = (UserDetails) auth.getPrincipal();
                User fetchedUser = userService.findByUserName(userDetail.getUsername());
                if (!fetchedUser.getPassword().equals(user.getPassword()))
                {
                    fetchedUser.setPassword(user.getPassword());
                }
                fetchedUser.setFirstName(user.getFirstName());
                fetchedUser.setLastName(user.getLastName());
                fetchedUser.setUniversityName(user.getUniversityName());
                userService.saveOrUpdate(fetchedUser);
                logger.info("update profile request");
                return new ResponseEntity<>("Your account is successfully updated",HttpStatus.OK);
            } else {
                logger.error("Unauthorized access");
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        catch (Exception e)
        {
            logger.error("Error in user update profile", e);
            return new ResponseEntity<>("Invalid data",HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * This method is validate book list using previous using book demand list
     * @param allBooks {@link List<Book>}
     * @param userDemandsBooks {@link List<UserDemandsBook>}
     */
    private void getValidBooks(List<Book>allBooks, List<UserDemandsBook>userDemandsBooks) {
        for (UserDemandsBook userDemandsBook: userDemandsBooks) {
            for (Book bookObj: userDemandsBook.getBooks())
            {
                allBooks.removeIf(book -> book.getId().equals(bookObj.getId()));
            }
        }
    }

    /**
     * This method check user is exist or not
     * @param userName {@link String}
     * @return Boolean using {@link ResponseEntity}
     */
    @RequestMapping(value = "existuser/{userName}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> existUser(@PathVariable("userName") String userName)
    {
        try
        {
            User user = userService.findByUserName(userName);
            return new ResponseEntity<>(user == null, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}