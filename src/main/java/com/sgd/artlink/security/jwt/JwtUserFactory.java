package com.sgd.artlink.security.jwt;

import com.sgd.artlink.model.Status;
import com.sgd.artlink.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Класс который формирует из User JwtUser
 */
public final class JwtUserFactory {

    private JwtUserFactory() {}

    public static JwtUser create(User user) {

        return JwtUser.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .enabled(Status.ACTIVE.equals(user.getStatus()))
                    .authority(new SimpleGrantedAuthority(user.getRole().getName().name()))
                    .build();
    }

}
