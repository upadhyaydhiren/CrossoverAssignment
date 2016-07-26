package com.crossover.service;

import com.crossover.models.User;

/**
 * This is sub interface of generic service {@link GenericService} for {@link User}
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since 10/7/16
 * @see GenericService
 * @see UserServiceImpl
 */
public interface UserService extends GenericService<User, String> {

    User findOne(String id);

    User findByUserName(String userName);
}
