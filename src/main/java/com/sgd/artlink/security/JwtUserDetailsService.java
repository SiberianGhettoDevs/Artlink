package com.sgd.artlink.security;

import com.sgd.artlink.exception.ClientSideException;
import com.sgd.artlink.model.User;
import com.sgd.artlink.security.jwt.JwtUser;
import com.sgd.artlink.security.jwt.JwtUserFactory;
import com.sgd.artlink.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userService.findByEmail(email);
            JwtUser jwtUser = JwtUserFactory.create(user);

            log.info("IN loadUserByUsername - found user by email: " + email);
            return jwtUser;
        } catch (ClientSideException e) {
            throw new UsernameNotFoundException("User not found by email: " + email, e);
        }
    }
}
