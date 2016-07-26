package com.crossover.service;

import java.util.List;

/**
 * This is generic service interface for all entities
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see GenericServiceImpl
 */

public interface GenericService<E, K> {

    void saveOrUpdate(E entity);

    void saveOrUpdate(List<E> entity);

    List<E> getAll();

    E get(K id);

    void remove(K id);

}
