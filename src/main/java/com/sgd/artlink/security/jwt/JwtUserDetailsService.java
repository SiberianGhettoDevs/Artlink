package com.sgd.artlink.security.jwt;

import com.sgd.artlink.exception.ClientSideException;
import com.sgd.artlink.model.User;
import com.sgd.artlink.repository.UserRepository;
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

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByEmail(email)
                                        .orElseThrow(() -> {
                                            log.warn("IN loadUserByUsername - no user found by email: {}", email);
                                            return new ClientSideException("user not found by email: " + email);
                                        });
            JwtUser jwtUser = JwtUserFactory.create(user);

            log.info("IN loadUserByUsername - found user by email: " + email);
            return jwtUser;
        } catch (ClientSideException e) {
            throw new UsernameNotFoundException("User not found by email: " + email, e);
        }
    }
}
