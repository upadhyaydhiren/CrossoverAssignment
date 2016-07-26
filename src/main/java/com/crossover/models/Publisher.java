package com.crossover.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * This is model class for publisher
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since 10/7/16
 */

@Document(collection = "publisher")
public class Publisher{

    @Id
    private String id;

    @Field(value = "publisher_name")
    private String publisherName;

    @Field(value = "publisher_url")
    private String publisherURL;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherURL() {
        return publisherURL;
    }

    public void setPublisherURL(String publisherURL) {
        this.publisherURL = publisherURL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null || obj.getClass() != this.getClass())
        { return false; }
        Publisher publisher = (Publisher)obj;
        return publisherName.equals(publisher.getPublisherName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((publisherName == null) ? 0 : publisherName.hashCode());
        return result;
    }
}
