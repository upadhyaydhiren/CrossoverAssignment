package com.crossover.controller;

import com.crossover.application.MainApplication;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.Assert.*;

/**
 * This is web index controller integration testing
 * Created by dhiren on 13/7/16.
 * @author dhiren
 * @since 13/7/16
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(MainApplication.class)
@WebIntegrationTest*/
public class IndexControllerTest {

    RestTemplate template = new TestRestTemplate();
    //@Test
    public void index() throws Exception {

        HttpStatus httpStatus = template.getForEntity("http://localhost:8080/login", String.class).getStatusCode();
        assertThat(httpStatus.is2xxSuccessful(), Is.is(true));
    }
}