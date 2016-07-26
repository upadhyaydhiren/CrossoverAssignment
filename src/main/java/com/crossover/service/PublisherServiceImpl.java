package com.crossover.service;

import com.crossover.models.Publisher;
import com.crossover.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is subclass of {@link GenericServiceImpl} implements {@link PublisherService}
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see GenericServiceImpl
 * @see PublisherService
 */

@SuppressWarnings("ALL")
@Service
public class PublisherServiceImpl extends GenericServiceImpl<Publisher, String> implements PublisherService {


    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        super(publisherRepository);
        this.publisherRepository = publisherRepository;
    }

    /**
     * This method is specific method for fetch publisher {@link Publisher} by publisher name
     * @param publisherName {@link String}
     * @return publisher {@link Publisher}
     */
    @Override
    public Publisher findByPublisherName(String publisherName) {
        return publisherRepository.findByPublisherName(publisherName);
    }
}
