package com.crossover.service;

import com.crossover.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is user authentication service class implements userDatailsService {@link UserDetailsService}
 * Created by dhiren on 10/7/16.
 * @author dhiren
 * @since 10/7/16
 * @see UserDetailsService
 */

@Service
public class UserAuthenticationService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(UserAuthenticationService.class);

    @Autowired
    private UserService userService;

    /**
     * This method is used for when user try to log in to system
     * @param userName {@link String}
     * @return UserDetails {@link UserDetails}
     * @throws UsernameNotFoundException {@link UsernameNotFoundException}
     * @see  UsernameNotFoundException
     * @see org.springframework.security.core.userdetails.User
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = null;

        try {
            user = userService.findByUserName(userName);
            System.out.println(user.getUserName());
        } catch (Exception e) {
            logger.debug("Error in login service", e);
        }

        if (user == null) {
            throw new UsernameNotFoundException("User " + userName + " not found");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(userName, user.getPassword(), true, true, true, true, authorities);
    }

}
