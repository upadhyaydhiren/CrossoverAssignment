package com.crossover.service;

import com.crossover.models.UserDemandsBook;
import com.crossover.repository.UserDemadsBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is subclass of {@link GenericServiceImpl} implements {@link UserDemandsBookService}
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see GenericServiceImpl
 * @see UserDemandsBookService
 */

@Service
public class UserDemandsBookServiceImpl extends GenericServiceImpl<UserDemandsBook, String> implements UserDemandsBookService {

    private final UserDemadsBookRepository userDemadsBookRepository;

    @Autowired
    public UserDemandsBookServiceImpl(UserDemadsBookRepository userDemadsBookRepository) {
        super(userDemadsBookRepository);
        this.userDemadsBookRepository = userDemadsBookRepository;
    }

    @Override
    public List<UserDemandsBook> findByUserName(String userName) {
        return userDemadsBookRepository.findByUserName(userName);
    }

    @Override
    public UserDemandsBook findByPlacedDate(Long date) {
        return userDemadsBookRepository.findByPlacedDate(date);
    }

    @Override
    public List<UserDemandsBook> findByPlacedDateBetween(Long from, Long to) {
        return userDemadsBookRepository.findByPlacedDateBetween(from, to);
    }
}
