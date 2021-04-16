package com.sgd.artlink.controller.impl;

import com.sgd.artlink.controller.AuthController;
import com.sgd.artlink.dto.UserDto;
import com.sgd.artlink.model.User;
import com.sgd.artlink.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    @Override
    public ResponseEntity<Void> signup(UserDto userDto) {
        User user = User.builder()
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .password(userDto.getPassword())
                        .build();

        authService.signup(user);

        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<UserDto>> users() {
        List<UserDto> users = authService.users()
                                        .stream()
                                        .map(u -> UserDto.builder().id(u.getId())
                                                                    .username(u.getUsername())
                                                                    .email(u.getEmail())
                                                                    .password(u.getPassword())
                                                                    .role(u.getRole().getName().name())
                                                                    .build()
                                        ).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}
