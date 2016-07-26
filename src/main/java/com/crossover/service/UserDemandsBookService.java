package com.crossover.service;

import com.crossover.models.UserDemandsBook;

import java.util.List;

/**
 * This is sub interface of generic service {@link GenericService} for {@link UserDemandsBook}
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see GenericService
 * @see UserDemandsBookServiceImpl
 */

public interface UserDemandsBookService extends GenericService<UserDemandsBook, String> {

    List<UserDemandsBook> findByUserName(String userName);

    UserDemandsBook findByPlacedDate(Long date);

    List<UserDemandsBook> findByPlacedDateBetween(Long from, Long to);
}
