package com.sgd.artlink.controller.impl;

import com.sgd.artlink.dto.UserDto;
import com.sgd.artlink.exception.ClientSideException;
import com.sgd.artlink.model.User;
import com.sgd.artlink.security.jwt.JwtTokenProvider;
import com.sgd.artlink.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<Map<Object, Object>> signin(@RequestBody UserDto userDto) {
        try {
            String email = userDto.getEmail();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, userDto.getPassword()));
            User user = userService.findByEmail(email);

            String token = jwtTokenProvider.createToken(email, user.getRole());
            Map<Object, Object> response = new HashMap<>();
            response.put("email", email);
            response.put("token", token);

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            throw new ClientSideException("Invalid email or password", e);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(UserDto userDto) {
        User user = User.builder()
                        .username(userDto.getUsername())
                        .email(userDto.getEmail())
                        .password(userDto.getPassword())
                        .build();

        userService.register(user);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> users() {
        List<UserDto> users = userService.getAll()
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
