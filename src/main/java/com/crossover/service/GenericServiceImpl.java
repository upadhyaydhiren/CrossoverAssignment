package com.crossover.service;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * This is implementation class of generic service interface {@link GenericService}
 * This class interact with mongo repository {@link MongoRepository} and perform database related information
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see BookServiceImpl
 * @see PublisherServiceImpl
 * @see UserDemandsBookServiceImpl
 * @see UserServiceImpl
 * @see GenericService
 */

@Service
public class GenericServiceImpl<E, K extends Serializable> implements GenericService<E, K> {

    private MongoRepository<E, K> mongoRepository;

    GenericServiceImpl(MongoRepository<E, K> mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    GenericServiceImpl() {

    }

    /**
     * This is generic method for save and update all entities document
     *
     * @param entity {@link E}
     */
    @Override
    public void saveOrUpdate(E entity) {
        mongoRepository.save(entity);
    }


    @Override
    public void saveOrUpdate(List<E> entity) {
        mongoRepository.save(entity);
    }

    /**
     * This is generic method for fetch all entities document
     * @return list of Entity {@link List}
     */
    @Override
    public List<E> getAll() {
        return mongoRepository.findAll();
    }

    /**
     * This is generic method for get entity document by id
     * @param id {@link K extends {@link Serializable}}
     * @return entity {@link E}
     */
    @Override
    public E get(K id) {
        return mongoRepository.findOne(id);
    }

    /**
     * This is generic method remove document by id
     * @param id {@link K extends {@link Serializable}}
     */
    @Override
    public void remove(K id) {

        mongoRepository.delete(id);
    }
}
