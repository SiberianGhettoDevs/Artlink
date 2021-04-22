package com.sgd.artlink.security.jwt;

import com.sgd.artlink.model.Status;
import com.sgd.artlink.model.User;
import lombok.NoArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Класс который формирует из User JwtUser
 */

@NoArgsConstructor
public final class JwtUserFactory {
    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                new SimpleGrantedAuthority(user.getRole().getName().name())
        );
    }

}
