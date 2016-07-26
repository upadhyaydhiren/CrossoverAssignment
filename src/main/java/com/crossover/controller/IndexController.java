package com.crossover.controller;

import com.crossover.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * This is controller class for navigate path of ui
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since 10/7/16
 */


@Controller
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private UserService userService;


    /**
     * This is root navigation method for ui redirect
     * @param error {@link String}
     * @param logout {@link String}
     * @return return login ui page
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String index(@RequestParam(value = "error",required = false) String error, @RequestParam(value = "logout",	required = false) String logout)
    {
        logger.info("root intialized");
        return "login";
    }

    /**
     * This method redirect to home page after successfully login
     * @return home ui page
     */
    @RequestMapping(value = "home", method = RequestMethod.GET)
    public String home()
    {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            logger.info("After login",userDetail.getUsername());
            return "home";
        }
         else {
            logger.error("Unauthorized access");
            return "redirect:login";
        }
    }

    /**
     * This is ui navigation method for previous method
     * @return return ui page name for view controller
     */
    @RequestMapping(value = "demands", method = RequestMethod.GET)
    public String previousDemands()
    {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            logger.info("After login",userDetail.getUsername());
            return "demand";
        }
        else {
            logger.error("Unauthorized access");
            return "redirect:login";
        }
    }

}