package com.crossover.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


/**
 * This is View Resolver Config class
 * Created by dhiren on 11/7/16.
 * @author dhiren
 * @since 11/7/16
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
 */

@Configuration
@EnableWebMvc
class ViewResolverConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix(".html");
        return resolver;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**", "/resources/**").addResourceLocations("classpath:/META-INF/resources/webjars/", "/resources/");
    }


}
