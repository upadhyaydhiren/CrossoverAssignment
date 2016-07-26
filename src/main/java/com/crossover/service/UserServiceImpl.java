package com.crossover.service;

import com.crossover.models.User;
import com.crossover.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * This is subclass of {@link GenericServiceImpl} implements {@link UserService}
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see GenericServiceImpl
 * @see UserService
 */


@Service
public class UserServiceImpl extends GenericServiceImpl<User, String> implements UserService {

    private final UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    @Override
    public void saveOrUpdate(User entity) {

        entity.setPassword(new BCryptPasswordEncoder().encode(entity.getPassword()));
        super.saveOrUpdate(entity);
    }

    @Override
    public User findOne(String id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }
}
