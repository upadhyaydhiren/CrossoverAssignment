package com.crossover.service;

import com.crossover.models.Publisher;

/**
 * This is sub interface of {@link GenericService} for {@link Publisher}
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see GenericService
 * @see PublisherServiceImpl
 */

public interface PublisherService extends GenericService<Publisher, String> {

    Publisher findByPublisherName(String publisherName);
}
