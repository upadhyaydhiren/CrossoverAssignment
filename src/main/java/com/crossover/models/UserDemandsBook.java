package com.crossover.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * This is user demand book model  class
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since  10/7/16
 */

@Document(collection = "user_demands_book")
public class UserDemandsBook {

    @Id
    private String id;

    @Field(value = "user_name")
    private String userName;

    @DBRef(db = "book")
    private List<Book>books;

    private Long placedDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public Long getPlacedDate() {
        return placedDate;
    }

    public void setPlacedDate(Long placedDate) {
        this.placedDate = placedDate;
    }

}
